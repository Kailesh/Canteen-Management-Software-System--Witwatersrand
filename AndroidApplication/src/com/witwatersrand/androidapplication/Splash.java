package com.witwatersrand.androidapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Splash screen implementation
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class Splash extends Activity {
	private static final String LOGGER_TAG = "WITWATERSRAND";
	
	
	/**
	 * Setting up the activity
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOGGER_TAG, "Splash -- onCreate()");
		setContentView(R.layout.splash);
		
		// TODO Setup scheduler service which executes a task everyday in the morning
		
		Thread timerThread = new Thread() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				Log.i(LOGGER_TAG, "Splash -- onCreate() -- run()");
				try {
					sleep(1500); // 2000 ms = 2 s
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent startCanteenApplication = new Intent("com.witwatersrand.androidapplication.LOGIN");
					startActivity(startCanteenApplication);
				}
				
			}
		};
		
		timerThread.start();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		Log.i(LOGGER_TAG, "Splash -- onPause()");
		super.onPause();
		finish();
	}
}
