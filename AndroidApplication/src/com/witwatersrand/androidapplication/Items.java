package com.witwatersrand.androidapplication;

import java.io.IOException;
import java.util.Iterator;

import android.os.Bundle;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

public class Items extends Activity {

	String tag = "KAILESH"; // Debug Purposes

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TableLayout table = new TableLayout(this);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);
		setContentView(table);
		// Row Title
		TableRow rowTitle = new TableRow(this);
		rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);

		// Title
		TextView title = new TextView(this);
		title.setText("RMB Canteen Menu");
		title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
		title.setGravity(Gravity.CENTER);
		title.setTypeface(Typeface.SERIF, Typeface.BOLD);

		TableRow.LayoutParams params = new TableRow.LayoutParams();
		params.span = 4;
		rowTitle.addView(title, params);

		TableRow myRow = new TableRow(this);

		TextView titleItem = new TextView(this);
		titleItem.setText("Item");
		titleItem.setTypeface(Typeface.SANS_SERIF);
		myRow.addView(titleItem);

		TextView titlePrice = new TextView(this);
		titlePrice.setText("Price");
		titlePrice.setTypeface(Typeface.SANS_SERIF);
		myRow.addView(titlePrice);

		TextView titleStation = new TextView(this);
		titleStation.setText("Station");
		titleStation.setTypeface(Typeface.SANS_SERIF);
		myRow.addView(titleStation);

		TextView titleBuy = new TextView(this);
		titleBuy.setText("Buy");
		titleBuy.setTypeface(Typeface.SANS_SERIF);
		myRow.addView(titleBuy);

		table.addView(rowTitle);
		table.addView(myRow);
		
		// ///////////////////////////////////////////////////////////////////////////////////////////////
		// String myJsonMessage = requestMenu();
		String myJsonMessage = "{ \"updated\": \"false\",\"menu\": [{ \"item\": \"Hake\",\"Station\": \"A la Minute Grill\",\"Price\": \"16.53\", \"Availability\": \"true\"},{\"item\": \"Beef Olives\",\"Station\": \"Main Meal\",\"Price\": \"28.50\",\"Availability\": \"false\"},{\"item\": \"Chicken Lasagne & Veg\",\"Station\": \"Frozen Meals\",\"Price\": \"28.50\",\"Availability\": \"true\"}]}";

		Log.d("KAILESH", myJsonMessage);

		JSONParser parser = new JSONParser();
		Object myObject;
		try {
			myObject = parser.parse(myJsonMessage);
			JSONObject jsonObject = (JSONObject) myObject;
			boolean updateStatus = Boolean.parseBoolean((String) jsonObject
					.get("updated"));

			JSONArray menu = (JSONArray) jsonObject.get("menu");
			Iterator<JSONObject> iterator = menu.iterator();
			

			while (iterator.hasNext()) {
				JSONObject currentObject = (JSONObject) iterator.next();
				String itemName = (String) currentObject.get("item");
				Float price = new Float((String) currentObject.get("Price"));
				String stationName = (String) currentObject.get("Station");
				boolean availabilityStatus = Boolean.parseBoolean((String) currentObject.get("Availability"));
				Log.d(tag, itemName + " -- " + stationName + " -- " + availabilityStatus + " -- " + price);
				
				TextView itemNameView = new TextView(this);
				TextView priceView = new TextView(this);
				TextView stationNameView = new TextView(this);
				TableRow currentRow = new TableRow(this);
				Button currentButton = new Button(this);
				
				itemNameView.setText(itemName);
				priceView.setText("" + price);
				stationNameView.setText(stationName);
				currentRow.addView(itemNameView);
				currentRow.addView(priceView);
				currentRow.addView(stationNameView);
				currentRow.addView(currentButton);
								
				table.addView(currentRow);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(tag, e.getMessage());
		} catch (Exception b) {
			Toast.makeText(Items.this, b.toString(), Toast.LENGTH_SHORT).show();
			Log.d(tag, b.getMessage());
		}

	}

	private String requestMenu() {
		// TODO Auto-generated method stub
		try {
			int TIMEOUT_MILLISEC = 10000; // = 10 seconds
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient client = new DefaultHttpClient(httpParams);

			HttpGet myGetRequest = new HttpGet(
					"http://146.141.125.60/yii/index.php/mobile/getmenu");
			HttpResponse myResponse = client.execute(myGetRequest);

			if (myResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// String myString = myResponse.getEntity().getContent().
				String myJsonString = EntityUtils.toString(myResponse
						.getEntity());
				return myJsonString;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception b) {
			b.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_items, menu);
		return true;
	}
}