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

public class Cart extends Activity {
	final String LOGGER_TAG = "WITWATERSRAND";
	String _httpPostMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOGGER_TAG, "Cart -- onCreate()");

		setContentView(R.layout.activity_cart);

		// Create orderlist
		
		OrderItem[] myOrder = getOrderList();
		
		OrderEncoder myOrderEncoder = new OrderEncoder(myOrder);
		_httpPostMessage = myOrderEncoder.getOrderJsonMessage();
		Log.i(LOGGER_TAG, "Cart -- _httpPostMessage = " + _httpPostMessage);
		
		UploadOrder task = new UploadOrder();
		Log.i(LOGGER_TAG, "Cart -- Calling another thread for the HTTP POST request");
		task.execute(new String[] {"http://146.141.125.177/yii/index.php/mobile/PlaceOrders"});
	}

	private OrderItem[] getOrderList() {
		OrderItem[] myOrder = new OrderItem[3];
		myOrder[0] = new OrderItem();
		myOrder[0].setItemName("Hake");
		myOrder[0].setPurchaseQuantity(1);
		myOrder[0].setStationName("A la Minute Grill");

		myOrder[1] = new OrderItem();
		myOrder[1].setItemName("Beef Olives");
		myOrder[1].setPurchaseQuantity(2);
		myOrder[1].setStationName("Main Meal");
	
		myOrder[2] = new OrderItem();
		myOrder[2].setItemName("Chicken Lasagne");
		myOrder[2].setPurchaseQuantity(3);
		myOrder[2].setStationName("Frozen Meals");
		
		
		return myOrder;
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

			String responseMessage = sendHTTPRequest(urls);
			Log.i(LOGGER_TAG, "Cart -- UploadOrder -- responseMessage = " + responseMessage);

			return responseMessage;
		}

		private String sendHTTPRequest(String[] urls) {
			for (String url : urls) {
				Log.i(LOGGER_TAG, "Cart -- UploadOrder -- sendHTTPRequest()");
				int TIMEOUT_MILLISEC = 10000; // = 10 seconds
				
				HttpParams httpParams = new BasicHttpParams();	
				HttpConnectionParams.setConnectionTimeout(httpParams,
						TIMEOUT_MILLISEC);
				HttpConnectionParams.setSoTimeout(httpParams,
						TIMEOUT_MILLISEC);
				HttpClient client = new DefaultHttpClient(httpParams);
				
				try {
					HttpPost myPostRequest = new HttpPost(url);
					Log.i(LOGGER_TAG, "_httpPostMessage = " + _httpPostMessage);
					
					StringEntity message = new StringEntity(_httpPostMessage);
					myPostRequest.addHeader("content-type", "applcation/json");
					myPostRequest.setEntity(message);
					HttpResponse myResponse = client.execute(myPostRequest);
					
					Log.i(LOGGER_TAG, "Cart -- UploadOrder -- HTTP request complete");

					if (myResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						Log.i(LOGGER_TAG, "Cart -- UploadOrder -- HTTP OK");
						String myJsonString = EntityUtils.toString(myResponse
								.getEntity());
						Log.d(LOGGER_TAG, myJsonString);
						return myJsonString;
					}
				} catch (IOException e) {
					e.printStackTrace();
					Log.d(LOGGER_TAG, "Cart -- Exception e -- " + e.toString() + " -- " + e.getMessage());
				} catch (Exception b) {
					b.printStackTrace();
					Log.d(LOGGER_TAG, "Cart -- Exception b -- " + b.toString() + " -- " + b.getMessage());
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
		}
	}
}
