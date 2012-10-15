/**
 * 
 */
package com.witwatersrand.androidapplication;

import android.util.Log;

/**
 * @author Kailesh
 *
 */
public class CanteenItem {
	final private String LOGGER_TAG = "WITWATERSRAND";
	
	private String _itemName;
	private float _price;
	private String _stationName;
	
	CanteenItem(String itemName, float price, String station) {
		Log.i(LOGGER_TAG, "CanteenItem -- Constructor with parameters");
		this._itemName = itemName;
		this._price = price;
		this._stationName = station;
	}
	
	CanteenItem() {
		Log.i(LOGGER_TAG, "CanteenItem -- Constructor");
		this._itemName = "Name Not Assigned";
		this._price = 0;
		this._stationName = "Station Not Assigned";
	}
	
	/**
	 * @return the _itemame
	 */
	public String getItemName() {
		Log.i(LOGGER_TAG, "CanteenItem -- getItemName()");
		return _itemName;
	}

	/**
	 * @param _itemame
	 *            the _itemame to set
	 */
	public void setItemName(String _itemame) {
		Log.i(LOGGER_TAG, "CanteenItem -- setItemName()");
		this._itemName = _itemame;
	}

	/**
	 * @return the _price
	 */
	public float getPrice() {
		Log.i(LOGGER_TAG, "CanteenItem -- getPrice()");
		return _price;
	}

	/**
	 * @param _price
	 *            the _price to set
	 */
	public void setPrice(float _price) {
		Log.i(LOGGER_TAG, "CanteenItem -- setPrice()");
		this._price = _price;
	}

	/**
	 * @return the _stationName
	 */
	public String getStationName() {
		Log.i(LOGGER_TAG, "CanteenItem -- getStationName()");
		return _stationName;
	}

	/**
	 * @param _stationName
	 *            the _stationName to set
	 */
	public void setStationName(String stationName) {
		Log.i(LOGGER_TAG, "CanteenItem -- setStationName()");
		this._stationName = stationName;
	}			
}
