package com.witwatersrand.androidapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Manages the application shared preferences
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class ApplicationPreferences {
	
	private static final String LOGGER_TAG = "WITWATERSRAND";
	private static final String APPLIATION_DATA_FILENAME = "preferencesFilename";
	private static final String ORDER_NUMBER_KEY = "order";
	private static final String LATEST_ORDER_TOTAL = "latest_order_total";
	private static final String REMEMBER_ME_KEY = "remember_password";
	private static final String NAME_KEY = "name";
	private static final String PASSWORD_KEY = "password";
	private static final String USER_ACCOUNT_BALANCE_KEY = "account_balance";
	private static final String MENU_UPDATED_KEY = "menu_updated";
	private static final String HAVE_MENU_KEY = "have_menu";
	private static final String SERVER_IP_ADDRESS_KEY = "server_ip_address";
	
	
	private static SharedPreferences getSharedPreferences(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- getPreferences()");
        return context.getSharedPreferences(APPLIATION_DATA_FILENAME, 0);
    }
	
	private static SharedPreferences getPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	

	/**
	 * Get the current order of the order which is still yet to be sent
	 * @param context
	 * @return The current order number
	 */
	public static int getOrderNumber(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- getOrderNumber()");
		Log.d(LOGGER_TAG, "ApplicationPreferences -- getOrderNumber() -- Order number = " + getSharedPreferences(context).getInt(ORDER_NUMBER_KEY, 1));
		return getSharedPreferences(context).getInt(ORDER_NUMBER_KEY, 1);
	}
	
	/**
	 * Set the current order
	 * @param context
	 * @param orderNumber The order number
	 */
	public static void setOrderNumber(Context context, int orderNumber) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setOrderNumber()");
		Log.d(LOGGER_TAG, "ApplicationPreferences -- setOrderNumber() -- Order number = " + getSharedPreferences(context).getInt(ORDER_NUMBER_KEY, 1));
		getSharedPreferences(context).edit().putInt(ORDER_NUMBER_KEY, orderNumber).commit();
	}

	/**
	 * Get the latest order total
	 * @param context
	 * @return latest order total
	 */
	public static float getLatestOrderTotal(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- getLatestOrderTotal()");
		return getSharedPreferences(context).getFloat(LATEST_ORDER_TOTAL, 0);
	}

	/**
	 * Set the latest order total
	 * @param context
	 * @param total the latest order total
	 */
	public static void setLatestOrderTotal(Context context, float total) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setLatestOrderTotal()");
		getSharedPreferences(context).edit().putFloat(LATEST_ORDER_TOTAL, total).commit();
	}
	
	/**
	 * Get the account balance of the user
	 * @param context
	 * @return the account balance
	 */
	public static float getAccountBalance(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- getAccountBalance()");
		return getSharedPreferences(context).getFloat(USER_ACCOUNT_BALANCE_KEY, 0);
	}

	/**
	 * Set the account balance of the user
	 * @param context
	 * @param balance the account balance
	 */
	public static void setAccountBalance(Context context, float balance) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setAccountBalance()");
		getSharedPreferences(context).edit().putFloat(USER_ACCOUNT_BALANCE_KEY, balance).commit();
	}
	
	/**
	 * Check for whether the user credentials are to be remembered
	 * @param context
	 * @return whether the user credentials are to be remembered or not
	 */
	public static boolean isUserRemembered(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- isUserRemembered()");
		return getPreferences(context).getBoolean(REMEMBER_ME_KEY, false);
	}
	
	/**
	 * Set the remember me (for credentials) status
	 * @param context
	 * @param rememberMe remember me boolean
	 */
	public static void setRememberMeStatus(Context context, boolean rememberMe) {
		getPreferences(context).edit().putBoolean(REMEMBER_ME_KEY, rememberMe).commit();
	}

	/**
	 * Get the user name
	 * @param context
	 * @return User name
	 */
	public static String getUserName(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- getUserName()");
		return getPreferences(context).getString(NAME_KEY, "Unknown");
	}
	
	/**
	 * Set the user name
	 * @param context
	 * @param name the user name to set
	 */
	public static void setUserName(Context context, String name) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setUserName()");
		getPreferences(context).edit().putString(NAME_KEY, name).commit();
	}

	/**
	 * Get the password
	 * @param context
	 * @return password
	 */
	public static String getPassword(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- getPassword()");
		return getSharedPreferences(context).getString(PASSWORD_KEY, "Unknown");
	}

	/**
	 * Set the password
	 * @param context
	 * @param password user password
	 */
	public static void setPassword(Context context, String password) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setPassword()");
		getSharedPreferences(context).edit().putString(PASSWORD_KEY, password).commit();
	}
	
	/**
	 * Check for an updated menu
	 * @param context
	 * @return updated menu boolean
	 */
	public static boolean isMenuUpated(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- isMenuUpated()");
		return getSharedPreferences(context).getBoolean(MENU_UPDATED_KEY, false);
	}
	
	/**
	 * Set the updated menu status
	 * @param context
	 * @param updated updated menu boolean
	 */
	public static void setMenuUpdated(Context context, boolean updated) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setMenuUpdated()");
		getSharedPreferences(context).edit().putBoolean(MENU_UPDATED_KEY, updated).commit();
	}
	
	/**
	 * Check for a received menu
	 * @param context
	 * @return received menu boolean
	 */
	public static boolean haveMenu(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- haveMenu()");
		return getSharedPreferences(context).getBoolean(HAVE_MENU_KEY, false);
	}
	
	/**
	 * Set the received menu status
	 * @param context
	 * @param haveMenu received menu boolean
	 */
	public static void setHaveMenu(Context context, boolean haveMenu) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setHaveMenu()");
		getSharedPreferences(context).edit().putBoolean(HAVE_MENU_KEY, haveMenu).commit();
	}
	
	/**
	 * Get the server IP address
	 * @param context
	 * @return IP address of the server running the canteen manager
	 */
	public static String getServerIPAddress(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- getServerIPAddress()");
		return "146.141.125.82";
	}
	
	/**
	 * Set the server IP address
	 * @param context
	 * @param address IP address of the server running the canteen manager
	 */
	public static void setServerIPAddress(Context context, String address) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setServerIPAddress()");
		getPreferences(context).edit().putString(SERVER_IP_ADDRESS_KEY, address).commit();
	}
}