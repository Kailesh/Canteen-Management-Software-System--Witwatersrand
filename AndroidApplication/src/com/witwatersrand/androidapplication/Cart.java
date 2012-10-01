package com.witwatersrand.androidapplication;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
	final String loggerTag = "WITWATERSRAND";
	String httpPostMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(loggerTag, "Cart -- onCreate()");

		setContentView(R.layout.activity_cart);

		// Create orderlist
		
		MenuItem[] myOrder = getOrderList();
		
		OrderEncoder myOrderEncoder = new OrderEncoder(myOrder);
		httpPostMessage = myOrderEncoder.getOrderJsonMessage();
		Log.i(loggerTag, "Cart -- httpPostMessage = " + httpPostMessage);
		
		UploadOrder task = new UploadOrder();
		Log.i(loggerTag, "Cart -- Calling another thread for the HTTP POST request");
		task.execute(new String[] {"http://146.141.125.123/yii/index.php/mobile/PlaceOrders"});
	}

	private MenuItem[] getOrderList() {
		MenuItem[] myOrder = new MenuItem[3];
		myOrder[0] = new MenuItem();
		myOrder[0].setItemName("Hake");
		myOrder[0].setQuantity(1);
		myOrder[0].setStationName("A la Minute Grill");

		myOrder[1] = new MenuItem();
		myOrder[1].setItemName("Beef Olives");
		myOrder[1].setQuantity(2);
		myOrder[1].setStationName("Main Meal");
	
		myOrder[2] = new MenuItem();
		myOrder[2].setItemName("Chicken Lasagne");
		myOrder[2].setQuantity(3);
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
			Log.i(loggerTag, "Cart -- UploadOrder -- doInBackground()");

			String responseMessage = sendHTTPRequest(urls);
			Log.i(loggerTag, "Cart -- UploadOrder -- responseMessage = " + responseMessage);

			return responseMessage;
		}

		private String sendHTTPRequest(String[] urls) {
			for (String url : urls) {
				Log.i(loggerTag, "Cart -- UploadOrder -- sendHTTPRequest()");
				int TIMEOUT_MILLISEC = 10000; // = 10 seconds
				Log.i(loggerTag, "001");
				
				HttpParams httpParams = new BasicHttpParams();
				Log.i(loggerTag, "002");
				
				HttpConnectionParams.setConnectionTimeout(httpParams,
						TIMEOUT_MILLISEC);
				HttpConnectionParams.setSoTimeout(httpParams,
						TIMEOUT_MILLISEC);
				Log.i(loggerTag, "003");
				
				HttpClient client = new DefaultHttpClient(httpParams);
				
				Log.i(loggerTag, "004");
				try {
					HttpPost myPostRequest = new HttpPost(url);
					Log.i(loggerTag, "005");
					
					Log.i(loggerTag, "httpPostMessage = " + httpPostMessage);
					
					StringEntity message = new StringEntity(httpPostMessage);
					
					Log.i(loggerTag, "006");
					myPostRequest.addHeader("content-type", "applcation/json");
					Log.i(loggerTag, "007");
					
					myPostRequest.setEntity(message);
					Log.i(loggerTag, "008");
					
					HttpResponse myResponse = client.execute(myPostRequest);
					
					Log.i(loggerTag, "Cart -- UploadOrder -- HTTP request complete");

					if (myResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						Log.i(loggerTag, "Cart -- UploadOrder -- HTTP OK");
						String myJsonString = EntityUtils.toString(myResponse
								.getEntity());
						Log.d(loggerTag, myJsonString);
						return myJsonString;
					}
				} catch (IOException e) {
					e.printStackTrace();
					Log.d(loggerTag, "Cart -- Exception e -- " + e.toString() + " -- " + e.getMessage());
				} catch (Exception b) {
					b.printStackTrace();
					Log.d(loggerTag, "Cart -- Exception b -- " + b.toString() + " -- " + b.getMessage());
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
