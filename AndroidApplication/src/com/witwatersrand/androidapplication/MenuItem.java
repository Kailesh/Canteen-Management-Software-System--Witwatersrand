/**
 * 
 */
package com.witwatersrand.androidapplication;

import android.util.Log;



/**
 * @author Kailesh
 * 
 */
public class MenuItem extends CanteenItem {
	final private String LOGGER_TAG = "WITWATERSRAND";
	private boolean _availability;
	
	MenuItem(String itemName, float price, String station) {
		super(itemName, price, station);
		Log.i(LOGGER_TAG, "MenuItem -- Constructor with parameters");
		this._availability = true;
	}
	
	MenuItem() {
		super();
		Log.i(LOGGER_TAG, "MenuItem -- Constructor");
		this._availability = true;
	}
	
	/**
	 * @return the _availability
	 */
	public boolean isAvailable() {
		Log.i(LOGGER_TAG, "MenuItem -- isAvailable");
		return _availability;
	}

	/**
	 * @param _availability
	 *            the _availability to set
	 */
	public void setAvailability(boolean _availability) {
		Log.i(LOGGER_TAG, "MenuItem -- setAvailability()");
		this._availability = _availability;
	}
}