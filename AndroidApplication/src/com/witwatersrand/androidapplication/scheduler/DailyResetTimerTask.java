/**
 * 
 */
package com.witwatersrand.androidapplication.scheduler;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author Kailesh
 *
 */
public class DailyResetTimerTask extends Service {
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("bleh", "Its me!!");
		this.stopSelf();
		return super.onStartCommand(intent, flags, startId);
	}

}
