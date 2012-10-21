package com.witwatersrand.androidapplication;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Retrieves the MAC address of the device
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class DeviceIDGenerator {
	final private static String LOGGER_TAG = "WITWATERSRAND";
	
	/**
	 * Get the MAC address of the device
	 * @param context
	 * @return the MAC address of the device
	 */
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