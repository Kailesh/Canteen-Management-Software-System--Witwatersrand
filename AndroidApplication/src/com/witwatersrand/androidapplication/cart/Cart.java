package com.witwatersrand.androidapplication.cart;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.witwatersrand.androidapplication.ApplicationPreferences;
import com.witwatersrand.androidapplication.CanteenManagerDatabase;
import com.witwatersrand.androidapplication.OrderItem;
import com.witwatersrand.androidapplication.R;
import com.witwatersrand.androidapplication.R.id;
import com.witwatersrand.androidapplication.R.layout;
import com.witwatersrand.androidapplication.R.menu;
import com.witwatersrand.androidapplication.progressrequester.longpoller.LongPollerProgressRequester;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
	

	
	static final String APPLIATION_DATA_FILENAME = "mySharedPreferences";
	private static final String ORDER_NUMBER_KEY = "order";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOGGER_TAG, "Cart -- onCreate()");

		setContentView(R.layout.activity_cart);

		_cartLV = (ListView) findViewById(R.id.lvCart);
		totalTV = (TextView) findViewById(R.id.tvCartTotal);
		totalTV.setText("R " + String.format("%.2f", (float) 0));
		_deliveryCB = (CheckBox) findViewById(R.id.cbDelivery);
		_deliveryFloorS = (Spinner) findViewById(R.id.spFloor);
		_deliverySideS = (Spinner) findViewById(R.id.spSide);
		_deliveryCB.setOnClickListener(this);
		

		setDelivery();
		
		loadCart();

		makePurchaseB = (Button) findViewById(R.id.bPurchase);
		makePurchaseB.setOnClickListener(this);
	}
	
	private void setDelivery() {
		if (_deliveryCB.isChecked()) {
			_deliveryFloorS.setEnabled(true);
			_deliverySideS.setEnabled(true);
			
		} else {
			_deliveryFloorS.setEnabled(false);
			_deliverySideS.setEnabled(false);
		}
	}
	

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
			myOrderEncoder.setDeliveryLocation("Floor " + _deliveryFloorS.getSelectedItem() + " - " + _deliverySideS.getSelectedItem());
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_cart, menu);
		return true;
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


	private class UploadOrder extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			Log.i(LOGGER_TAG, "Cart -- UploadOrder -- doInBackground()");

			// String responseMessage = sendHTTPRequest(urls);
			// Log.i(LOGGER_TAG, "Cart -- UploadOrder -- responseMessage = "
			// + responseMessage);
					
			// Fake response
			String responseMessage  = "Order Received";
			return responseMessage;
		}

		private String sendHTTPRequest(String[] urls) {
			for (String url : urls) {
				Log.i(LOGGER_TAG, "Cart -- UploadOrder -- sendHTTPRequest()");
				int TIMEOUT_MILLISEC = 10000; // = 10 seconds

				HttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams,
						TIMEOUT_MILLISEC);
				HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpClient client = new DefaultHttpClient(httpParams);

				try {
					HttpPost myPostRequest = new HttpPost(url);
					Log.i(LOGGER_TAG, "_httpPostMessage = " + _httpPostMessage);

					StringEntity message = new StringEntity(_httpPostMessage);
					myPostRequest.addHeader("content-type", "applcation/json");
					myPostRequest.setEntity(message);
					HttpResponse myResponse = client.execute(myPostRequest);

					Log.i(LOGGER_TAG,
							"Cart -- UploadOrder -- HTTP request complete");
					Log.i(LOGGER_TAG, "" + myResponse.getStatusLine().getStatusCode());
					if (myResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						Log.i(LOGGER_TAG, "Cart -- UploadOrder -- HTTP OK");
						String myJsonString = EntityUtils.toString(myResponse
								.getEntity());
						Log.d(LOGGER_TAG, myJsonString);
						return myJsonString;
					}
				} catch (IOException e) {
					e.printStackTrace();
					Log.d(LOGGER_TAG, "Cart -- Exception e -- " + e.toString()
							+ " -- " + e.getMessage());
				} catch (Exception b) {
					b.printStackTrace();
					Log.d(LOGGER_TAG, "Cart -- Exception b -- " + b.toString()
							+ " -- " + b.getMessage());
				} finally {
					client.getConnectionManager().shutdown();
				}
			}
			// TODO Throw Exception
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String response) {
			super.onPostExecute(response);
			Log.i(LOGGER_TAG, "Cart -- UploadOrder -- onPostExecute()");

			Toast.makeText(Cart.this, "" + response, Toast.LENGTH_LONG).show();
			Log.i(LOGGER_TAG, "Cart -- UploadOrder -- onPostExecute() -- Setting pending status to true");
			ApplicationPreferences.setPendingStatus(Cart.this, true);

			// startOrderCompletionService();
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
