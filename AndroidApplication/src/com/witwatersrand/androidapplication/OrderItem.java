package com.witwatersrand.androidapplication;

import android.util.Log;

/**
 * A class that describes a order item inheriting the properties of a canteen item
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
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
	 * Get the purchase quantity of an order item
	 * @return the purchase quantity
	 */
	public int getPurchaseQuantity() {
		Log.i(LOGGER_TAG, "OrderItem -- getPurchaseQuantity()");
		return _purchaseQuantity;
	}

	/**
	 * Get the purchase quantity of an order item
	 * @param quantity the purchase quantity to set
	 */
	public void setPurchaseQuantity(int quantity) {
		Log.i(LOGGER_TAG, "OrderItem -- setPurchaseQuantity()");
		this._purchaseQuantity = quantity;
	}
}