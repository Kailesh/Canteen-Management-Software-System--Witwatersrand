package com.witwatersrand.androidapplication;

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

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Cart extends Activity implements OnClickListener {
	final String LOGGER_TAG = "WITWATERSRAND";
	String _httpPostMessage;
	ListView _cartLV;
	OrderItem[] _myCart;
	public static TextView totalTV;
	Button makePurchaseB;

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
		loadCart();

		makePurchaseB = (Button) findViewById(R.id.bPurchase);
		makePurchaseB.setOnClickListener(this);
	}

	public void onClick(View v) {
		Log.i(LOGGER_TAG, "Cart -- onClick()");
		switch (v.getId()) {
		case R.id.bPurchase:
			CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(this);
			myDatabase.open();
			
			int orderNumber = ApplicationPreferences.getOrderNumber(this);
			ApplicationPreferences.setOrderNumber(this, orderNumber);
			OrderItem[] myOrder = myDatabase.getOrder(orderNumber);
			myDatabase.close();
			
			orderNumber++;
			ApplicationPreferences.setOrderNumber(this, orderNumber);
			
			OrderEncoder myOrderEncoder = new OrderEncoder(myOrder);
			_httpPostMessage = myOrderEncoder.getOrderJsonMessage();
			Log.i(LOGGER_TAG, "Cart -- _httpPostMessage = " + _httpPostMessage);

			UploadOrder task = new UploadOrder();
			Log.i(LOGGER_TAG,
					"Cart -- Calling another thread for the HTTP POST request");
			task.execute(new String[]
					{"http://146.141.125.177/yii/index.php/mobile/PlaceOrders"});
			Button currentB = (Button) findViewById(R.id.bPurchase);
			currentB.setEnabled(false);
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
		myDatabase.close();

		_cartLV.setAdapter(new CartAdapter(this, R.layout.cart_list_item,
				_myCart));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_cart, menu);
		return true;
	}

	private class UploadOrder extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			Log.i(LOGGER_TAG, "Cart -- UploadOrder -- doInBackground()");

			//String responseMessage = sendHTTPRequest(urls);
			//Log.i(LOGGER_TAG, "Cart -- UploadOrder -- responseMessage = "
			//		+ responseMessage);

			String responseMessage  = "bleh";
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
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Toast.makeText(Cart.this, "Order Placed :)", Toast.LENGTH_LONG).show();
		}
	}

}
