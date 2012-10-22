package com.witwatersrand.androidapplication.menuitems;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.witwatersrand.androidapplication.ApplicationPreferences;
import com.witwatersrand.androidapplication.httprequests.HttpGetRequester;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * An activity that validates whether the current menu at the server is updated
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class Menu extends Activity {
	final static private String LOGGER_TAG = "WITWATERSRAND";
	final private String JSON_UPDATE_KEY =  "menuUpdated";
	
	/**
	 * Sends an HTTP request to the server requesting the status of the menu (updated or not)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(LOGGER_TAG, "Menu -- onCreate()");   
        CheckMenuUpdated task = new CheckMenuUpdated();
        task.execute("http://" + ApplicationPreferences.getServerIPAddress(getBaseContext()) + "/yii/index.php/mobile/menuupdate");
    }
    
    /**
	 * A class which handles running the HTTP request on another thread
     * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
     *
     */
    private class CheckMenuUpdated extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... uris) {
			Log.i(LOGGER_TAG, "Menu -- CheckMenuUpdated -- doInBackground()");

			HttpGetRequester requester = new HttpGetRequester(uris[0]);
			return requester.receiveResponse();
		
			// Faking response
			// return "{\"menuUpdated\" : true}";
		}
		
		/**
		 * Executed after the HTTP response has been received or failed. Handles
		 * whether the Items activity should be called or not 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.i(LOGGER_TAG, "Menu -- CheckMenuUpdated -- onPostExecute()");
			Log.i(LOGGER_TAG, "Menu -- CheckMenuUpdated -- onPostExecute() -- result = |" + result + "|");		
			
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(result);
				ApplicationPreferences.setMenuUpdated(Menu.this, (Boolean) jsonObject.get(JSON_UPDATE_KEY));
			} catch (Exception e) {
				e.printStackTrace();
				Log.i(LOGGER_TAG, "Menu -- CheckMenuUpdated -- onPostExecute() -- Exception = |" + e.getMessage() + "|");
			}
			if (ApplicationPreferences.isMenuUpated(Menu.this)) {
				Log.i(LOGGER_TAG, "Menu -- CheckMenuUpdated -- onPostExecute() -- Menu updated");
				Intent myIntent = new Intent("com.witwatersrand.androidapplication.ITEMS");
				startActivity(myIntent);
				finish();
			} else {
				Log.i(LOGGER_TAG, "Menu -- CheckMenuUpdated -- onPostExecute() -- Menu not available");
				Toast.makeText(Menu.this, "An updated version of the menu is not currently available on the server. Please try again later.", Toast.LENGTH_LONG).show();
				finish();
			}
		}
    }
}