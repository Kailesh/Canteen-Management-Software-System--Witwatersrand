package com.witwatersrand.androidapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;

public class CartAdapter extends ArrayAdapter<OrderItem> {
	private final String LOGGER_TAG = "WITWATERSRAND";
	private final Context _context;
	OrderItem[] _myCart;
	int _LAYOUT_RESOURCE_ID;
	View rowRootView;
	TextView quantityTV;
	
	private static final String APPLIATION_DATA_FILENAME = "preferencesFilename";
	private static final String ORDER_NUMBER_KEY = "order";
	private static final String TOTAL_COST_KEY = "total_cost";
	
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

		// Item name;
		TextView itemNameTV = (TextView) rowRootView
				.findViewById(R.id.tvCartItemName);
		int TEXT_WIDTH = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 195, getContext().getResources()
						.getDisplayMetrics());
		itemNameTV.setWidth(TEXT_WIDTH);
		itemNameTV.setText(_myCart[position].getItemName());

		// Station name
		TextView stationNameTV = (TextView) rowRootView
				.findViewById(R.id.tvCartStationName);
		stationNameTV.setWidth(TEXT_WIDTH);
		stationNameTV.setText(_myCart[position].getStationName());

		// Quantity of item
		quantityTV = (TextView) rowRootView
				.findViewById(R.id.tvCartQuantity);
		quantityTV.setWidth(TEXT_WIDTH);
		quantityTV.setText("" + _myCart[position].getPurchaseQuantity());
		
		// Price
		TextView priceTV = (TextView) rowRootView.findViewById(R.id.tvCartPrice);
		priceTV.setText("R "
				+ String.format("%.2f", _myCart[position].getPrice()));
	
		// Add button
		final int _selectedPosition = position;
		Button addButton = (Button) rowRootView.findViewById(R.id.bIncerement);
		addButton.setOnClickListener(new View.OnClickListener() {
			TextView myUniqueQuantityTV = (TextView) rowRootView.findViewById(R.id.tvCartQuantity);
			int  itemQuantity = 0;
			public void onClick(View v) {
				Log.i(LOGGER_TAG, "CartAdapter -- getView() -- onClick() -- Button pressed for item name: " + _myCart[_selectedPosition].getItemName());

				itemQuantity = Integer.parseInt(myUniqueQuantityTV.getText().toString());
				itemQuantity++;
				
				_myCart[_selectedPosition].setPurchaseQuantity(itemQuantity);
				
				myUniqueQuantityTV.setText("" + itemQuantity);
				
				SharedPreferences applicationData = _context.getSharedPreferences(APPLIATION_DATA_FILENAME, 0);
				
				CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(_context);
				myDatabase.open();
				myDatabase.updatePurchaseQuantity(_myCart[_selectedPosition].getItemName(), itemQuantity, applicationData.getInt(ORDER_NUMBER_KEY, 1));
				
				float total = myDatabase.getTotalForOrder(applicationData.getInt(ORDER_NUMBER_KEY, 1));				
				// TODO Not good practice but works
				Cart.totalTV.setText("R " + String.format("%.2f", total));

				Editor myEditor = applicationData.edit();
				myEditor.putFloat(TOTAL_COST_KEY, total);
				myEditor.commit();
				myDatabase.close();
			}
		});
		
		// Remove button
		Button removeButton = (Button) rowRootView.findViewById(R.id.bDecerement);
		removeButton.setOnClickListener(new View.OnClickListener() {
			TextView myUniqueQuantityTV = (TextView) rowRootView.findViewById(R.id.tvCartQuantity);
			int  itemQuantity = 0;
			public void onClick(View v) {
				Log.i(LOGGER_TAG, "CartAdapter -- getView() -- onClick() -- Button pressed for item name: " + _myCart[_selectedPosition].getItemName());
				itemQuantity = Integer.parseInt(myUniqueQuantityTV.getText().toString());				
				itemQuantity--;				
				_myCart[_selectedPosition].setPurchaseQuantity(itemQuantity);
				
				myUniqueQuantityTV.setText("" + itemQuantity);
				
				SharedPreferences applicationData = _context.getSharedPreferences(APPLIATION_DATA_FILENAME, 0);
				
				CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(_context);
				myDatabase.open();
				myDatabase.updatePurchaseQuantity(_myCart[_selectedPosition].getItemName(), itemQuantity, applicationData.getInt(ORDER_NUMBER_KEY, 1));
				
				float total = myDatabase.getTotalForOrder(applicationData.getInt(ORDER_NUMBER_KEY, 1));
				
				// TODO Not good practice but works
				Cart.totalTV.setText("R " + String.format("%.2f", total));

				Editor myEditor = applicationData.edit();
				myEditor.putFloat(TOTAL_COST_KEY, total);
				myEditor.commit();
				myDatabase.close();
			}
		});
		return rowRootView;
	}
}