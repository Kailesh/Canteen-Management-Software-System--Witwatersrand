/**
 * 
 */
package com.witwatersrand.androidapplication;

import net.technologichron.android.control.NumberPicker;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Kailesh
 * 
 */
public class MenuItemsAdapter extends ArrayAdapter<MenuItem> {
	
	String loggerTag = "WITWATERSRAND";

	private final String _tag = "WITWATERSRAND";
	private final Context _context;
	MenuItem[] _myMenu;
	int _LAYOUT_RESOURCE_ID;

	public MenuItemsAdapter(Context context, int textViewResourceId,
			MenuItem[] menuItems) {
		super(context, textViewResourceId, menuItems);
		Log.i(loggerTag, "MenuItemsAdapter -- Constructor");
		this._context = context;
		this._myMenu = menuItems;
		this._LAYOUT_RESOURCE_ID = textViewResourceId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i(loggerTag, "MenuItemsAdapter getView()");
		LayoutInflater myInflater = (LayoutInflater) _context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowRootView = myInflater.inflate(_LAYOUT_RESOURCE_ID, parent,
				false);
		Log.i(loggerTag, "MenuItemsAdapter -- Inflator called");
		TextView itemNameTV = (TextView) rowRootView
				.findViewById(R.id.tvItemName);
		int TEXT_WIDTH = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 195, getContext().getResources()
						.getDisplayMetrics());
		itemNameTV.setWidth(TEXT_WIDTH);
		itemNameTV.setText(_myMenu[position].getItemname());

		TextView stationNameTV = (TextView) rowRootView
				.findViewById(R.id.tvStationName);
		stationNameTV.setWidth(TEXT_WIDTH);
		stationNameTV.setText(_myMenu[position].getStationName());

		TextView priceTV = (TextView) rowRootView
				.findViewById(R.id.tvPrice);
		priceTV.setText(Float.toString(_myMenu[position].getPrice()));

		// TODO Quantity Picker - find out out how the picker works
		
		final NumberPicker quantityPicker = (NumberPicker) rowRootView.findViewById(R.id.selectedPicker);

		Log.i(loggerTag, "MenuItemsAdapter -- getView() -- quantityPicker.getValue() = " + quantityPicker.getValue());
		
		final int myPosition = position;

		Button mySelectedButton = (Button) rowRootView
				.findViewById(R.id.selectedButton);
		mySelectedButton
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Log.d(loggerTag, "Button pressed for item name: " + _myMenu[myPosition].getItemname());
						_myMenu[myPosition].setQuantity(quantityPicker.getValue());
						Log.d(loggerTag, "_myMenu[myPosition].getQuantity() = " + _myMenu[myPosition].getQuantity());
					}
				});
		Log.i(loggerTag, "MenuItemsAdapter getView() complete");
		return rowRootView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseAdapter#isEnabled(int)
	 */
	@Override
	public boolean isEnabled(int position) {
		Log.i(loggerTag, "MenuItemsAdapter isEnabled()");
		if (_myMenu[position].isAvailable()) {
			return true;
		}
		return false;
	}
}
