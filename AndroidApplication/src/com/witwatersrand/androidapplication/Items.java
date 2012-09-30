package com.witwatersrand.androidapplication;

import java.io.IOException;
import java.util.Iterator;

import android.os.AsyncTask;
import android.os.Bundle;

import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Items extends ListActivity {
	final String loggerTag = "WITWATERSRAND"; // Debug Purposes

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(loggerTag, "after oncreate");

		DownloadMenuData task = new DownloadMenuData();
		Log.d(loggerTag, "002");
		task.execute(new String[] { "http://146.141.125.80/yii/index.php/mobile/getmenu" });
		Log.d(loggerTag, "003");

		// String myJsonMessage =
		// "{ \"updated\": \"false\",\"menu\": [{ \"item\": \"Hake\",\"Station\": \"A la Minute Grill\",\"Price\": \"16.53\", \"Availability\": \"true\"},{\"item\": \"Beef Olives\",\"Station\": \"Main Meal\",\"Price\": \"28.50\",\"Availability\": \"false\"},{\"item\": \"Chicken Lasagne & Veg\",\"Station\": \"Frozen Meals\",\"Price\": \"28.50\",\"Availability\": \"true\"}]}";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_items, menu);
		return true;
	}

	private class DownloadMenuData extends AsyncTask<String, Void, String> {

		String responseString;

		/**
		 * @param responseString
		 *            the responseString to set
		 */
		public void setResponseString(String responseString) {
			Log.d(loggerTag, "Inside setResponseString() where responseString = "
					+ responseString);
			this.responseString = responseString;
		}

		@Override
		protected String doInBackground(String... urls) {
			Log.d(loggerTag, "Inside doInBackground()");

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
					Log.d(loggerTag, "after executing http get");

					if (myResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						Log.d(loggerTag, "response OK");
						// String myString =
						// myResponse.getEntity().getContent().
						String myJsonString = EntityUtils.toString(myResponse
								.getEntity());
						Log.d(loggerTag, myJsonString);
						this.setResponseString(myJsonString);
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
			Log.d(loggerTag, "myJsonMessage = " + myJsonMessage);
			Log.d(loggerTag, "005");

			MenuParser myMenuParser = new MenuParser(myJsonMessage);
			setListAdapter(new MenuItemsAdapter(Items.this, R.layout.activity_items, myMenuParser.getMenu()));
		}
	}
}