/**
 * @author Kailesh Ramjee
 */
package com.witwatersrand.androidapplication;

import android.util.Log;

/**
 *  A class that describes a canteen item
 */
public class CanteenItem {
	
	final private String LOGGER_TAG = "WITWATERSRAND";
	private String _itemName;
	private float _price;
	private String _stationName;
	
	
	/**
     * A constructor.
     * @param itemName the name of the canteen
     * @param price the price of the canteen item
     * @param station the station that the canteen item belongs to
     * @throws InvalidPriceException
     */
	CanteenItem(String itemName, float price, String station) throws InvalidPriceException {
		Log.i(LOGGER_TAG, "CanteenItem -- Constructor with parameters");
		this._itemName = itemName;
		this.setPrice(price);
		this._stationName = station;
	}
	
	/**
     * A constructor.
     */
	CanteenItem() {
		Log.i(LOGGER_TAG, "CanteenItem -- Constructor");
		this._itemName = "Name Not Assigned";
		this._price = 0;
		this._stationName = "Station Not Assigned";
	}
	
	/**
	 * @return the canteen item name
	 */
	public String getItemName() {
		Log.i(LOGGER_TAG, "CanteenItem -- getItemName()");
		return _itemName;
	}

	/**
	 * @param itemame the canteen item name
	 */
	public void setItemName(String itemame) {
		Log.i(LOGGER_TAG, "CanteenItem -- setItemName()");
		this._itemName = itemame;
	}

	/**
	 * @return the _price
	 */
	public float getPrice() {
		Log.i(LOGGER_TAG, "CanteenItem -- getPrice()");
		return _price;
	}

	/**
	 * @param price the item price
	 * @throws InvalidPriceException 
	 */
	public void setPrice(float price) throws InvalidPriceException {
		Log.i(LOGGER_TAG, "CanteenItem -- setPrice()");
		if(price < 0) {
			throw new InvalidPriceException("The parameter price is negative");
		}
		this._price = price;
	}

	/**
	 * @return the stationName
	 */
	public String getStationName() {
		Log.i(LOGGER_TAG, "CanteenItem -- getStationName()");
		return _stationName;
	}

	/**
	 * @param stationName the station name of the canteen item
	 */
	public void setStationName(String stationName) {
		Log.i(LOGGER_TAG, "CanteenItem -- setStationName()");
		this._stationName = stationName;
	}
}