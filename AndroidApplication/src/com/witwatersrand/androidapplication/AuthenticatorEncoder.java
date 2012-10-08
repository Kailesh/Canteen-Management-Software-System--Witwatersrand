/**
 * 
 */
package com.witwatersrand.androidapplication;

import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONObject;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.Log;


/**
 * @author Kailesh
 * 
 */
public class AuthenticatorEncoder {

	final private static String LOGGER_TAG = "WITWATERSRAND";

	final private static String JSON_USERNAME_KEY = "username";
	final private static String JSON_PASSWORD_KEY = "password";
	final private static String JSON_DEVICE_MAC_ADDRESS = "deviceMacAddress";
	private String _username;
	private String _password;
	private String _deviceMacAddress;
	Context _context;
	
	private BluetoothAdapter btAdapther; 

	private String _authenticatorJsonMessage;

	public AuthenticatorEncoder(String username, String password, Context context) {
		Log.i(LOGGER_TAG, "AuthenticatorEncoder -- Constructor");
		_username = username;
		_password = password;
		_deviceMacAddress = getMacAddress();
		_context = context;
		encodeAuthenticatorIntoJson();
	}

	private String getMacAddress() {
		Log.i(LOGGER_TAG, "AuthenticatorEncoder -- getMacAddress()");
		try {
			btAdapther = BluetoothAdapter.getDefaultAdapter();
			String deviceMacAddress = btAdapther.getAddress();
			return deviceMacAddress;
		} catch (Exception e) {
			Log.i(LOGGER_TAG,
					"AuthenticatorEncoder -- Exception -- " + e.toString());
			e.printStackTrace();
		}
		Log.d(LOGGER_TAG,
				"AuthenticatorEncoder -- getMacAddress() -- Could not retrieve device MAC address");
		return "MAC address not retrievd";
	}

	// TODO Unchecked conversion - Type safety: The method put(Object, Object)
	// belongs to the raw type HashMap. References to generic type HashMap<K,V>
	// should be parameterized
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
	 * @return the _authenticatorJsonMessage
	 */
	public String getAuthenticatorJsonMessage() {
		return _authenticatorJsonMessage;
	}

}