package com.witwatersrand.androidapplication.authetication;

import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONObject;

import com.witwatersrand.androidapplication.DeviceIDGenerator;

import android.content.Context;
import android.util.Log;

/**
 * Encodes a authetication message to be sent to the server
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class AuthenticatorEncoder {

	final private static String LOGGER_TAG = "WITWATERSRAND";
	final private static String JSON_USERNAME_KEY = "username";
	final private static String JSON_PASSWORD_KEY = "password";
	final private static String JSON_DEVICE_MAC_ADDRESS = "deviceID";
	private String _username;
	private String _password;
	private String _deviceMacAddress;
	Context _context;
	private String _authenticatorJsonMessage;

	
	public AuthenticatorEncoder(String username, String password, Context context) {
		Log.i(LOGGER_TAG, "AuthenticatorEncoder -- Constructor");
		_username = username;
		_password = password;	
		_deviceMacAddress = DeviceIDGenerator.getWifiMacAddress(context);
		_context = context;
		encodeAuthenticatorIntoJson();
	}

	// TODO Unchecked conversion - Type safety: The method put(Object, Object)
	// belongs to the raw type HashMap. References to generic type HashMap<K,V>
	// should be parameterized
	/**
	 * Encodes username, encrypted password and device MAC address into a JSON message
	 */
	@SuppressWarnings("unchecked")
	private void encodeAuthenticatorIntoJson() {
		Log.i(LOGGER_TAG,
				"AuthenticatorEncoder -- encodeAuthenticatorIntoJson()");
		JSONObject myJsonObject = new JSONObject();
		myJsonObject.put(JSON_USERNAME_KEY, _username);
		myJsonObject.put(JSON_PASSWORD_KEY, _password);
		myJsonObject.put(JSON_DEVICE_MAC_ADDRESS, _deviceMacAddress);

		StringWriter myStringWriter = new StringWriter();
		try {
			myJsonObject.writeJSONString(myStringWriter);
			_authenticatorJsonMessage = myStringWriter.toString();
			Log.i(LOGGER_TAG,
					"AuthenticatorEncoder -- encodeAuthenticatorIntoJson() -- "
							+ _authenticatorJsonMessage);
		} catch (IOException e) {
			Log.i(LOGGER_TAG, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Get the JSON message
	 * @return the JSON suthentication message
	 */
	public String getAuthenticatorJsonMessage() {
		return _authenticatorJsonMessage;
	}
}