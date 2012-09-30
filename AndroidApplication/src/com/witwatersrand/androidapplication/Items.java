package com.witwatersrand.androidapplication;

import java.io.IOException;

import android.os.AsyncTask;
import android.os.Bundle;

import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class Items extends ListActivity {
	final String loggerTag = "WITWATERSRAND"; // Debug Purposes

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(loggerTag, "Items -- onCreate()");

		DownloadMenuData task = new DownloadMenuData();
		Log.i(loggerTag, "Items -- Calling another thread for the HTTP request");
		task.execute(new String[] { "http://146.141.125.80/yii/index.php/mobile/getmenu" });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_items, menu);
		return true;
	}

	private class DownloadMenuData extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			Log.i(loggerTag, "Items -- DownloadMenuData -- doInBackground()");

			// String jsonMenuString = sendHTTPRequest(urls);
			String jsonMenuString = "{ \"updated\": \"false\",\"menu\": [{ \"item\": \"Hake\",\"station\": \"A la Minute Grill\",\"price\": \"16.53\", \"availability\": \"true\"},{\"item\": \"Beef Olives\",\"station\": \"Main Meal\",\"price\": \"28.50\",\"availability\": \"false\"},{\"item\": \"Chicken Lasagne & Veg\",\"station\": \"Frozen Meals\",\"price\": \"28.50\",\"availability\": \"true\"}]}";

			return jsonMenuString;
		}

		private String sendHTTPRequest(String... urls) {
			for (String url : urls) {
				Log.d(loggerTag, "Inside for inside doInBackground()");
				try {
					Log.d(loggerTag, "Inside try inside doInBackground()");
					int TIMEOUT_MILLISEC = 10000; // = 10 seconds
					HttpParams httpParams = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParams,
							TIMEOUT_MILLISEC);
					HttpConnectionParams.setSoTimeout(httpParams,
							TIMEOUT_MILLISEC);
					HttpClient client = new DefaultHttpClient(httpParams);

					HttpGet myGetRequest = new HttpGet(url);
					HttpResponse myResponse = client.execute(myGetRequest);
					Log.i(loggerTag,
							"Items -- DownloadMenuData -- HTTP request complete");

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
				}
			}
			return null;
		}

		protected void onPostExecute(String myJsonMessage) {
			super.onPostExecute(myJsonMessage);
			Log.i(loggerTag, "Items -- DownloadMenuData -- onPostExecute()");
			Log.i(loggerTag, "Items -- DownloadMenuData -- myJsonMessage = "
					+ myJsonMessage);
			MenuParser myMenuParser = new MenuParser(myJsonMessage);
			Log.i(loggerTag,
					"Items -- DownloadMenuData -- Calling setListAdapter()");
			setListAdapter(new MenuItemsAdapter(Items.this,
					R.layout.activity_items, myMenuParser.getMenu()));
		}
	}
}