package com.witwatersrand.androidapplication.progressrequester;

import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONObject;

import com.witwatersrand.androidapplication.ApplicationPreferences;
import com.witwatersrand.androidapplication.CanteenManagerDatabase;
import com.witwatersrand.androidapplication.DeviceIDGenerator;
import com.witwatersrand.androidapplication.OrderItem;
import com.witwatersrand.androidapplication.OrderedItem;
import com.witwatersrand.androidapplication.R;
import com.witwatersrand.androidapplication.httprequests.HttpPostRequester;
import com.witwatersrand.androidapplication.httprequests.HttpRequester;


import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * An activity that retrieves and displays the the prgress of each item of an order
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class OrderProgress extends Activity implements OnClickListener {
	final static private String LOGGER_TAG = "WITWATERSRAND";
	private static final String ORDER_KEY = "Order";
	private static final String DEVICE_MAC_ADDRESS = "deviceID";
	private static final String ORDER_NUMBER = "order";
	TextView _orderNameTV;
	int _orderNumber;
	ListView _orderLV;
	Button _refreshB;
	RequestProgress task;
	
	
	/**
	 * Setting up the activity
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		Log.i(LOGGER_TAG, "OrderProgress -- onCreate()");
        setContentView(R.layout.activity_order_progress);
        _orderNameTV = (TextView) findViewById(R.id.tvOrderName);
        _orderNumber = Integer.parseInt(getIntent().getExtras().getString(ORDER_KEY));
        _orderNameTV.setText("Order " + _orderNumber);
        _orderNameTV.setTextColor(Color.parseColor("#add8e6"));
        _orderLV = (ListView) findViewById(R.id.lvProgress);
        _refreshB = (Button) findViewById(R.id.bProgressRefresh);
        _refreshB.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF00640));
        _refreshB.setOnClickListener(this);
       
        task = new RequestProgress();
        task.execute(new String[] {"http://" + ApplicationPreferences.getServerIPAddress(OrderProgress.this) + "/yii/index.php/mobile/queryprogress"});
    }
    
    /**
     * Removes the refresh button if all items of a order have progress DONE or DELIVERED
     */
    private void removeRefreshButtonIfRequired() {
    	CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(this);
        myDatabase.open();        
        if (myDatabase.isOrderReceived(_orderNumber)) {
        	myDatabase.close();
        	_refreshB.setVisibility(View.GONE);
        } 
        myDatabase.close();
    }
    
    /**
	 * Implementation of business logic for a button press
	 */
	public void onClick(View v) {
		Log.i(LOGGER_TAG, "OrderProgress -- onClick() -- Button Pressed");
		switch(v.getId()) {
		case R.id.bProgressRefresh:
			Log.i(LOGGER_TAG, "OrderProgress -- onClick() -- bProgressRefresh Pressed");	
			if(task.getStatus() == Status.FINISHED) {
				Log.i(LOGGER_TAG, "OrderProgress -- onClick() -- Finished retrieving the status");
				RequestProgress anotherTask = new RequestProgress();
				anotherTask.execute(new String[] {"http://" + ApplicationPreferences.getServerIPAddress(OrderProgress.this) + "/yii/index.php/mobile/queryprogress"});	 
			} else {
				Log.i(LOGGER_TAG, "OrderProgress -- onClick() -- Not finished retrieving the status");
			}
			break;
		}
	}
	
	/**
	 * Display the progress from the local database
	 */
	public void displayLocalProgress() {
		Log.i(LOGGER_TAG, "OrderProgress -- displayLocalProgress()");
		Log.i(LOGGER_TAG, "OrderProgress -- displayLocalProgress() -- Retrieving progress from database and displaying");
		CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(OrderProgress.this);
		myDatabase.open();
		OrderedItem[] currentOrder = myDatabase.getOrderedItemList(_orderNumber);
		myDatabase.close();
		_orderLV.setAdapter(new ItemsProgressAdapter(OrderProgress.this, R.layout.progress_list_item, currentOrder));	
	}
    
	/**
	 * A class which handles running the HTTP request on another thread
	 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
	 *
	 */
    private class RequestProgress extends AsyncTask<String, Void, String> {

    	/**
		 * Calls a HTTP requester and makes the request
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... urls) {
			Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- doInBackground()");
			
			HttpPostRequester requester = new HttpPostRequester(urls[0]);
			requester.setPostMessage(generatePostMessage());
			return requester.receiveResponse();
			
			// Faking response
			// return "[{\"item\": \"Beef Olives\",\"progressStatus\": \"DONE\"},{\"item\": \"Hake\",\"progressStatus\": \"PLACED\"},]";
		}
		

		@SuppressWarnings("unchecked")
		private String generatePostMessage() {
			Log.d(LOGGER_TAG, "OrderProgress -- RequestProgress -- generatePostMessage()");

			String progressRequestJsonMessage = "Message Not Set";
			JSONObject myJsonObject = new JSONObject();
			String macAddress = DeviceIDGenerator.getWifiMacAddress(OrderProgress.this);
			myJsonObject.put(DEVICE_MAC_ADDRESS, macAddress);
			myJsonObject.put(ORDER_NUMBER, _orderNumber);
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

		/**
		 * Executed after the HTTP response has been received or failed. Parses,
		 * stores and displays the progress of each item of the order.
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String response) {
			super.onPostExecute(response);
			Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- onPostExecute()");
			Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- onPostExecute() -- response = |" + response + "|");

			if (response.equals(HttpRequester.getResponseNotOkMessage())) {
				Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- onPostExecute() -- Http not OK");
				Toast.makeText(OrderProgress.this, HttpRequester.getResponseNotOkMessage(), Toast.LENGTH_SHORT).show();
				displayLocalProgress();
				return;
			}
			
			if (response.equals(HttpRequester.getExceptionThrownMessage())) {
				Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- onPostExecute() -- Exception thrown when  attempting to receive response");
				Toast.makeText(OrderProgress.this, HttpRequester.getExceptionThrownMessage(), Toast.LENGTH_SHORT).show();
				displayLocalProgress();
				return;
			} 

			Log.i(LOGGER_TAG, "OrderProgress -- RequestProgress -- onPostExecute() -- Updating database with the received progress results and displaying");
			CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(OrderProgress.this);
			myDatabase.open();
			OrderItem [] currentOrder = myDatabase.getOrder(_orderNumber);
			ProgressParser myParser = new ProgressParser(response, currentOrder);
			OrderedItem[] myOrder = myParser.getOrderedItemList();

			for(int i = 0; i != myOrder.length; i++) {
				myDatabase.updateItemProgress(myOrder[i].getItemName(), _orderNumber, myOrder[i].getState());
			}
			myDatabase.close();
			removeRefreshButtonIfRequired();
			_orderLV.setAdapter(new ItemsProgressAdapter(OrderProgress.this, R.layout.progress_list_item, myOrder));
		}
	}
}