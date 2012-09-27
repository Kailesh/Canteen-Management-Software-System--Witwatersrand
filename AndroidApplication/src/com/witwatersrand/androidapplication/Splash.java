/*
 * 
 */
package com.witwatersrand.androidapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Kailesh
 * 
 */
public class Splash extends Activity {

	/**
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread timerThread = new Thread() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				try {
					sleep(1500); // 2000 ms = 2 s
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent startCanteenApplication = new Intent("com.witwatersrand.androidapplication.MENU");
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
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	
}
