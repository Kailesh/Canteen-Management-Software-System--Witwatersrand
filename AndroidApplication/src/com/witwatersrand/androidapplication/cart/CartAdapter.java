package com.witwatersrand.androidapplication.cart;

import com.witwatersrand.androidapplication.ApplicationPreferences;
import com.witwatersrand.androidapplication.CanteenManagerDatabase;
import com.witwatersrand.androidapplication.OrderItem;
import com.witwatersrand.androidapplication.R;
import com.witwatersrand.androidapplication.R.id;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class CartAdapter extends ArrayAdapter<OrderItem> {
	private final String LOGGER_TAG = "WITWATERSRAND";
	private final Context _context;
	OrderItem[] _myCart;
	int _LAYOUT_RESOURCE_ID;
	View rowRootView;
	TextView quantityTV;
	
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

				float newCost = ApplicationPreferences.getLatestOrderTotal(_context) + _myCart[_selectedPosition].getPrice();
				float balance = ApplicationPreferences.getAccountBalance(_context);
				if (newCost > balance) { 
					Toast.makeText(_context, "You do not have suffiecient funds to add more items to the cart", Toast.LENGTH_SHORT).show();
				} else {
					itemQuantity = Integer.parseInt(myUniqueQuantityTV.getText().toString());
					itemQuantity++;

					_myCart[_selectedPosition].setPurchaseQuantity(itemQuantity);

					myUniqueQuantityTV.setText("" + itemQuantity);

					int orderNumber =  ApplicationPreferences.getOrderNumber(_context);
					Log.d(LOGGER_TAG, "orderNumber = " + orderNumber );
					ApplicationPreferences.setOrderNumber(_context, orderNumber);

					CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(_context);
					myDatabase.open();
					myDatabase.updatePurchaseQuantity(_myCart[_selectedPosition].getItemName(), itemQuantity, ApplicationPreferences.getOrderNumber(_context));

					float total = myDatabase.getTotalForOrder(ApplicationPreferences.getOrderNumber(_context));				
					// TODO Not good practice but works
					Cart.totalTV.setText("R " + String.format("%.2f", total));

					ApplicationPreferences.setLatestOrderTotal(_context, total);
					myDatabase.close();
				}
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
				if (itemQuantity == 0) {
					Log.i(LOGGER_TAG, "CartAdapter -- getView() -- onClick() -- itemQuantity = |" + itemQuantity + "|");
						
				} else {
					itemQuantity--;
					_myCart[_selectedPosition]
							.setPurchaseQuantity(itemQuantity);

					myUniqueQuantityTV.setText("" + itemQuantity);
					
					int orderNumber =  ApplicationPreferences.getOrderNumber(_context);
					Log.d(LOGGER_TAG, "orderNumber = " + orderNumber );
					ApplicationPreferences.setOrderNumber(_context, orderNumber);

					CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(
							_context);
					myDatabase.open();
					myDatabase.updatePurchaseQuantity(_myCart[_selectedPosition].getItemName(), itemQuantity, ApplicationPreferences.getOrderNumber(_context));

					float total = myDatabase.getTotalForOrder(ApplicationPreferences.getOrderNumber(_context));

					// TODO Not good practice but works
					Cart.totalTV.setText("R " + String.format("%.2f", total));

					ApplicationPreferences.setLatestOrderTotal(_context, total);
					myDatabase.close();
				}
			}
		});
		return rowRootView;
	}
}