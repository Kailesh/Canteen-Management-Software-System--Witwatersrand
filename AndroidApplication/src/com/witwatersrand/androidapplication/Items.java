package com.witwatersrand.androidapplication;

import java.io.IOException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class Items extends Activity implements OnClickListener{
	final static private String LOGGER_TAG = "WITWATERSRAND"; // Debug Purposes
	ListView _menuLV;
	Button goToCartB;
	public static TextView totalTV;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOGGER_TAG, "Items -- onCreate()");
		setContentView(R.layout.activity_items);
		_menuLV = (ListView) findViewById(R.id.lvMenuItems);
		goToCartB = (Button) findViewById(R.id.bPurchase);
		goToCartB.setOnClickListener(this);
		totalTV = (TextView) findViewById(R.id.tvMenuItemsTotal);
		totalTV.setText("R " + String.format("%.2f", (float) 0));	
		
		SharedPreferences currentPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean menuUpdated = currentPreferences.getBoolean("menu_updated", false);
		
		if(ApplicationPreferences.haveMenu(Items.this)) {
			Log.i(LOGGER_TAG, "Items -- onCreate() -- The menu is updated and menu items will be displayed from the database");
			
			CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(this);
			myDatabase.open();
			MenuItem[] myMenu = myDatabase.getMenu();
			myDatabase.close();
			_menuLV.setAdapter(new MenuItemsAdapter(Items.this,
					R.layout.purchase_menu_item, myMenu));
		
		} else {

			Log.i(LOGGER_TAG, "Items -- onCreate() -- The menu is not-updated and menu items will requested");
			DownloadMenuData task = new DownloadMenuData();
			Log.i(LOGGER_TAG, "Items -- onCreate() -- Calling another thread for the HTTP GET request");
			task.execute(new String[] {"http://146.141.125.68/yii/index.php/mobile/getmenu"});
		}
	}

	public void onClick(View arg0) {
		switch(arg0.getId())
		{
		case R.id.bPurchase:
			Intent cartIntent = new Intent("com.witwatersrand.androidapplication.CART");
			startActivity(cartIntent);
			break;
		// More buttons, more cases
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_items, menu);
		return true;
	}

	private class DownloadMenuData extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			Log.i(LOGGER_TAG, "Items -- DownloadMenuData -- doInBackground()");
			String jsonMenuString = sendHTTPRequest(urls);
			
			// Fake the menu items
			// String jsonMenuString = "{ \"updated\": \"false\",\"menu\": [{ \"item\": \"Hake\",\"station\": \"A la Minute Grill\",\"price\": \"16.53\", \"availability\": \"true\"},{\"item\": \"Beef Olives\",\"station\": \"Main Meal\",\"price\": \"28.50\",\"availability\": \"true\"},{\"item\": \"Chicken Lasagne & Veg\",\"station\": \"Frozen Meals\",\"price\": \"28.50\",\"availability\": \"true\"}]}";
			return jsonMenuString;
		}

		private String sendHTTPRequest(String... urls) {
			for (String url : urls) {
				Log.i(LOGGER_TAG, "Items -- DownloadMenuData -- doInBackground()");
				try {
					int TIMEOUT_MILLISEC = 10000; // = 10 seconds
					HttpParams httpParams = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParams,
							TIMEOUT_MILLISEC);
					HttpConnectionParams.setSoTimeout(httpParams,
							TIMEOUT_MILLISEC);
					HttpClient client = new DefaultHttpClient(httpParams);

					HttpGet myGetRequest = new HttpGet(url);
					HttpResponse myResponse = client.execute(myGetRequest);
					Log.i(LOGGER_TAG,
							"Items -- DownloadMenuData -- HTTP request complete");

					if (myResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						Log.i(LOGGER_TAG, "Items -- DownloadMenuData -- HTTP OK");
						String myJsonString = EntityUtils.toString(myResponse
								.getEntity());
						Log.d(LOGGER_TAG, myJsonString);
						return myJsonString;
					}
				} catch (IOException e) {
					e.printStackTrace();
					Log.d(LOGGER_TAG, e.getMessage());
				} catch (Exception b) {
					b.printStackTrace();
					Log.d(LOGGER_TAG, b.getMessage());
				}
			}
			return null;
		}

		protected void onPostExecute(String myJsonMessage) {
			super.onPostExecute(myJsonMessage);
			Log.i(LOGGER_TAG, "Items -- DownloadMenuData -- onPostExecute()");
			Log.i(LOGGER_TAG, "Items -- DownloadMenuData -- onPostExecute() -- myJsonMessage = " + myJsonMessage);
			MenuParser myMenuParser = new MenuParser(myJsonMessage);
			MenuItem[] myMenu = myMenuParser.getMenu();
			Log.i(LOGGER_TAG, "Items -- DownloadMenuData -- storing in database");
			CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(Items.this);
			myDatabase.open();
			myDatabase.setMenu(myMenu);
			myDatabase.close();
			ApplicationPreferences.setHaveMenu(Items.this, true);
			Log.i(LOGGER_TAG, "Items -- DownloadMenuData -- Calling setListAdapter()");
			_menuLV.setAdapter(new MenuItemsAdapter(Items.this,
					R.layout.purchase_menu_item, myMenu));
		}
	}
}