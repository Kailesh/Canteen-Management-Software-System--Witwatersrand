package com.witwatersrand.androidapplication.authetication;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.util.Log;

/**
 * Parses a authentication data from an HTTP response 
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class AuthenticationParser {
	final String LOGGER_TAG = "WITWATERSRAND";
	final private static String JSON_ACCESS_KEY = "access";
	final private static String JSON_REASON_KEY = "reason";
	final private static String JSON_ACCOUNT_BALANCE = "balance";
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

	/**
	 * Parse the authentication data including the status, the reason, and the users account balance
	 * @param jsonAuthenticationMessage
	 */
	private void parseAuthenticationData(String jsonAuthenticationMessage) {
		Log.i(LOGGER_TAG, "AuthenticationParser -- parseAuthenticationData() -- Parsing menu data");
		JSONParser parser = new JSONParser();
		try {
			_jsonObject = (JSONObject) parser.parse(jsonAuthenticationMessage);
						
			_loginSuccess = (Boolean) _jsonObject.get(JSON_ACCESS_KEY);
			_reason = (String) _jsonObject.get(JSON_REASON_KEY);
			// TODO Conversion from double to float happens here
			String tempBalance = "" + _jsonObject.get(JSON_ACCOUNT_BALANCE);
			_accountBalance = Float.parseFloat(tempBalance);
			Log.i(LOGGER_TAG, "AuthenticationParser -- parseAuthenticationData() -- Data parsed");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(LOGGER_TAG, "Exception -- " + e.getMessage());
		}	
	}

	/**
	 * Check whether the user's authentication is successful
	 * @return the true if the user has successfully been authenticated
	 */
	public boolean isAutheticated() {
		Log.i(LOGGER_TAG, "AuthenticationParser -- isAutheticated()");
		return _loginSuccess;
	}

	
	/**
	 * Get the reason (failure code) for authentication failure
	 * @see defined error codes
	 * @return the reason for authentication failure (failure code)
	 */
	public String getReason() {
		Log.i(LOGGER_TAG, "AuthenticationParser -- getReason()");
		return _reason;
	}

	/**
	 * Get the user account balance
	 * @return the user account balance
	 */
	public float getAccountBalance() {
		Log.i(LOGGER_TAG, "AuthenticationParser -- getAccountBalance()");
		return _accountBalance;
	}
	
	public boolean isAuthenticationMessageValid(String message) {
		// TODO implement this
		return false;	
	}
}