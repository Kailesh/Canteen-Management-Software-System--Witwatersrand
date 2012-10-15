package com.witwatersrand.androidapplication.authetication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.witwatersrand.androidapplication.ApplicationPreferences;
import com.witwatersrand.androidapplication.R;
import com.witwatersrand.androidapplication.R.id;
import com.witwatersrand.androidapplication.R.layout;
import com.witwatersrand.androidapplication.R.menu;

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
import android.widget.Toast;

public class Authentication extends Activity implements OnClickListener {
	final private static String LOGGER_TAG = "WITWATERSRAND";
	EditText usernameET, passwordET;
	CheckBox rememberMeCB;
	private String _httpPostMessage;
	private String _username, _password;
	private boolean _remember;
	private static final String NOT_RECEIVED_MESSAGE = "Not received";
	
	private static final String UNKNOWN = "Unknown";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOGGER_TAG, "Authentication -- onCreate()");
		setContentView(R.layout.activity_authentication);
		setUpViewVariables();
	}

	private void setUpViewVariables() {
		Log.i(LOGGER_TAG, "Authentication -- setUpViewVariables()");
		Button loginB = (Button) findViewById(R.id.bLogin);
		loginB.setOnClickListener(this);
		usernameET = (EditText) findViewById(R.id.etUsername);
		passwordET = (EditText) findViewById(R.id.etPassword);
		rememberMeCB = (CheckBox) findViewById(R.id.cbRememberMe);
				
		if (ApplicationPreferences.isUserRemembered(getBaseContext())) {
			_username = ApplicationPreferences.getUserName(getBaseContext());			
			_password = ApplicationPreferences.getPassword(this);
	
			sendAuthenticationRequest();
		} else {
			_username = UNKNOWN;
			_password = UNKNOWN;
			_httpPostMessage = UNKNOWN;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_authentication, menu);
		return true;
	}

	public void onClick(View v) {
		Log.i(LOGGER_TAG, "Authentication -- onClick()");
		switch (v.getId()) {
		case R.id.bLogin:
			_username = usernameET.getText().toString();
			_password = passwordET.getText().toString();
			_remember = rememberMeCB.isChecked();
			
			sendAuthenticationRequest();
			break;
		}
	}
	
	private void sendAuthenticationRequest() {
		AuthenticatorEncoder myEncoder;
		try {
			myEncoder = new AuthenticatorEncoder(_username,
					MD5Encryptor.createMD5(_password), Authentication.this);
			_httpPostMessage = myEncoder.getAuthenticatorJsonMessage();
		} catch (NoSuchAlgorithmException e) {
			Log.i(LOGGER_TAG, "Authentication -- onClick() -- NoSuchAlgorithmException -- " + e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			Log.i(LOGGER_TAG, "Authentication -- onClick() -- UnsupportedEncodingException -- " + e.getMessage());
			e.printStackTrace();
		}
		AuthenticateUser task = new AuthenticateUser();
		
		
		
		task.execute(new String[] { "http://" + ApplicationPreferences.getServerIPAddress(getBaseContext()) + "/yii/index.php/mobile/authenticate" });
	}

	private class AuthenticateUser extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			Log.i(LOGGER_TAG, "Authentication -- AuthenticateUser -- doInBackground()");
			//String jsonAuthenticationString = sendHTTPRequest(urls);
			
			// Faking the HTTP response message	
			String jsonAuthenticationString = "{\"access\": true,\"reason\": \"RMB-OK\",\"balance\": 2445.45}";
			return jsonAuthenticationString;
		}

		private String sendHTTPRequest(String[] urls) {
			Log.i(LOGGER_TAG,
					"Authentication -- AuthenticateUser -- sendHTTPRequest()");
			for (String url : urls) {
				Log.d(LOGGER_TAG, "Inside for inside doInBackground()");
				try {
					int TIMEOUT_MILLISEC = 60000; // = 60 seconds
					HttpParams httpParams = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParams,
							TIMEOUT_MILLISEC);
					HttpConnectionParams.setSoTimeout(httpParams,
							TIMEOUT_MILLISEC);
					HttpClient client = new DefaultHttpClient(httpParams);

					HttpPost myPostRequest = new HttpPost(url);

					StringEntity message = new StringEntity(_httpPostMessage);
					myPostRequest.addHeader("content-type", "applcation/json");
					myPostRequest.setEntity(message);

					HttpResponse myResponse = client.execute(myPostRequest);
					Log.i(LOGGER_TAG,
							"Authentication -- AuthenticateUser -- HTTP request complete");
					if (myResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						Log.i(LOGGER_TAG,
								"Authentication -- AuthenticateUser -- HTTP OK");
						String myJsonString = EntityUtils.toString(myResponse
								.getEntity());
						Log.d(LOGGER_TAG, "sendHTTPRequest() -- myJsonString = " + myJsonString);
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
			Log.d(LOGGER_TAG, "Authentication -- AuthenticateUser -- sendHTTPRequest() -- JSON message not set");
			return NOT_RECEIVED_MESSAGE;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.i(LOGGER_TAG, "Authentication -- AuthenticateUser -- onPostExecute()");
			Log.i(LOGGER_TAG, "Authentication -- AuthenticateUser -- onPostExecute() -- result = " + result);
			
			if (result.equals(NOT_RECEIVED_MESSAGE)) {
				Toast.makeText(Authentication.this, NOT_RECEIVED_MESSAGE, Toast.LENGTH_SHORT).show();
			} else {
				AuthenticationParser myAuthenticationParser;
				myAuthenticationParser = new AuthenticationParser(result);
				
				ApplicationPreferences.setUserName(Authentication.this, _username);
				ApplicationPreferences.setRememberMeStatus(Authentication.this, _remember);
				ApplicationPreferences.setAccountBalance(Authentication.this, myAuthenticationParser.getAccountBalance());
				// TODO Need to MD5 hash with salt
				ApplicationPreferences.setPassword(Authentication.this, _password);

				if (myAuthenticationParser.isAutheticated()) {
					Intent startCanteenApplication = new Intent(
							"com.witwatersrand.androidapplication.STARTMENU");
					startActivity(startCanteenApplication);
					finish();
				} else {
					Toast.makeText(Authentication.this, "Authentication Failure! " + myAuthenticationParser.getReason(), Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}