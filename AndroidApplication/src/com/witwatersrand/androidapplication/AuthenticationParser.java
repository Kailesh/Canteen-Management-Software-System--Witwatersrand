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
	
	final private static String JSON_AUTHENTICATION_KEY = "authentication";
	final private static String JSON_ACCESS_KEY = "access";
	final private static String JSON_REASON_KEY = "reason";
	final private static String JSON_ACCOUNT_BALANCE = "accountBalance";
	
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
						
			_loginSuccess = (Boolean) _jsonObject.get(JSON_ACCESS_KEY);
			_reason = (String) _jsonObject.get(JSON_REASON_KEY);
			// TODO Conversion from double to float happens here
			//_accountBalance = Float. _jsonObject.get(JSON_ACCOUNT_BALANCE));
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
		Log.i(LOGGER_TAG, "AuthenticationParser -- isAutheticated()");
		return _loginSuccess;
	}

	/**
	 * @return the _reason
	 */
	public String getReason() {
		Log.i(LOGGER_TAG, "AuthenticationParser -- getReason()");
		return _reason;
	}

	/**
	 * @return the _accountBalance
	 */
	public float getAccountBalance() {
		Log.i(LOGGER_TAG, "AuthenticationParser -- getAccountBalance()");
		return _accountBalance;
	}
}