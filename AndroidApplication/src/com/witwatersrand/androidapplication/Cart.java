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
		
		UploadOrder task = new UploadOrder();
		Log.i(loggerTag, "Cart -- Calling another thread for the HTTP POST request");
		task.execute(new String[] {"http://146.141.125.80/yii/index.php/mobile/placeorder"});
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
			Log.i(loggerTag, "Carts -- UploadOrder -- doInBackground()");

			String responseMessage = sendHTTPRequest(urls);
			Log.i(loggerTag, "Carts -- responseMessage = " + responseMessage);

			return responseMessage;
		}

		private String sendHTTPRequest(String[] urls) {
			for (String url : urls) {
				Log.d(loggerTag, "Cart -- UploadOrder -- sendHTTPRequest()");
				int TIMEOUT_MILLISEC = 10000; // = 10 seconds
				HttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams,
						TIMEOUT_MILLISEC);
				HttpConnectionParams.setSoTimeout(httpParams,
						TIMEOUT_MILLISEC);
				HttpClient client = new DefaultHttpClient(httpParams);
				try {
					HttpPost myPostRequest = new HttpPost(url);
					StringEntity message = new StringEntity(httpPostMessage);
					myPostRequest.addHeader("content-type", "applcation/json");
					myPostRequest.setEntity(message);
					HttpResponse myResponse = client.execute(myPostRequest);
					
					Log.i(loggerTag, "Cart -- UploadOrder -- HTTP request complete");

					if (myResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						Log.i(loggerTag, "Items -- DownloadMenuData -- HTTP OK");
						String myJsonString = EntityUtils.toString(myResponse
								.getEntity());
						Log.d(loggerTag, myJsonString);
						return myJsonString;
					}
				} catch (IOException e) {
					e.printStackTrace();
					Log.d(loggerTag, e.getMessage());
				} catch (Exception b) {
					b.printStackTrace();
					Log.d(loggerTag, b.getMessage());
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
