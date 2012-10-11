package com.witwatersrand.androidapplication;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

public class DeviceIDGenerator {
	final private static String LOGGER_TAG = "WITWATERSRAND";
	final private static String UNKNOWN_MAC_ADDRESS = "Unknown MAC Address";
	
	public static String getMacAddress() {
		Log.i(LOGGER_TAG, "DeviceIDGenerator -- getMacAddress()");
		try {
			BluetoothAdapter btAdapther; 
			btAdapther = BluetoothAdapter.getDefaultAdapter();
			String deviceMacAddress = btAdapther.getAddress();
			return deviceMacAddress;
		} catch (Exception e) {
			Log.i(LOGGER_TAG, "DeviceIDGenerator -- getMacAddress() -- Exception = |" + e.toString() + "|");
			e.printStackTrace();
		}
		Log.d(LOGGER_TAG,
				"DeviceIDGenerator -- getMacAddress() -- Fatal Error");
		return UNKNOWN_MAC_ADDRESS;
	}
}
