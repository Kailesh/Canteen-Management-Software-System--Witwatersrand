package com.witwatersrand.androidapplication;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Authentication extends Activity implements OnClickListener{
	String LOGGER_TAG = "WITWATERSRAND";
	EditText usernameET, passwordET;
	CheckBox rememberMeCB;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        setUpViewVariables();
    }

    private void setUpViewVariables() {
    	Button loginB = (Button) findViewById(R.id.bLogin);
    	loginB.setOnClickListener(this);
    	usernameET = (EditText) findViewById(R.id.etUsername);
    	passwordET = (EditText) findViewById(R.id.etPassword);
    	rememberMeCB = (CheckBox) findViewById(R.id.cbRememberMe);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_authentication, menu);
        return true;
    }

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bLogin:
			String username = usernameET.getText().toString();
			String password = passwordET.getText().toString();
			boolean remember = rememberMeCB.isChecked();
			
			// Send authentication request and retrieve status and account balance 
			/* Setup preferences
			 * set name preference to whatever was given, 
			 * store password as a sharedpreference,
			 * set remember me preference to whatever is given,
			 * store account balance as a sharedpreference,
			 * don't some how create the number of purchase variable for the first time,
			 * don't save the boolean for updated menu for the first time
			 */
			  
			break;
		}
	}
	private class AuthenticateUser extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			
			String jsonAuthenticationString = sendHTTPRequest(urls);
			return jsonAuthenticationString;
		}

		private String sendHTTPRequest(String[] urls) {
			for (String url : urls) {
				Log.d(LOGGER_TAG, "Inside for inside doInBackground()");
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
							"Authentication -- AuthenticateUser -- HTTP request complete");
					if (myResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						Log.i(LOGGER_TAG, "Authentication -- AuthenticateUser -- HTTP OK");
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

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			AuthenticationParser myAuthenticationParser = new AuthenticationParser(result);
			myAuthenticationParser.
			
			Intent startCanteenApplication = new Intent("com.witwatersrand.androidapplication.STARTMENU");
			startActivity(startCanteenApplication);
		}
	}
}
