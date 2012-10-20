package com.witwatersrand.androidapplication;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class DeviceIDGenerator {
	final private static String LOGGER_TAG = "WITWATERSRAND";
	final private static String UNKNOWN_MAC_ADDRESS = "Unknown MAC Address";
	
	public static String getBlueToothMacAddress() {
		Log.i(LOGGER_TAG, "DeviceIDGenerator -- getBlueToothMacAddress()");
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
	
	
	public static String getWifiMacAddress(Context context) {
		Log.i(LOGGER_TAG, "DeviceIDGenerator -- getWifiMacAddress()");
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInf = wifiManager.getConnectionInfo();
		String macAddr = wifiInf.getMacAddress();
		return macAddr.toUpperCase();
		
		//--------------------|Fake MAC Address|--------------------
		 //return "90:C1:15:BC:97:4F";
		//----------------------------------------------------------
	}
	
}