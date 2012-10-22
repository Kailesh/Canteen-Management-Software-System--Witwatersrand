package com.witwatersrand.androidapplication;

import com.witwatersrand.androidapplication.progressrequester.Progress;

import android.util.Log;

/**
 * A class which encapsulates an OrderItem array with a Progress enum array
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class OrderedItemsAdapter {
	final private String LOGGER_TAG = "WITWATERSRAND";
	private OrderItem[] _myOrder;
	private Progress[] _states;
	
	
	public OrderedItemsAdapter(OrderItem[] myOrder) {
		Log.i(LOGGER_TAG, "OrderedItem -- constructor");
		setOrder(myOrder);
		setProgressStates(new Progress[getOrder().length]);
	}

	/**
	 * Get the order items array 
	 * @see OrderItem
	 * @return the _myOrder
	 */
	public OrderItem[] getOrder() {
		return _myOrder;
	}

	/**
	 * Set the order items array 
	 * @see OrderItem
	 * @param myOrder the _myOrder to set
	 */
	public void setOrder(OrderItem[] myOrder) {
		this._myOrder = myOrder;
	}

	/**
	 * Get the progress array
	 * @see Progress
	 * @return the Progress enum array
	 */
	public Progress[] getProgressStates() {
		return _states;
	}

	/**
	 * Get the progress array
	 * @see Progress
	 * @param _states the _states to set
	 */
	public void setProgressStates(Progress[] _states) {
		this._states = _states;
	}
}