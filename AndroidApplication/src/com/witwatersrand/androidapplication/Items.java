package com.witwatersrand.androidapplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Items extends Activity {

	String tag = "KAILESH";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);

		String myJsonMessage = requestMenu();
		//String myJsonMessage = "{ \"updated\": \"false\",\"menu\": [{ \"ItemName\": \"Hake\",\"Station\": \"A la Minute Grill\",\"Price\": \"16.50\", \"Availability\": \"true\"},{\"ItemName\": \"Beef Olives\",\"Station\": \"Main Meal\",\"Price\": \"28.50\",\"Availability\": \"false\"},{\"ItemName\": \"Chicken Lasagne & Veg\",\"Station\": \"Frozen Meals\",\"Price\": \"28.50\",\"Availability\": \"true\"}]}";

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
					String myString = (String) iterator.next().get("item");
				Log.d(tag,  myString);
				
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(tag,  e.getMessage());
		} catch (Exception b) {
			Toast.makeText(Items.this, b.toString(), Toast.LENGTH_SHORT).show();
			Log.d(tag,  b.getMessage());
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
