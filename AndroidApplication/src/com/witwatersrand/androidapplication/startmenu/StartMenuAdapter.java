/**
 * 
 */
package com.witwatersrand.androidapplication.startmenu;

import com.witwatersrand.androidapplication.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Kailesh
 *
 */
public class StartMenuAdapter extends ArrayAdapter<StartMenuItem>  {

	private final String LOGGER_TAG = "WITWATERSRAND";

	private Context _context;
	private StartMenuItem[] _myOptions;
	private int _LAYOUT_RESOURCE_ID;
	View rowRootView;

	public StartMenuAdapter(Context context, int textViewResourceId,
			StartMenuItem[] myOptions) {
		super(context, textViewResourceId, myOptions);
		Log.i(LOGGER_TAG, "StartMenuAdapter -- Constructor");
		this._context = context;
		this._myOptions = myOptions;
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
		Log.i(LOGGER_TAG, "StartMenuAdapter -- getView()");
		LayoutInflater myInflater = (LayoutInflater) _context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		rowRootView = myInflater.inflate(_LAYOUT_RESOURCE_ID, parent,
				false);
		Log.i(LOGGER_TAG, "StartMenuAdapter -- getView() --Inflator called");

		TextView optionNameTV = (TextView) rowRootView.findViewById(R.id.tvOptionName);
		optionNameTV.setText(_myOptions[position].getOptionName());
		
		ImageView iconIV = (ImageView) rowRootView.findViewById(R.id.ivIcon);
		int resourceId = _context.getResources().getIdentifier(_myOptions[position].getImageName(), "drawable", "com.witwatersrand.androidapplication");
		iconIV.setImageResource(resourceId);
		
		Log.i(LOGGER_TAG, "StartMenuAdapter -- onListItemClick()");
		
		return rowRootView;
	}
	
	
}
