package com.witwatersrand.androidapplication;

import android.app.Activity;
import android.os.Bundle;

/**
 * Defines an activity which displays the about-me screen
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 */
public class AboutUs extends Activity {

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
	}
}
