/**
 * 
 */
package com.witwatersrand.androidapplication;

import com.witwatersrand.androidapplication.progressrequester.Progress;

import android.util.Log;

/**
 * @author Kailesh
 *
 */
public class OrderedItems {
	final private String LOGGER_TAG = "WITWATERSRAND";
	private OrderItem[] _myOrder;
	private Progress[] _states;

	
	
	public OrderedItems(OrderItem[] myOrder) {
		Log.i(LOGGER_TAG, "OrderedItem -- constructor");
		setOrder(myOrder);
		setProgressStates(new Progress[getOrder().length]);
	}



	/**
	 * @return the _myOrder
	 */
	public OrderItem[] getOrder() {
		return _myOrder;
	}



	/**
	 * @param _myOrder the _myOrder to set
	 */
	public void setOrder(OrderItem[] _myOrder) {
		this._myOrder = _myOrder;
	}



	/**
	 * @return the _states
	 */
	public Progress[] getProgressStates() {
		return _states;
	}



	/**
	 * @param _states the _states to set
	 */
	public void setProgressStates(Progress[] _states) {
		this._states = _states;
	}
}
