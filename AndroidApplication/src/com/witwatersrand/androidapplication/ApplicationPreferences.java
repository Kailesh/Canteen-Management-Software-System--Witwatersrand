/**
 * 
 */
package com.witwatersrand.androidapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * @author Kailesh
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
	
	private static SharedPreferences getSharedPreferences(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- getPreferences()");
        return context.getSharedPreferences(APPLIATION_DATA_FILENAME, 0);
    }
	
	private static SharedPreferences getPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public static int getOrderNumber(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- getOrderNumber()");
		Log.d(LOGGER_TAG, "ApplicationPreferences -- getOrderNumber() -- Order number = " + getSharedPreferences(context).getInt(ORDER_NUMBER_KEY, 1));
		return getSharedPreferences(context).getInt(ORDER_NUMBER_KEY, 1);
	}
	
	public static void setOrderNumber(Context context, int orderNumber) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setOrderNumber()");
		Log.d(LOGGER_TAG, "ApplicationPreferences -- setOrderNumber() -- Order number = " + getSharedPreferences(context).getInt(ORDER_NUMBER_KEY, 1));
		getSharedPreferences(context).edit().putInt(ORDER_NUMBER_KEY, orderNumber).commit();
	}

	public static float getLatestOrderTotal(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- getLatestOrderTotal()");
		return getSharedPreferences(context).getFloat(LATEST_ORDER_TOTAL, 0);
	}

	public static void setLatestOrderTotal(Context context, float total) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setLatestOrderTotal()");
		getSharedPreferences(context).edit().putFloat(LATEST_ORDER_TOTAL, total).commit();
	}
	
	public static float getAccountBalance(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- getAccountBalance()");
		return getSharedPreferences(context).getFloat(USER_ACCOUNT_BALANCE_KEY, 0);
	}

	public static void setAccountBalance(Context context, float balance) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setAccountBalance()");
		getSharedPreferences(context).edit().putFloat(USER_ACCOUNT_BALANCE_KEY, balance).commit();
	}
	
	public static boolean isUserRemembered(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- isUserRemembered()");
		return getPreferences(context).getBoolean(REMEMBER_ME_KEY, false);
	}
	
	public static void setRememberMeStatus(Context context, boolean rememberMe) {
		getPreferences(context).edit().putBoolean(REMEMBER_ME_KEY, rememberMe).commit();
	}

	public static String getUserName(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- getUserName()");
		return getPreferences(context).getString(NAME_KEY, "Unknown");
	}
	
	public static void setUserName(Context context, String name) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setUserName()");
		getPreferences(context).edit().putString(NAME_KEY, name).commit();
	}

	public static String getPassword(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- getPassword()");
		return getSharedPreferences(context).getString(PASSWORD_KEY, "Unknown");
	}

	public static void setPassword(Context context, String password) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setPassword()");
		getSharedPreferences(context).edit().putString(PASSWORD_KEY, password).commit();
	}
	
	public static boolean isMenuUpated(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- isMenuUpated()");
		return getSharedPreferences(context).getBoolean(MENU_UPDATED_KEY, false);
	}
	
	public static void setMenuUpdated(Context context, boolean updated) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setOrderNumber()");
		getSharedPreferences(context).edit().putBoolean(MENU_UPDATED_KEY, updated).commit();
	}
	
	public static boolean haveMenu(Context context) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- haveMenu()");
		return getSharedPreferences(context).getBoolean(HAVE_MENU_KEY, false);
	}
	
	public static void setHaveMenu(Context context, boolean haveMenu) {
		Log.i(LOGGER_TAG, "ApplicationPreferences -- setHaveMenu()");
		getSharedPreferences(context).edit().putBoolean(HAVE_MENU_KEY, haveMenu).commit();
	}
}