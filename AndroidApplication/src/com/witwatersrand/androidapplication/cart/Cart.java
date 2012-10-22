package com.witwatersrand.androidapplication.cart;

import com.witwatersrand.androidapplication.ApplicationPreferences;
import com.witwatersrand.androidapplication.CanteenManagerDatabase;
import com.witwatersrand.androidapplication.OrderItem;
import com.witwatersrand.androidapplication.R;
import com.witwatersrand.androidapplication.httprequests.HttpPostRequester;
import com.witwatersrand.androidapplication.progressrequester.longpoller.LongPollerProgressRequester;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The cart activity
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class Cart extends Activity implements OnClickListener {
	final String LOGGER_TAG = "WITWATERSRAND";
	String _httpPostMessage;
	OrderItem[] _myCart;
	ListView _cartLV;
	public static TextView totalTV;
	Button makePurchaseB;
	Spinner _deliveryFloorS, _deliverySideS;
	CheckBox _deliveryCB;
	boolean closeItemsActivity = false;

	/**
	 * Setting up the cart activty
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOGGER_TAG, "Cart -- onCreate()");
		setContentView(R.layout.activity_cart);
		initializeVariables();
		
		setDelivery();
		loadCart();

		makePurchaseB = (Button) findViewById(R.id.bPurchase);
		makePurchaseB.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF006400	));
		makePurchaseB.setOnClickListener(this);
	}
	
	/**
	 * Initializing the view variables and binding the views to the ones defined in the XML layout
	 */
	private void initializeVariables() {
		_cartLV = (ListView) findViewById(R.id.lvCart);
		totalTV = (TextView) findViewById(R.id.tvCartTotal);
		totalTV.setText("R " + String.format("%.2f", (float) 0));
		_deliveryCB = (CheckBox) findViewById(R.id.cbDelivery);
		_deliveryFloorS = (Spinner) findViewById(R.id.spFloor);
		_deliverySideS = (Spinner) findViewById(R.id.spSide);
		_deliveryCB.setOnClickListener(this);
		_deliveryFloorS.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF006400));
		_deliverySideS.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF006400));
	}

	/**
	 * Enabling/disabling the delivery location options 
	 */
	private void setDelivery() {
		if (_deliveryCB.isChecked()) {

			_deliveryFloorS.setEnabled(true);
			_deliverySideS.setEnabled(true);
			
		} else {
			_deliveryFloorS.setEnabled(false);
			_deliverySideS.setEnabled(false);
		}
	}
	
	/**
	 * Implements button press logic
	 */
	public void onClick(View v) {
		Log.i(LOGGER_TAG, "Cart -- onClick()");
		switch (v.getId()) {
		case R.id.bPurchase:
			closeItemsActivity = true;
			CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(this);
			myDatabase.open();
			
			int orderNumber = ApplicationPreferences.getOrderNumber(this);
			ApplicationPreferences.setOrderNumber(this, orderNumber);
			OrderItem[] myOrder = myDatabase.getOrder(orderNumber);
			myDatabase.close();
			
			orderNumber++;
			ApplicationPreferences.setOrderNumber(this, orderNumber);
			
			OrderEncoder myOrderEncoder = new OrderEncoder(myOrder, ApplicationPreferences.getOrderNumber(this) -1, getBaseContext());
			myOrderEncoder.setDelivery(_deliveryCB.isChecked());
			if(_deliveryCB.isChecked()) {
				myOrderEncoder.setDeliveryLocation("Floor " + _deliveryFloorS.getSelectedItem() + " - " + _deliverySideS.getSelectedItem());
			} else {
				myOrderEncoder.setDeliveryLocation("-");
			}
			myOrderEncoder.encodeOrderIntoJson();
			_httpPostMessage = myOrderEncoder.getOrderJsonMessage();
			Log.i(LOGGER_TAG, "Cart -- _httpPostMessage = " + _httpPostMessage);

			UploadOrder task = new UploadOrder();
			Log.i(LOGGER_TAG,
					"Cart -- Calling another thread for the HTTP POST request");
			task.execute(new String[]
					{"http://" + ApplicationPreferences.getServerIPAddress(getBaseContext()) + "/yii/index.php/mobile/placeorders"});
			Button currentB = (Button) findViewById(R.id.bPurchase);
			currentB.setEnabled(false);
			break;
		
		case R.id.cbDelivery:
			setDelivery();
			
			break;
		}
	}

	/**
	 * Load the cart from the database
	 */
	private void loadCart() {
		Log.i(LOGGER_TAG, "Cart -- loadCart()");

		CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(this);
		myDatabase.open();
		
		Log.d(LOGGER_TAG, "orderNumber = " + ApplicationPreferences.getOrderNumber(this));
		_myCart = myDatabase.getOrder(ApplicationPreferences.getOrderNumber(this));
		Log.d(LOGGER_TAG, "_myCart.length = " + _myCart.length);
		
		float total = myDatabase.getTotalForOrder(ApplicationPreferences.getOrderNumber(this));
		myDatabase.close();
		totalTV.setText("R " + String.format("%.2f", total));
		
		_cartLV.setAdapter(new CartAdapter(this, R.layout.cart_list_item,
				_myCart));
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#finish()
	 */
	@Override
	public void finish() {
		Log.i(LOGGER_TAG, "Cart -- finish()");
		// Setup intent to send back
		Intent returnData = new Intent();
		returnData.putExtra("close", closeItemsActivity);
		setResult(RESULT_OK, returnData);
		super.finish();
	}

	
	/**
	 * A class which handles running the HTTP request on another thread
	 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
	 *
	 */
	private class UploadOrder extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			Log.i(LOGGER_TAG, "Cart -- UploadOrder -- doInBackground()");
			
			HttpPostRequester requester = new HttpPostRequester(urls[0]);
			requester.setPostMessage(_httpPostMessage);
			return requester.receiveResponse();
								
			// Fake response
			// return "Order received";
		}
		
		
		/**
		 * Executed after the HTTP response has been received or failed. Displays
		 * the cart items. 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String response) {
			super.onPostExecute(response);
			Log.i(LOGGER_TAG, "Cart -- UploadOrder -- onPostExecute()");

			Toast.makeText(Cart.this, "" + response, Toast.LENGTH_LONG).show();
			Log.i(LOGGER_TAG, "Cart -- UploadOrder -- onPostExecute() -- Setting pending status to true for this order");
			startOrderCompletionService();
			Log.i(LOGGER_TAG, "Cart -- UploadOrder -- onPostExecute() -- Closing the Cart activity");
			Cart.this.finish();
		}
		
		private void startOrderCompletionService() {
			Log.i(LOGGER_TAG, "Cart -- UploadOrder -- startOrderCompletionService()");
			Intent myBackgroundServiceIntent;
			myBackgroundServiceIntent = new Intent(getApplicationContext(), LongPollerProgressRequester.class);
			
			String ORDER_COMPLETION_SERVICE_TAG = "order_completion_requester_service" + (ApplicationPreferences.getOrderNumber(getBaseContext()) - 1);
			Log.i(LOGGER_TAG, "Cart -- UploadOrder -- startOrderCompletionService() -- ORDER_COMPLETION_SERVICE_TAG = |" + ORDER_COMPLETION_SERVICE_TAG + "|");
			
			myBackgroundServiceIntent.putExtra("myService", ORDER_COMPLETION_SERVICE_TAG);
			myBackgroundServiceIntent.putExtra("order_number", ApplicationPreferences.getOrderNumber(getBaseContext()) - 1);
			
			myBackgroundServiceIntent.addCategory(ORDER_COMPLETION_SERVICE_TAG);
			startService(myBackgroundServiceIntent);
		}
	}
}