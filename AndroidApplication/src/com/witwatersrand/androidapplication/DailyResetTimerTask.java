/**
 * 
 */
package com.witwatersrand.androidapplication;

import java.util.TimerTask;

import android.content.Context;

/**
 * @author Kailesh
 *
 */
public class DailyResetTimerTask extends TimerTask {
	
	private Context _context;

	/**
	 * 
	 */
	public DailyResetTimerTask(Context context) {
		_context = context;
	}

	@Override
	public void run() {
		
		// Reset 
		CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(_context);
		myDatabase.open();
		myDatabase.removeAllMenuItems();
		myDatabase.removeAllOrderItems();
		
		ApplicationPreferences.setOrderNumber(_context, 1);
		ApplicationPreferences.setLatestOrderTotal(_context, 0);
		ApplicationPreferences.setMenuUpdated(_context, false);
		ApplicationPreferences.setHaveMenu(_context, false);
	}

}
