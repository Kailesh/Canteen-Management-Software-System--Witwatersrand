/**
 * 
 */
package com.witwatersrand.androidapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author Kailesh
 * 
 */
public class StartMenu extends ListActivity {
	private static final String LOGGER_TAG = "WITWATERSRAND";
	private static final String menuOptions[] = { "Todays Items", "Video Feed",
			"User Information", "Cart", "Current Orders",
			"Simulate State At Time", "Exit", "About Us" };

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOGGER_TAG, "StartMenu -- onCreate()");
		setListAdapter(new ArrayAdapter<String>(StartMenu.this,
				android.R.layout.simple_list_item_1, menuOptions));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView,
	 * android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.i(LOGGER_TAG, "StartMenu -- onListItemClick()");
		String selectedClass = menuOptions[position];

		if (selectedClass.equals("Exit")) {
			finish();
		} else {

			try {
				Class<?> myClass;

				myClass = Class.forName("com.witwatersrand.androidapplication."
						+ selectedClass.replaceAll("\\s", ""));

				Intent myIntent = new Intent(StartMenu.this, myClass);
				startActivity(myIntent);
			} catch (ClassNotFoundException e) {
				Log.d(LOGGER_TAG,
						"ClassNotFoundException Exception -- " + e.toString());
				e.printStackTrace();
			}
		}
	}
}
