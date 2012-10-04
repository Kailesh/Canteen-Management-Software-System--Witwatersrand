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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Kailesh
 * 
 */
public class MenuItemsAdapter extends ArrayAdapter<MenuItem> {
	
	private final String LOGGER_TAG = "WITWATERSRAND";
	private final Context _context;
	MenuItem[] _myMenu;
	int _LAYOUT_RESOURCE_ID;
	NumberPicker quantityPicker;
	

	public MenuItemsAdapter(Context context, int textViewResourceId,
			MenuItem[] menuItems) {
		super(context, textViewResourceId, menuItems);
		Log.i(LOGGER_TAG, "MenuItemsAdapter -- Constructor");
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
		Log.i(LOGGER_TAG, "MenuItemsAdapter getView()");
		LayoutInflater myInflater = (LayoutInflater) _context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowRootView = myInflater.inflate(_LAYOUT_RESOURCE_ID, parent,
				false);
		Log.i(LOGGER_TAG, "MenuItemsAdapter -- Inflator called");
		TextView itemNameTV = (TextView) rowRootView
				.findViewById(R.id.tvItemName);
		int TEXT_WIDTH = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 195, getContext().getResources()
						.getDisplayMetrics());
		itemNameTV.setWidth(TEXT_WIDTH);
		itemNameTV.setText(_myMenu[position].getItemName());

		TextView stationNameTV = (TextView) rowRootView
				.findViewById(R.id.tvStationName);
		stationNameTV.setWidth(TEXT_WIDTH);
		stationNameTV.setText(_myMenu[position].getStationName());

		TextView priceTV = (TextView) rowRootView
				.findViewById(R.id.tvPrice);
				
		priceTV.setText("R " + String.format("%.2f", _myMenu[position].getPrice()));

		quantityPicker = (NumberPicker) rowRootView.findViewById(R.id.selectedPicker);

		Log.i(LOGGER_TAG, "MenuItemsAdapter -- getView() -- quantityPicker.getValue() = " + quantityPicker.getValue());
		
		final int _selectedPosition = position;
		Button mySelectedButton = (Button) rowRootView.findViewById(R.id.selectedButton);
		mySelectedButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Log.i(LOGGER_TAG, "Button pressed for item name: " + _myMenu[_selectedPosition].getItemName());
				int enteredQuantity = quantityPicker.getValue();
				Log.d(LOGGER_TAG, "enteredQuantity = " + enteredQuantity);
				CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(_context);
				myDatabase.open();
				// TODO The last parameter should be retrieved as a shared preference
				myDatabase.addPurchaseItemToOrder(_myMenu[_selectedPosition] , enteredQuantity, 1);
				myDatabase.close();
			}
		});
		Log.i(LOGGER_TAG, "MenuItemsAdapter getView() complete");
		return rowRootView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseAdapter#isEnabled(int)
	 */
	@Override
	public boolean isEnabled(int position) {
		Log.i(LOGGER_TAG, "MenuItemsAdapter isEnabled()");
		if (_myMenu[position].isAvailable()) {
			return true;
		}
		return false;
	}


			

}