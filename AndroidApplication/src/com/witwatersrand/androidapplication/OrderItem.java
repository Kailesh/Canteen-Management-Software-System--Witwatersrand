package com.witwatersrand.androidapplication;

import android.util.Log;

/**
 * @author Kailesh
 *
 */
public class OrderItem extends CanteenItem {
	final private String LOGGER_TAG = "WITWATERSRAND";
	private int _purchaseQuantity;

	OrderItem(String itemName, float price, String station) throws InvalidPriceException {
		super(itemName, price, station);
		Log.i(LOGGER_TAG, "OrderItem -- constructor with parameters");
		this._purchaseQuantity = 0;
	}
	
	OrderItem() {
		super();
		Log.i(LOGGER_TAG, "OrderItem -- constructor");
		this._purchaseQuantity = 0;
	}
	
	/**
	 * @return the _purchase_quantity
	 */
	public int getPurchaseQuantity() {
		Log.i(LOGGER_TAG, "OrderItem -- getPurchaseQuantity()");
		return _purchaseQuantity;
	}

	/**
	 * @param _purchase_quantity the _purchase_quantity to set
	 */
	public void setPurchaseQuantity(int quantity) {
		Log.i(LOGGER_TAG, "OrderItem -- setPurchaseQuantity()");
		this._purchaseQuantity = quantity;
	}
}