package com.witwatersrand.androidapplication.menuitems;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.witwatersrand.androidapplication.ApplicationPreferences;
import com.witwatersrand.androidapplication.R;

import android.os.AsyncTask;
import android.os.Bundle;

import android.app.Activity;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class TodaysItems extends Activity {
	final static private String LOGGER_TAG = "WITWATERSRAND"; // Debug Purposes
	String mySelection[] = { "Items" };
	final static private String JSON_UPDATE_KEY =  "menuUpdated";
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckMenuUpdated task = new CheckMenuUpdated();
        task.execute("http://" + ApplicationPreferences.getServerIPAddress(getBaseContext()) + "/yii/index.php/mobile/menuupdate");
    }
    
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_todays_items, menu);
        return true;
    }
    
    private class CheckMenuUpdated extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			Log.i(LOGGER_TAG, "TodaysItems -- CheckMenuUpdated -- doInBackground()");
			// return sendHTTPRequest(urls);
			// Faking response
			return "{\"menuUpdated\" : true}";
		}
		
		private String sendHTTPRequest(String... urls) {
			for (String url : urls) {
				Log.i(LOGGER_TAG, "TodaysItems -- CheckMenuUpdated -- sendHTTPRequest()");
				try {
					HttpResponse myResponse = httpRequest(url);
					Log.i(LOGGER_TAG, "TodaysItems -- CheckMenuUpdated -- HTTP request complete");

					if (myResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						Log.i(LOGGER_TAG, "TodaysItems -- CheckMenuUpdated -- HTTP OK");
						String myJsonString = EntityUtils.toString(myResponse
								.getEntity());
						Log.d(LOGGER_TAG, myJsonString);
						return myJsonString;
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.d(LOGGER_TAG, e.getMessage());
					Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
				}
			}
			Log.d(LOGGER_TAG, "TodaysItems -- CheckMenuUpdated -- doInBackground() -- Error has occured");
			return null;
		}

		private HttpResponse httpRequest(String url) throws ClientProtocolException, IOException {
			Log.i(LOGGER_TAG, "TodaysItems -- CheckMenuUpdated -- httpRequest()");
			int TIMEOUT_MILLISEC = 10000; // = 10 seconds
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient client = new DefaultHttpClient(httpParams);

			HttpGet myGetRequest = new HttpGet(url);
			return client.execute(myGetRequest);
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.i(LOGGER_TAG, "TodaysItems -- CheckMenuUpdated -- onPostExecute()");
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(result);
				ApplicationPreferences.setMenuUpdated(TodaysItems.this, (Boolean) jsonObject.get(JSON_UPDATE_KEY));
			} catch (ParseException e) {
				e.printStackTrace();
				Log.d(LOGGER_TAG, e.getMessage());
				Toast.makeText(TodaysItems.this, e.getMessage() , Toast.LENGTH_SHORT).show();
			}
			if (ApplicationPreferences.isMenuUpated(TodaysItems.this)) {
				Log.i(LOGGER_TAG, "TodaysItems -- CheckMenuUpdated -- onPostExecute() -- Menu available");
//				setListAdapter(new ArrayAdapter<String>(TodaysItems.this,
//						android.R.layout.simple_list_item_1, mySelection));
				Intent myIntent = new Intent("com.witwatersrand.androidapplication.ITEMS");
				startActivity(myIntent);
				finish();
			} else {
				Log.i(LOGGER_TAG, "TodaysItems -- CheckMenuUpdated -- onPostExecute() -- Menu not available");
				Toast.makeText(TodaysItems.this, "An updated version of the menu is not currently available on the server. Please try again later.", Toast.LENGTH_LONG).show();
				finish();
			}
		}
    }
}