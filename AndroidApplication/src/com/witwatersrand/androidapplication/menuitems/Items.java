package com.witwatersrand.androidapplication.menuitems;

import android.os.AsyncTask;
import android.os.Bundle;

import com.witwatersrand.androidapplication.ApplicationPreferences;
import com.witwatersrand.androidapplication.CanteenManagerDatabase;
import com.witwatersrand.androidapplication.MenuItem;
import com.witwatersrand.androidapplication.R;
import com.witwatersrand.androidapplication.httprequests.HttpGetRequester;
import com.witwatersrand.androidapplication.httprequests.HttpRequester;

import android.app.Activity;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * The menu items activity
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class Items extends Activity implements OnClickListener{
	final static private String LOGGER_TAG = "WITWATERSRAND";
	ListView _menuLV;
	Button goToCartB;
	public static TextView totalTV;
	private int CART_ACTIVITY_CODE = 0;
	
	/**
	 * Setting up the menu items activty
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOGGER_TAG, "Items -- onCreate()");
		setContentView(R.layout.activity_items);
		
		initializeVariables();
		
		if(ApplicationPreferences.haveMenu(Items.this)) {
			Log.i(LOGGER_TAG, "Items -- onCreate() -- The menu is updated and menu items will be displayed from the database");
			
			CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(this);
			myDatabase.open();
			MenuItem[] myMenu = myDatabase.getMenu();
			myDatabase.close();
			_menuLV.setAdapter(new MenuItemsAdapter(Items.this,
					R.layout.cart_list_item, myMenu));
		
		} else {

			Log.i(LOGGER_TAG, "Items -- onCreate() -- The menu is not-updated and menu items will requested");
			DownloadMenuData task = new DownloadMenuData();
			Log.i(LOGGER_TAG, "Items -- onCreate() -- Calling another thread for the HTTP GET request");
			task.execute("http://" + ApplicationPreferences.getServerIPAddress(getBaseContext()) + "/yii/index.php/mobile/getmenu");
		}
	}
	
	
	private void initializeVariables() {
		Log.i(LOGGER_TAG, "Items -- initializeVariables()");
		_menuLV = (ListView) findViewById(R.id.lvMenuItems);
		goToCartB = (Button) findViewById(R.id.bPurchase);
		goToCartB.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x00006400));	
		goToCartB.setOnClickListener(this);
		totalTV = (TextView) findViewById(R.id.tvMenuItemsTotal);
		totalTV.setText("R " + String.format("%.2f", (float) 0));
	}

	/**
	 * Implements button press logic
	 */
	public void onClick(View arg0) {
		switch(arg0.getId())
		{
		case R.id.bPurchase:
			Intent cartIntent = new Intent("com.witwatersrand.androidapplication.CART");
			startActivityForResult(cartIntent, CART_ACTIVITY_CODE);
			break;
		// More buttons, more cases
		}
	}
	
	/**
	 * Called when the called activity is destroyed
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(LOGGER_TAG, "Items -- onActivityResult()");
		if(resultCode == RESULT_OK && requestCode == CART_ACTIVITY_CODE) {
			if(data.hasExtra("close")) {
				if(data.getExtras().getBoolean("close")) {
					finish();
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * A class which handles running the HTTP request on another thread
	 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
	 *
	 */
	private class DownloadMenuData extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... uris) {
			Log.i(LOGGER_TAG, "Items -- DownloadMenuData -- doInBackground()");
			HttpGetRequester requester = new HttpGetRequester(uris[0]);
			return requester.receiveResponse();
			
			// Fake the menu items
			// String jsonMenuString = "{ \"updated\": \"false\",\"menu\": [{ \"item\": \"Hake\",\"station\": \"A la Minute Grill\",\"price\": \"16.53\", \"availability\": \"true\"},{\"item\": \"Beef Olives\",\"station\": \"Main Meal\",\"price\": \"28.50\",\"availability\": \"true\"},{\"item\": \"Chicken Lasagne & Veg\",\"station\": \"Frozen Meals\",\"price\": \"28.50\",\"availability\": \"true\"}]}";
		}

		/**
		 * Executed after the HTTP response has been received or failed. Displays 
		 * the menu after it has been received.
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		protected void onPostExecute(String response) {
			super.onPostExecute(response);
			Log.i(LOGGER_TAG, "Items -- DownloadMenuData -- onPostExecute()");
			Log.i(LOGGER_TAG, "Items -- DownloadMenuData -- onPostExecute() -- response = " + response);
			
			if (response.equals(HttpRequester.getExceptionThrownMessage())) {
				return;
			}
			if (response.equals(HttpRequester.getResponseNotOkMessage())){
				return;
			}
			MenuParser myMenuParser = new MenuParser(response);
			MenuItem[] myMenu = myMenuParser.getMenu();
			Log.i(LOGGER_TAG, "Items -- DownloadMenuData -- storing in database");
			CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(Items.this);
			myDatabase.open();
			myDatabase.setMenu(myMenu);
			myDatabase.close();
			ApplicationPreferences.setHaveMenu(Items.this, true);
			Log.i(LOGGER_TAG, "Items -- DownloadMenuData -- Calling setListAdapter()");
			// changed purchase_menu_item to cart_list_item
			_menuLV.setAdapter(new MenuItemsAdapter(Items.this,
					R.layout.cart_list_item, myMenu));
		}
	}
}