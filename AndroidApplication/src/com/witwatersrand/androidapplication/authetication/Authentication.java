package com.witwatersrand.androidapplication.authetication;

import com.witwatersrand.androidapplication.ApplicationPreferences;
import com.witwatersrand.androidapplication.R;
import com.witwatersrand.androidapplication.httprequests.HttpPostRequester;
import com.witwatersrand.androidapplication.httprequests.HttpRequester;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.LightingColorFilter;
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
	
	private static final String UNKNOWN = "Unknown";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOGGER_TAG, "Authentication -- onCreate()");
		setContentView(R.layout.activity_authentication);
		setUpViewVariables();
		sendRequestIfRemembered();
	}


	private void setUpViewVariables() {
		Log.i(LOGGER_TAG, "Authentication -- setUpViewVariables()");
		Button loginB = (Button) findViewById(R.id.bLogin);
		loginB.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF006400));
		loginB.setOnClickListener(this);
		usernameET = (EditText) findViewById(R.id.etUsername);
		passwordET = (EditText) findViewById(R.id.etPassword);
		rememberMeCB = (CheckBox) findViewById(R.id.cbRememberMe);
	}
	
	
	private void sendRequestIfRemembered() {
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
		Log.i(LOGGER_TAG, "Authentication -- sendAuthenticationRequest()");
		AuthenticatorEncoder myEncoder;
		try {
			myEncoder = new AuthenticatorEncoder(_username, MD5Encryptor.createMD5(_password), Authentication.this);
			_httpPostMessage = myEncoder.getAuthenticatorJsonMessage();
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(LOGGER_TAG, "Authentication -- onClick() -- Exception = |" + e.getMessage() + "|");
		}
		AuthenticateUser task = new AuthenticateUser();
		task.execute("http://" + ApplicationPreferences.getServerIPAddress(getBaseContext()) + "/yii/index.php/mobile/authenticate");
	}

	private class AuthenticateUser extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... uris) {
			Log.i(LOGGER_TAG, "Authentication -- AuthenticateUser -- doInBackground()");
			
			HttpPostRequester requester = new HttpPostRequester(uris[0]);
			requester.setPostMessage(_httpPostMessage);
			return requester.receiveResponse();
			
			// Faking the HTTP response message	
			// return "{\"access\": true,\"reason\": \"RMB-OK\",\"balance\": 2445.45}";
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
			
			if (result.equals(HttpRequester.getResponseNotOkMessage())) {
				Log.i(LOGGER_TAG, "Authentication -- AuthenticateUser -- onPostExecute() -- Http not OK");
				Toast.makeText(Authentication.this, HttpRequester.getResponseNotOkMessage(), Toast.LENGTH_SHORT).show();
				return;
			} 
			
			if (result.equals(HttpRequester.getExceptionThrownMessage())) {
				Log.i(LOGGER_TAG, "Authentication -- AuthenticateUser -- onPostExecute() -- Exception thrown when  attempting to receive response");
				Toast.makeText(Authentication.this, HttpRequester.getExceptionThrownMessage(), Toast.LENGTH_SHORT).show();
				return;
			} 

			AuthenticationParser myAuthenticationParser;
			myAuthenticationParser = new AuthenticationParser(result);

			ApplicationPreferences.setUserName(Authentication.this, _username);
			ApplicationPreferences.setRememberMeStatus(Authentication.this, _remember);
			ApplicationPreferences.setAccountBalance(Authentication.this, myAuthenticationParser.getAccountBalance());

			// TODO Need to MD5 hash with salt
			ApplicationPreferences.setPassword(Authentication.this, _password);

			if (myAuthenticationParser.isAutheticated()) {
				Toast.makeText(Authentication.this, "Login Success!", Toast.LENGTH_SHORT).show();

				Intent startCanteenApplication = new Intent("com.witwatersrand.androidapplication.STARTMENU");
				startActivity(startCanteenApplication);
				finish();
			} else {
				Toast.makeText(Authentication.this, "Authentication Failure! " + myAuthenticationParser.getReason(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}