package com.witwatersrand.androidapplication;

public class OrderedItem extends OrderItem {
	Progress state;
	
	OrderedItem() {
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
