package com.witwatersrand.androidapplication;

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
		protected String doInBackground(String... params) {
			
			
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
