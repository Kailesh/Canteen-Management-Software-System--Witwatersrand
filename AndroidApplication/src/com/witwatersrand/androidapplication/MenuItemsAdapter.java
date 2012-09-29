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
public class MenuItemsAdapter extends ArrayAdapter<MenuItem>{
	
	private final String _tag = "WITWATERSRAND";
	private final Context _context;
	MenuItem[] _myMenu;
	int _LAYOUT_RESOURCE_ID;
	

	public MenuItemsAdapter(Context context, int textViewResourceId,
			MenuItem[] menuItems) {
		super(context, textViewResourceId, menuItems);
		this._context = context;
		this._myMenu = menuItems;
		this._LAYOUT_RESOURCE_ID = textViewResourceId;
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater myInflater = (LayoutInflater) _context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowRootView = myInflater.inflate(_LAYOUT_RESOURCE_ID, parent, false);
		
		TextView itemNameTV = (TextView) rowRootView.findViewById(R.id.tvItemName);
		int TEXT_WIDTH = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 195, getContext().getResources().getDisplayMetrics());
		itemNameTV.setWidth(TEXT_WIDTH);
		itemNameTV.setText(_myMenu[position].getItemname());		
		
		TextView stationNameTV = (TextView) rowRootView.findViewById(R.id.tvStationName);
		stationNameTV.setWidth(TEXT_WIDTH);
		stationNameTV.setText(_myMenu[position].getStationName());

		TextView priceTV = (TextView) rowRootView.findViewById(R.id.tvStationName);
		priceTV.setText(Float.toString(_myMenu[position].getPrice()));
		
		
		// TODO Quantity Picker - find out out how the picker works
		NumberPicker myPicker = (NumberPicker) rowRootView.findViewById(R.id.selectedPicker);
		
		
		Button mySelectedButton = (Button) rowRootView.findViewById(R.id.selectedButton);                
		mySelectedButton.setOnClickListener(_myMenu[position].addToCartListener);
		
		return rowRootView;
	}

	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#isEnabled(int)
	 */
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		if(_myMenu[position].isAvailable()) {
			return true;
		}
		return false;
	}
}
