package com.witwatersrand.androidapplication;

import android.util.Log;


/**
 * A class that describes a menu item inheriting the properties of a canteen item
 * @see CanteenItem
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class MenuItem extends CanteenItem {
	final private String LOGGER_TAG = "WITWATERSRAND";
	private boolean _availability;
	
	public MenuItem(String itemName, float price, String station) throws InvalidPriceException {
		super(itemName, price, station);
		Log.i(LOGGER_TAG, "MenuItem -- Constructor with parameters");
		this._availability = true;
	}
	
	public MenuItem() {
		super();
		Log.i(LOGGER_TAG, "MenuItem -- Constructor");
		this._availability = true;
	}
	
	/**
	 * @return the availability
	 */
	public boolean isAvailable() {
		Log.i(LOGGER_TAG, "MenuItem -- isAvailable");
		return _availability;
	}

	/**
	 * @param availability
	 *            the availability to set
	 */
	public void setAvailability(boolean availability) {
		Log.i(LOGGER_TAG, "MenuItem -- setAvailability()");
		this._availability = availability;
	}
}