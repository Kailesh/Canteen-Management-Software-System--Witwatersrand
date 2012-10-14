package com.witwatersrand.androidapplication.progressrequester;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

import com.witwatersrand.androidapplication.ApplicationPreferences;
import com.witwatersrand.androidapplication.CanteenManagerDatabase;
import com.witwatersrand.androidapplication.DeviceIDGenerator;
import com.witwatersrand.androidapplication.OrderItem;
import com.witwatersrand.androidapplication.OrderedItem;
import com.witwatersrand.androidapplication.R;


import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderProgress extends Activity implements OnClickListener {
	final static private String LOGGER_TAG = "WITWATERSRAND";
	private static final String ORDER_KEY = "Order";
	private static final String DEVICE_MAC_ADDRESS = "deviceID";
	private static final String ORDER_NUMBER = "order";
	private static final String MESSAGE_UNKNOWN = "Unknown message";
	TextView orderNameTV;
	int orderNumber;
	ListView _orderLV;
	Button _refreshB;
	RequestProgress task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		Log.i(LOGGER_TAG, "OrderProgress -- onCreate()");
        setContentView(R.layout.activity_order_progress);
        orderNameTV = (TextView) findViewById(R.id.tvOrderName);
        orderNumber = Integer.parseInt(getIntent().getExtras().getString(ORDER_KEY));
        orderNameTV.setText("Order " + orderNumber);
        orderNameTV.setTextColor(Color.parseColor("#add8e6"));
        _orderLV = (ListView) findViewById(R.id.lvProgress);
        _refreshB = (Button) findViewById(R.id.bProgressRefresh);
        _refreshB.setOnClickListener(this);
        _refreshB.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF006400	));
        task = new RequestProgress();
        task.execute(new String[] {"http://" + ApplicationPreferences.getServerIPAddress(getBaseContext()) + "/yii/index.php/mobile/queryprogress"});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_order_progress, menu);
        return true;
    }
    
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.bProgressRefresh:
			if(task.getStatus() == Status.FINISHED) {
				task.execute(new String[] {"http://146.141.125.64/yii/index.php/mobile/queryprogress"});	 
			}
			break;
		}
	}
    
    private class RequestProgress extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- doInBackground()");
			return sendHTTPRequest(urls);
			// Faking response
			// return "[{\"item\": \"Beef Olives\",\"progressStatus\": \"DONE\"},{\"item\": \"Hake\",\"progressStatus\": \"PLACED\"},]";
		}
		
		private String sendHTTPRequest(String... urls) {

			for (String url : urls) {
				Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- doInBackground()");
				try {
					HttpResponse myResponse = httpRequest(url);
					Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- HTTP request complete");

					if (myResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- HTTP OK");
						String myJsonString = EntityUtils.toString(myResponse
								.getEntity());
						Log.d(LOGGER_TAG, "OrderProgress -- RequestProgress -- doInBackground() -- myJsonString = |" + myJsonString + "|");
						return myJsonString;
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.d(LOGGER_TAG, "OrderProgress -- RequestProgress -- doInBackground -- Exception = |" + e.getMessage() + "|");
					//Toast.makeText(getApplicationContext(), "" + e.getMessage() , Toast.LENGTH_SHORT).show();
				}
			}
			Log.d(LOGGER_TAG, "OrderProgress -- RequestProgress -- doInBackground() -- Error has occured");
			return MESSAGE_UNKNOWN;
		}

		private HttpResponse httpRequest(String url) throws ClientProtocolException, IOException {
			Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- httpRequest()");
			int TIMEOUT_MILLISEC = 10000; // = 10 seconds
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient client = new DefaultHttpClient(httpParams);
			HttpPost myPostRequest = new HttpPost(url);
			StringEntity message = new StringEntity(generatePostMessage());
			myPostRequest.addHeader("content-type", "applcation/json");
			myPostRequest.setEntity(message);
			return client.execute(myPostRequest);
		}

		@SuppressWarnings("unchecked")
		private String generatePostMessage() {
			Log.d(LOGGER_TAG, "OrderProgress -- RequestProgress -- generatePostMessage()");

			String progressRequestJsonMessage = "Message Not Set";
			JSONObject myJsonObject = new JSONObject();
			String macAddress = DeviceIDGenerator.getWifiMacAddress(OrderProgress.this);
			myJsonObject.put(DEVICE_MAC_ADDRESS, macAddress);
			myJsonObject.put(ORDER_NUMBER, orderNumber);
			StringWriter myStringWriter = new StringWriter();
			try {
				myJsonObject.writeJSONString(myStringWriter);
				progressRequestJsonMessage = myStringWriter.toString();
				Log.i(LOGGER_TAG, progressRequestJsonMessage);
			} catch (IOException e) {
				Log.i(LOGGER_TAG, e.getMessage());
				e.printStackTrace();
			}
			Log.d(LOGGER_TAG, "OrderProgress -- RequestProgress -- progressRequestJsonMessage = " + progressRequestJsonMessage);

			return progressRequestJsonMessage;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- onPostExecute()");
			if(result == MESSAGE_UNKNOWN) {
				Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- onPostExecute() -- Retrieving progress from database and displaying");
				CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(OrderProgress.this);
				myDatabase.open();
				OrderedItem[] currentOrder = myDatabase.getOrderedItemList(orderNumber);
				myDatabase.close();
				_orderLV.setAdapter(new ItemsProgressAdapter(OrderProgress.this, R.layout.progress_list_item, currentOrder));
	
			} else {
				Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- onPostExecute() -- Updating database with the received progress results and displaying");
				CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(OrderProgress.this);
				myDatabase.open();
				OrderItem [] currentOrder = myDatabase.getOrder(orderNumber);
				ProgressParser myParser = new ProgressParser(result, currentOrder);
				OrderedItem[] myOrder = myParser.getOrderedItemList();
				
				for(int i = 0; i != myOrder.length; i++) {
					myDatabase.updateItemProgress(myOrder[i].getItemName(), orderNumber, myOrder[i].getState());
				}
				myDatabase.close();
				
				_orderLV.setAdapter(new ItemsProgressAdapter(OrderProgress.this, R.layout.progress_list_item, myOrder));
			}
		}
	}

}
