/**
 * 
 */
package com.witwatersrand.androidapplication;

import android.util.Log;

/**
 * @author Kailesh
 *
 */
public class OrderedItems {
	final private String LOGGER_TAG = "WITWATERSRAND";
	OrderItem[] _myOrder;
	Progress[] _states;
	
	
	OrderedItems(OrderItem[] myOrder) {
		Log.i(LOGGER_TAG, "OrderedItem -- constructor");
		_myOrder = myOrder;
		_states = new Progress[_myOrder.length];
	}
}
