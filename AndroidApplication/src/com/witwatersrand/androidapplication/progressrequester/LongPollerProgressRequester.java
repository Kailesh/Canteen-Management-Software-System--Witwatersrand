package com.witwatersrand.androidapplication.progressrequester;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

import com.witwatersrand.androidapplication.ApplicationPreferences;
import com.witwatersrand.androidapplication.CanteenManagerDatabase;
import com.witwatersrand.androidapplication.Cart;
import com.witwatersrand.androidapplication.DeviceIDGenerator;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class LongPollerProgressRequester extends Service {
	private static final String LOGGER_TAG = "WITWATERSRAND";
	private static final String JSON_DEVICE_ID_KEY = "deviceID";
	static final String DEVICE_ID_UNKNOWN = "Device ID Unknown";
	private static final String UNKNOWN = "Unknown";
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		Log.d(LOGGER_TAG, "LongPollerProgressRequester -- onCreate()");
		super.onCreate();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind()
	 */
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(LOGGER_TAG, "LongPollerProgressRequester -- onBind()");
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(LOGGER_TAG, "LongPollerProgressRequester -- onStartCommand()");
		LongPollingRequest task = new LongPollingRequest();
		task.execute(new String[] { "http://146.141.125.64/yii/index.php/mobile/longpollingprogress" });
		Log.d(LOGGER_TAG, "PollingService -- onStartCommand() -- After calling task.execute()");
		return super.onStartCommand(intent, flags, startId);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		Log.d(LOGGER_TAG, "LongPollerProgressRequester -- onDestroy()");
		super.onDestroy();
	}
	
	private class LongPollingRequest extends AsyncTask<String, Void, String> {
		int CONNECTION_TIMEOUT = 7200000; // Two hours

		@Override
		protected String doInBackground(String... urls) {
			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- doInBackground()");
			
			//---------------------------|Fake delayed response|---------------------------
			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- doInBackground() -- Tring to waist time");
			for(int i = 0; i <= 1000000000 ; i++) {
				// waist time
			}
			return "{\"item\": \"Pizza\",\"status\": \"Done\",\"orderNumber\": \"3\"}";
			//-----------------------------------------------------------------------------
			// return executeHttpRequest(urls);
		}

		private String executeHttpRequest(String... urls) {
			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- executeHttpRequest()");
			for (String url : urls) {
				try {
					HttpResponse httpResponse = setupHttpRquest(url);
					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						Log.i(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- HTTP OK");
						String myJsonString = EntityUtils.toString(httpResponse.getEntity());
						Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- sendHTTPRequest() -- Received message = |" + myJsonString + "|");
						return myJsonString;
					}
				} catch (Exception e) {
					Log.e(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- executeHttpRequest() -- Exception = " + e.getMessage());
					e.printStackTrace();
				}
			}
			Log.e(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- executeHttpRequest() -- Fatal Error");
			return UNKNOWN;
		}

		private HttpResponse setupHttpRquest(String url) throws ClientProtocolException, IOException {
			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- setupHttpRquest()");
			DefaultHttpClient myDefaultHttpClient = new DefaultHttpClient();
			HttpParams httpParams = myDefaultHttpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
			ConnManagerParams.setTimeout(httpParams, CONNECTION_TIMEOUT);
			HttpPost myPostRequest = new HttpPost(url);
			myPostRequest.addHeader("Accept", "application/json");
			StringEntity message = new StringEntity(getDeviceIDMessage());
			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- message = |" + message + "|");
			myPostRequest.addHeader("content-type", "applcation/json");
			myPostRequest.setEntity(message);
			return myDefaultHttpClient.execute(myPostRequest);
		}
		
		// TODO Unchecked conversion - Type safety: The method put(Object, Object)
		// belongs to the raw type HashMap. References to generic type HashMap<K,V>
		// should be parameterized
		@SuppressWarnings("unchecked")
		public String getDeviceIDMessage() {
			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- getDeviceIDMessage()");
			JSONObject myJsonObject = new JSONObject();
			
			//--------------------Fake Mac Address--------------------------
			
			// String myMacAddress = DeviceIDGenerator.getWifiMacAddress(LongPollerProgressRequester.this);
			String myMacAddress = "90:C1:15:BC:97:4F";
			
			myJsonObject.put(JSON_DEVICE_ID_KEY, myMacAddress);

			StringWriter myStringWriter = new StringWriter();
			try {
				myJsonObject.writeJSONString(myStringWriter);
				return myStringWriter.toString();
			} catch (IOException e) {
				Log.i(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- getDeviceIDMessage() -- Exception = |" + e.getMessage() + "|");
				e.printStackTrace();
			}
			return DEVICE_ID_UNKNOWN;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String response) {
			super.onPostExecute(response);
			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- onPostExecute()");
			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- onPostExecute() -- Response message = |" + response + "|");
			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- onPostExecute() -- calling this.cancel(true)");
			
			SingleProgressStatusParser myParser = new SingleProgressStatusParser(response);
			
			// Update Database
			CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(LongPollerProgressRequester.this);
			myDatabase.open();
			myDatabase.updateItemProgress(myParser.getItemName(), myParser.getOrderNumber(), myParser.getItemProgress());
			myDatabase.close();
		
			this.cancel(true);
			
			if (ApplicationPreferences.isStatusPending(LongPollerProgressRequester.this)) {
				Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- onPostExecute() -- Status orders have not been received yet");
				new LongPollingRequest().execute(new String[] { "http://146.141.125.64/yii/index.php/mobile/longpollingprogress" });
			} else {
				stopService(Cart.myBackgroundServiceIntent);
			}
		}
	}
}
