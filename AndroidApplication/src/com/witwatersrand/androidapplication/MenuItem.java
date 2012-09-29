/**
 * 
 */
package com.witwatersrand.androidapplication;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Kailesh
 * 
 */
public class MenuItem {
	String _itemName;
	float _price;
	String _stationName;
	boolean _availability;
	int _quantity;
	
	final String tag = "WITWATERSRAND";
	
	MenuItem(String itemName, float price, String station, boolean availability) {
		this._itemName = itemName;
		this._price = price;
		this._stationName = station;
		this._availability = availability;
		this._quantity = 0;
	}

	MenuItem(String itemName, float price, String station) {
		this._itemName = itemName;
		this._price = price;
		this._stationName = station;
		this._availability = true;
		this._quantity = 0;
	}
	
	OnClickListener addToCartListener = new OnClickListener() {
		public void onClick(View v) {
			Log.d(tag, "Button pressed for item: " + _itemName);
			Log.d(tag, "Price: " + _price);
			Log.d(tag, "Station: " + _stationName);
			Log.d(tag, "Availability: " + _availability);
			Log.d(tag, "Quantity: " + _quantity);
		}
	};

	/**
	 * @return the _itemame
	 */
	public String getItemname() {
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
	public void setStationName(String _stationName) {
		this._stationName = _stationName;
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