// Author: Kailesh Ramjee
// University of Witwatersrand
package com.witwatersrand.androidapplication;

import java.io.IOException;

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


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {
	private static final String TAG = "MainActivity";
	Button myButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initialise();
		Toast.makeText(Main.this, "App started and initialised", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	private void initialise() {
		// TODO Auto-generated method stub
		myButton = (Button) findViewById(R.id.clickMeButton);
		myButton.setOnClickListener(this);
		Log.i(TAG, "Activity elements initialised");
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//Toast toast = Toast.makeText(MainActivity.this,	"Not initialised", Toast.LENGTH_SHORT);
		
		switch (v.getId()) {
		case R.id.clickMeButton:
			try {
				int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
				HttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpClient client = new DefaultHttpClient(httpParams);			
				
				HttpPost myPostRequest = new HttpPost("http://146.141.125.78/yii/index.php/mobile/getmenu");
				
				String requestJSONString = "{\"function\": \"menu\",\"deviceID\": \"android2192393\"}";
				myPostRequest.setEntity(new ByteArrayEntity(requestJSONString.toString().getBytes("UTF8")));
				HttpResponse myResponse = client.execute(myPostRequest);
				Toast.makeText(Main.this, myResponse.getStatusLine().toString(), Toast.LENGTH_SHORT).show();
				
				if (myResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					//String myString  = myResponse.getEntity().getContent().
					String myString = EntityUtils.toString(myResponse.getEntity());
					Toast.makeText(Main.this, myString, Toast.LENGTH_SHORT).show();
				}
			} catch (IOException e) {
				Toast.makeText(Main.this, e.toString(), Toast.LENGTH_SHORT).show();
			} catch (Exception b) {
				Toast.makeText(Main.this, "Exception b", Toast.LENGTH_SHORT).show();
				Toast.makeText(Main.this, b.getMessage(), Toast.LENGTH_LONG).show();
				Toast.makeText(Main.this, b.toString(), Toast.LENGTH_LONG).show();
			}
			Toast.makeText(Main.this, "Button processed", Toast.LENGTH_SHORT).show();
		}
	}
}