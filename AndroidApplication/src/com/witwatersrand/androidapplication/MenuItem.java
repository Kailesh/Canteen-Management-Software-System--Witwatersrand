/**
 * 
 */
package com.witwatersrand.androidapplication;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Kailesh
 * 
 */
public class MenuItem {
	String _itemame;
	float _price;
	String _stationName;
	boolean _availability;
	int _quantity;

	/**
	 * @return the _itemame
	 */
	public String get_itemame() {
		return _itemame;
	}

	/**
	 * @param _itemame
	 *            the _itemame to set
	 */
	public void set_itemame(String _itemame) {
		this._itemame = _itemame;
	}

	/**
	 * @return the _price
	 */
	public float get_price() {
		return _price;
	}

	/**
	 * @param _price
	 *            the _price to set
	 */
	public void set_price(float _price) {
		this._price = _price;
	}

	/**
	 * @return the _stationName
	 */
	public String get_stationName() {
		return _stationName;
	}

	/**
	 * @param _stationName
	 *            the _stationName to set
	 */
	public void set_stationName(String _stationName) {
		this._stationName = _stationName;
	}

	/**
	 * @return the _availability
	 */
	public boolean is_availability() {
		return _availability;
	}

	/**
	 * @param _availability
	 *            the _availability to set
	 */
	public void set_availability(boolean _availability) {
		this._availability = _availability;
	}

	/**
	 * @return the _quantity
	 */
	public int get_quantity() {
		return _quantity;
	}

	/**
	 * @param _quantity
	 *            the _quantity to set
	 */
	public void set_quantity(int _quantity) {
		this._quantity = _quantity;
	}

	MenuItem(String itemName, float price, String station, boolean availability) {
		this._itemame = itemName;
		this._price = price;
		this._stationName = station;
		this._availability = availability;
		this._quantity = 0;
	}

	MenuItem(String itemName, float price, String station) {
		this._itemame = itemName;
		this._price = price;
		this._stationName = station;
		this._availability = true;
		this._quantity = 0;
	}

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};
}
