/**
 * 
 */
package com.witwatersrand.androidapplication;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.util.Log;

/**
 * @author Kailesh
 *
 */
public class AuthenticationParser {
	final String LOGGER_TAG = "WITWATERSRAND";
//	String JSON_USERNAME_KEY = "username";
//	String JSON_PASSWORD_KEY = "password";
//	String JSON_DEVICE_MAC_ADDRESS = "deviceMacAddress";
	String JSON_AUTHENTICATION_KEY = "authentication";
	String JSON_ACCESS_KEY = "access";
	String JSON_REASON_KEY = "reason";
	String JSON_ACCOUNT_BALANCE = "accountBalance";
	
	JSONObject _jsonObject;
	
	boolean _loginSuccess;
	String _reason;
	float _accountBalance;

	public AuthenticationParser(String jsonAuthenticationMessage) {
		Log.i(LOGGER_TAG, "AuthenticationParser -- Constructor");
		_loginSuccess = false;
		_reason = "";
		_accountBalance = 0;
		
		parseAuthenticationData(jsonAuthenticationMessage);
		Log.i(LOGGER_TAG, "AuthenticationParser contruction complete");
	}


	private void parseAuthenticationData(String jsonAuthenticationMessage) {
		Log.i(LOGGER_TAG, "AuthenticationParser -- parseAuthenticationData() -- Parsing menu data");
		JSONParser parser = new JSONParser();
		try {
			_jsonObject = (JSONObject) parser.parse(jsonAuthenticationMessage);
			
			JSONObject authentication = (JSONObject) _jsonObject.get(JSON_AUTHENTICATION_KEY);
			
			_loginSuccess = (Boolean) authentication.get(JSON_ACCESS_KEY);
			_reason = (String) authentication.get(JSON_REASON_KEY);
			_accountBalance = (Float) _jsonObject.get(JSON_ACCOUNT_BALANCE);
			Log.i(LOGGER_TAG, "AuthenticationParser -- parseAuthenticationData() -- Data parsed");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(LOGGER_TAG, "Exception -- " + e.getMessage());
		}	
	}

	/**
	 * @return the _loginSuccess
	 */
	public boolean isAutheticated() {
		return _loginSuccess;
	}

	/**
	 * @return the _reason
	 */
	public String getReason() {
		return _reason;
	}


	/**
	 * @return the _accountBalance
	 */
	public float getAccountBalance() {
		return _accountBalance;
	}
}