package com.witwatersrand.androidapplication;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CartAdapter extends ArrayAdapter<OrderItem> {
	private final String LOGGER_TAG = "WITWATERSRAND";
	private final Context _context;
	OrderItem[] _myCart;
	int _LAYOUT_RESOURCE_ID;
	View rowRootView;

	public CartAdapter(Context context, int textViewResourceId,
			OrderItem[] orderList) {
		super(context, textViewResourceId, orderList);
		Log.i(LOGGER_TAG, "CartAdapter -- Constructor");
		this._context = context;
		this._myCart = orderList;
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
		Log.i(LOGGER_TAG, "CartAdapter --  getView()");
		LayoutInflater myInflater = (LayoutInflater) _context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rowRootView = myInflater.inflate(_LAYOUT_RESOURCE_ID, parent, false);
		Log.i(LOGGER_TAG, "CartAdapter -- Inflator called");

		// Item name
		Log.d(LOGGER_TAG, "01");
		TextView itemNameTV = (TextView) rowRootView
				.findViewById(R.id.tvCartItemName);
		Log.d(LOGGER_TAG, "02");
		int TEXT_WIDTH = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 195, getContext().getResources()
						.getDisplayMetrics());
		Log.d(LOGGER_TAG, "03");
		itemNameTV.setWidth(TEXT_WIDTH);
		Log.d(LOGGER_TAG, "04");
		itemNameTV.setText(_myCart[position].getItemName());
		Log.d(LOGGER_TAG, "05");
		// Station name
		TextView stationNameTV = (TextView) rowRootView
				.findViewById(R.id.tvCartStationName);
		Log.d(LOGGER_TAG, "06");
		stationNameTV.setWidth(TEXT_WIDTH);
		Log.d(LOGGER_TAG, "07");
		stationNameTV.setText(_myCart[position].getStationName());
		Log.d(LOGGER_TAG, "08");
		// Quantity of item
		TextView quantityTV = (TextView) rowRootView
				.findViewById(R.id.tvCartQuantity);
		Log.d(LOGGER_TAG, "09");
		quantityTV.setWidth(TEXT_WIDTH);
		Log.d(LOGGER_TAG, "10");
		quantityTV.setText("" + _myCart[position].getPurchaseQuantity());
		Log.d(LOGGER_TAG, "11");
		// Price
		TextView priceTV = (TextView) rowRootView.findViewById(R.id.tvCartPrice);
		Log.d(LOGGER_TAG, "12");
		priceTV.setText("R "
				+ String.format("%.2f", _myCart[position].getPrice()));
		Log.d(LOGGER_TAG, "13");
		return rowRootView;
	}

}
