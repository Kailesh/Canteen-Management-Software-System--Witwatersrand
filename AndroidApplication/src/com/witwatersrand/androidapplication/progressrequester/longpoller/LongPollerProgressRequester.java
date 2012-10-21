package com.witwatersrand.androidapplication.progressrequester.longpoller;

import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONObject;

import com.witwatersrand.androidapplication.R;
import com.witwatersrand.androidapplication.ApplicationPreferences;
import com.witwatersrand.androidapplication.CanteenManagerDatabase;
import com.witwatersrand.androidapplication.DeviceIDGenerator;
import com.witwatersrand.androidapplication.httprequests.HttpPostRequester;
import com.witwatersrand.androidapplication.httprequests.HttpRequester;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LongPollerProgressRequester extends Service {
	private static final String LOGGER_TAG = "WITWATERSRAND";
	private static final String JSON_DEVICE_ID_KEY = "deviceID";
	private static final String JSON_ORDER_NUMBER_KEY = "orderNumber";
	
	static final String DEVICE_ID_UNKNOWN = "Device ID Unknown";
	
	private String ORDER_COMPLETION_SERVICE_TAG;
	private int ORDER_NUMBER;

	
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
		task.execute(new String[] { "http://" + ApplicationPreferences.getServerIPAddress(getBaseContext()) + "/yii/index.php/mobile/longpoller" });
		Log.d(LOGGER_TAG, "PollingService -- onStartCommand() -- After calling task.execute()");
		ORDER_COMPLETION_SERVICE_TAG = intent.getExtras().getString("myService");
		ORDER_NUMBER = intent.getIntExtra("order_number", 0);
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

		@Override
		protected String doInBackground(String... urls) {
			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- doInBackground()");
			
			HttpPostRequester requester = new HttpPostRequester(urls[0]);
			requester.setTimeout(7200000); // Two hours
			requester.setPostMessage(getRequestMessage());
			return requester.receiveResponse();
			
//			//---------------------------|Fake delayed response|---------------------------
//			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- doInBackground() -- Tring to waist time");
//			for(int i = 0; i <= 1000000000 ; i++) {
//				// waist time
//			}
//			//return "{\"orderNumber\": 2,\"status\": \"Done\"}";
//			//-----------------------------------------------------------------------------
		}

		// TODO Unchecked conversion - Type safety: The method put(Object, Object)
		// belongs to the raw type HashMap. References to generic type HashMap<K,V>
		// should be parameterized
		@SuppressWarnings("unchecked")
		public String getRequestMessage() {
			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- getRequestMessage()");
			JSONObject myJsonObject = new JSONObject();

			String myMacAddress = DeviceIDGenerator.getWifiMacAddress(LongPollerProgressRequester.this);
			
			
			myJsonObject.put(JSON_DEVICE_ID_KEY, myMacAddress);
			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- getRequestMessage() - Order number sent to server = |" + (ApplicationPreferences.getOrderNumber(getBaseContext()) - 1) + "|");
			myJsonObject.put(JSON_ORDER_NUMBER_KEY, (ApplicationPreferences.getOrderNumber(LongPollerProgressRequester.this) - 1));

			StringWriter myStringWriter = new StringWriter();
			try {
				myJsonObject.writeJSONString(myStringWriter);
				String temString = myStringWriter.toString();
				Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- getRequestMessage() - temString = |" + temString + "|");

				return temString;
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
			
			if (response.equals(HttpRequester.getResponseNotOkMessage())) {
				Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- onPostExecute() -- Http not OK");
				Toast.makeText(getApplicationContext(), HttpRequester.getResponseNotOkMessage(), Toast.LENGTH_SHORT).show();
				return;
			}
			
			if (response.equals(HttpRequester.getExceptionThrownMessage())) {
				Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- onPostExecute() -- Exception thrown when  attempting to receive response");
				Toast.makeText(getApplicationContext(), HttpRequester.getExceptionThrownMessage(), Toast.LENGTH_SHORT).show();
				return;
			} 
			
			OrderProgressStatusParser myParser = new OrderProgressStatusParser(response);
			
			// Update Database
			CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(LongPollerProgressRequester.this);
			myDatabase.open();
			myDatabase.updateOrderProgress(myParser.getOrderNumber(), myParser.getItemProgress());
			
			// ApplicationPreferences.setPendingStatus(getBaseContext(), myDatabase.allStatusReceicved());
			myDatabase.close();
			
			// Show notification to user
			showNotification();
			
			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- onPostExecute() -- calling this.cancel(true)");
			this.cancel(true);
			
			stopOrderCompletionService();
		}

		private void showNotification() {
			Log.d(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- showNotification()");
			NotificationManager notificationManager = (NotificationManager) 
					getSystemService(NOTIFICATION_SERVICE);
			Notification orderCompleteNotification = new Notification(com.witwatersrand.androidapplication.R.drawable.ic_launcher,
					"Order " + ORDER_NUMBER + " is complete at RMB canteen", System.currentTimeMillis());
			// Hide the notification after its selected
			
			orderCompleteNotification.flags = Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_SHOW_LIGHTS | Notification.FLAG_AUTO_CANCEL;
			orderCompleteNotification.sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.soundsdroplet);
			orderCompleteNotification.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE;
						
			Intent orderActivityIntent = new Intent("com.witwatersrand.androidapplication.ORDERPROGRESS");
			orderActivityIntent.putExtra("Order", "" + ORDER_NUMBER);

			PendingIntent orderCompleteActivity = PendingIntent.getActivity(LongPollerProgressRequester.this, 0, orderActivityIntent, 0);
			orderCompleteNotification.setLatestEventInfo(LongPollerProgressRequester.this, "Canteen Manager",
					"Order " + ORDER_NUMBER + " is complete at RMB canteen" , orderCompleteActivity);
			orderCompleteNotification.number += 1;
			notificationManager.notify(0, orderCompleteNotification);
		}

		private void stopOrderCompletionService() {
			Log.i(LOGGER_TAG, "LongPollerProgressRequester -- LongPollingRequest -- startOrderCompletionService()");
			Intent myBackgroundServiceIntent;
			myBackgroundServiceIntent = new Intent(getApplicationContext(), LongPollerProgressRequester.class);
			myBackgroundServiceIntent.addCategory(ORDER_COMPLETION_SERVICE_TAG);
			stopService(myBackgroundServiceIntent);
		}
	}
}