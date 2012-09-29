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
	String tag = "KAILESH"; // Debug Purposes

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(tag, "after oncreate");

		DownloadMenuData task = new DownloadMenuData();
		Log.d(tag, "002");
		task.execute(new String[] { "http://146.141.125.80/yii/index.php/mobile/getmenu" });
		Log.d(tag, "003");

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
			Log.d(tag, "Inside setResponseString() where responseString = "
					+ responseString);
			this.responseString = responseString;
		}

		@Override
		protected String doInBackground(String... urls) {
			Log.d(tag, "Inside doInBackground()");

			for (String url : urls) {
				Log.d(tag, "Inside for inside doInBackground()");
				try {
					Log.d(tag, "Inside try inside doInBackground()");
					int TIMEOUT_MILLISEC = 10000; // = 10 seconds
					HttpParams httpParams = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParams,
							TIMEOUT_MILLISEC);
					HttpConnectionParams.setSoTimeout(httpParams,
							TIMEOUT_MILLISEC);
					HttpClient client = new DefaultHttpClient(httpParams);

					HttpGet myGetRequest = new HttpGet(url);
					HttpResponse myResponse = client.execute(myGetRequest);
					Log.d(tag, "after executing http get");

					if (myResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						Log.d(tag, "response OK");
						// String myString =
						// myResponse.getEntity().getContent().
						String myJsonString = EntityUtils.toString(myResponse
								.getEntity());
						Log.d(tag, myJsonString);
						this.setResponseString(myJsonString);
						return myJsonString;
					}
				} catch (IOException e) {
					e.printStackTrace();
					Log.d(tag, e.getMessage());
				} catch (Exception b) {
					b.printStackTrace();
					Log.d(tag, b.getMessage());
				}
			}

			return null;
		}

		protected void onPostExecute(String myJsonMessage) {
			Log.d(tag, "myJsonMessage = " + myJsonMessage);
			Log.d(tag, "responseString = " + responseString);
			Log.d(tag, "005");

			try {
				Log.d(tag, "005.1");

				// TODO Place this parsing functionality in its own class or
				// function

				JSONParser parser = new JSONParser();
				Object myObject;
				Log.d(tag, "005.2");
				myObject = parser.parse(myJsonMessage);
				Log.d(tag, "005.3");
				JSONObject jsonObject = (JSONObject) myObject;
				Log.d(tag, "005.4");
				boolean updateStatus = Boolean.parseBoolean((String) jsonObject
						.get("updated"));
				Log.d(tag, "" + updateStatus);
				JSONArray menu = (JSONArray) jsonObject.get("menu");

				Iterator<?> menuIterator = menu.iterator(); // Infer a generic
															// type and cast the
															// returns of the
															// iterator methods
															// in the loop.
															// Cannot cast
															// menuIterator to
															// Iterator<JSONObject>
															// here

				while (menuIterator.hasNext()) {

					JSONObject currentObject = (JSONObject) menuIterator.next();
					String itemName = (String) currentObject.get("item");

					float price = (Float.valueOf((String) currentObject
							.get("price"))).floatValue();
					String stationName = (String) currentObject.get("station");
					boolean availabilityStatus = Boolean
							.parseBoolean((String) currentObject
									.get("availability"));
					Log.d(tag, itemName + " -- " + stationName + " -- "
							+ availabilityStatus + " -- " + price);
					Log.d(tag, "---");

					// TODO Place items in a MenuItem[]

					// setListAdapter(new MenuItemsAdapter(this, myMenu));
				}

			} catch (ParseException e) {
				e.printStackTrace();
				Log.d(tag, e.getMessage());
			} catch (Exception b) {
				Toast.makeText(Items.this, b.toString(), Toast.LENGTH_SHORT)
						.show();
				Log.d(tag, b.getMessage());
			}
		}
	}
}