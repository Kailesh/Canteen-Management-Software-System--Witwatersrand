package com.witwatersrand.androidapplication;

import com.witwatersrand.androidapplication.progressrequester.Progress;

public class OrderedItem extends OrderItem {
	Progress state;
	
	public OrderedItem() {
		super();
		state = Progress.NONE;
	}

	/**
	 * @return the state
	 */
	public Progress getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Progress state) {
		this.state = state;
	}

}
