/**
 * 
 */
package com.witwatersrand.androidapplication.startmenu;


import com.witwatersrand.androidapplication.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * @author Kailesh
 * 
 */
public class StartMenu extends ListActivity  {
	private static final String LOGGER_TAG = "WITWATERSRAND";
	StartMenuItem[] _myOptions;
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOGGER_TAG, "StartMenu -- onCreate()");

		_myOptions = new StartMenuItem[8];
		
		_myOptions[0] = new StartMenuItem("Today's Items", "food");
		_myOptions[1] = new StartMenuItem("Video Feed", "video");
		_myOptions[2] = new StartMenuItem("User Information", "user_grey");
		_myOptions[3] = new StartMenuItem("Cart", "shopping_cart");
		_myOptions[4] = new StartMenuItem("Current Orders", "hamburger");
		_myOptions[5] = new StartMenuItem("Simulate State At Time", "time");
		_myOptions[6] = new StartMenuItem("Exit", "food_grey");
		_myOptions[7] = new StartMenuItem("About Us", "man");
		
		setListAdapter(new StartMenuAdapter(StartMenu.this, R.layout.start_menu_list_item, _myOptions));
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
		String selectedClass = _myOptions[position].getOptionName();

		if (selectedClass.equals("Exit")) {
			finish();
		} else {

			try {
				Intent myIntent = new Intent("com.witwatersrand.androidapplication." + selectedClass.replaceAll("\\s", "").replace("'", "").toUpperCase());
				startActivity(myIntent);
			} catch (Exception e) {
				Log.d(LOGGER_TAG, "Exception -- " + e.toString());
				e.printStackTrace();
			}
		}
	}
	
	
	
}
