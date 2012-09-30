/**
 * 
 */
package com.witwatersrand.androidapplication;

import android.util.Log;



/**
 * @author Kailesh
 * 
 */
public class MenuItem {
	String loggerTag = "WITWATERSRAND";
	String _itemName;
	float _price;
	String _stationName;
	boolean _availability;
	int _quantity;


	
	final String tag = "WITWATERSRAND";

	MenuItem(String itemName, float price, String station) {
		Log.i(loggerTag, "MenuItem -- Constructor with parameters");
		this._itemName = itemName;
		this._price = price;
		this._stationName = station;
		this._availability = true;
		this._quantity = 0;
	}
	
	MenuItem() {
		Log.i(loggerTag, "MenuItem -- Constructor");
		this._itemName = "Default-Name";
		this._price = 0;
		this._stationName = "Default-Station";
		this._availability = true;
		this._quantity = 0;
	}
	
	

	/**
	 * @return the _itemame
	 */
	public String getItemname() {
		Log.i(loggerTag, "MenuItem -- getItemname()");
		return _itemName;
	}

	/**
	 * @param _itemame
	 *            the _itemame to set
	 */
	public void setItemName(String _itemame) {
		this._itemName = _itemame;
	}

	/**
	 * @return the _price
	 */
	public float getPrice() {
		//float pr = (Float.valueOf((String) currentObject.get("price"))).floatValue();
		return _price;
	}

	/**
	 * @param _price
	 *            the _price to set
	 */
	public void setPrice(float _price) {
		this._price = _price;
	}

	/**
	 * @return the _stationName
	 */
	public String getStationName() {
		return _stationName;
	}

	/**
	 * @param _stationName
	 *            the _stationName to set
	 */
	public void setStationName(String stationName) {
		this._stationName = stationName;
	}

	/**
	 * @return the _availability
	 */
	public boolean isAvailable() {
		return _availability;
	}

	/**
	 * @param _availability
	 *            the _availability to set
	 */
	public void setAvailability(boolean _availability) {
		this._availability = _availability;
	}

	/**
	 * @return the _quantity
	 */
	public int getQuantity() {
		return _quantity;
	}

	/**
	 * @param _quantity
	 *            the _quantity to set
	 */
	public void setQuantity(int _quantity) {
		this._quantity = _quantity;
	}
}