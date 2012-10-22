package com.witwatersrand.androidapplication.progressrequester;

import com.witwatersrand.androidapplication.OrderedItem;
import com.witwatersrand.androidapplication.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * An adapter to a ArrayAdapter for a ListView for the options. Encapsulates the display of an order item progresses
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class ItemsProgressAdapter extends ArrayAdapter<OrderedItem> {
	private final String LOGGER_TAG = "WITWATERSRAND";
	private Context _context;
	private OrderedItem[] _myOrderedItems;
	private int _LAYOUT_RESOURCE_ID;
	View rowRootView;
	
	public ItemsProgressAdapter(Context context, int textViewResourceId,
			OrderedItem[] myOrder) {
		super(context, textViewResourceId, myOrder);
		this._context = context;
		this._myOrderedItems = myOrder;
		this._LAYOUT_RESOURCE_ID = textViewResourceId;
	}

	/**
	 * Represents an activity for each order item progress
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Log.i(LOGGER_TAG, "ItemsProgressAdapter -- getView()");
		LayoutInflater myInflater = (LayoutInflater) _context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		rowRootView = myInflater.inflate(_LAYOUT_RESOURCE_ID, parent,
				false);

		Log.i(LOGGER_TAG, "ItemsProgressAdapter -- getView() --Inflator called");
		
		// Item name
		TextView itemNameTV = (TextView) rowRootView
				.findViewById(R.id.tvProgressItemName);
		Log.d(LOGGER_TAG, "_myOrderedItems[position].getItemName() = "+ _myOrderedItems[position].getItemName());
		itemNameTV.setText(_myOrderedItems[position].getItemName());
		
		// Item quantity
		TextView quantityTV = (TextView) rowRootView
				.findViewById(R.id.tvProgressQuatity);
		quantityTV.setText("" +_myOrderedItems[position].getPurchaseQuantity());

		// Item status
		TextView statusTV = (TextView) rowRootView
				.findViewById(R.id.tvProgressStatus);
		statusTV.setText("" + _myOrderedItems[position].getState());
		ProgressBar myBar = (ProgressBar) rowRootView.findViewById(R.id.progressBar1);

		Progress myState = _myOrderedItems[position].getState();

		if(myState == Progress.NONE) {
				myBar.setProgress(0);
		} else if (myState == Progress.PLACED) {
			myBar.setProgress(30);
		} else if (myState == Progress.PROCESSING) {
			myBar.setProgress(63);
		} else if (myState == Progress.DONE) {
			myBar.setProgress(100);
		} else if (myState == Progress.DELIVERING) {
			myBar.setProgress(100);
		}
		Log.d(LOGGER_TAG, "05");
		return rowRootView;
	}
	
	
}
