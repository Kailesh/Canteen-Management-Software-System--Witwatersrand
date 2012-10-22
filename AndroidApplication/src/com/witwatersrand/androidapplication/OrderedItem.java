package com.witwatersrand.androidapplication;

import com.witwatersrand.androidapplication.progressrequester.Progress;

/**
 * A class that describes a order item inheriting the properties of a order item
 * @see OrderItem 
 * @see CanteenItem
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class OrderedItem extends OrderItem {
	
	private Progress state;
	

	public OrderedItem() {
		super();
		state = Progress.NONE;
	}

	/**
	 * Get the progress state of an ordered item
	 * @return the state
	 */
	public Progress getState() {
		return state;
	}

	/**
	 * Set the progress state of an ordered item
	 * @param state the state to set
	 */
	public void setState(Progress state) {
		this.state = state;
	}

}
