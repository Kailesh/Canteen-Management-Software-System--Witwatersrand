package com.witwatersrand.androidapplication.progressrequester;

import com.witwatersrand.androidapplication.ApplicationPreferences;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A list activity that displays todays orders
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class CurrentOrders extends ListActivity {
	private static final String LOGGER_TAG = "WITWATERSRAND";
	private static String orders[];

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOGGER_TAG, "CurrentOrders -- onCreate()");
        
        final int listSize = ApplicationPreferences.getOrderNumber(this) - 1;
        
        orders = new String[listSize];
        
        for(int i = 0; i != orders.length; i++) {
        	
        	orders[i] = "Order " + (i+1);
        }
        
        setListAdapter(new ArrayAdapter<String>(CurrentOrders.this,
				android.R.layout.simple_list_item_1, orders));
    }

    /**
     * Calls another activty that displays the progress of the chosen order
     */
    protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.i(LOGGER_TAG, "CurrentOrders -- onListItemClick()");
		String selectedOrder = orders[position];
		int orderNUmber = Integer.parseInt(selectedOrder.replace("Order ", ""));
		Log.d(LOGGER_TAG, "CurrentOrders -- onListItemClick() -- Order number = " + orderNUmber);
		Intent myIntent = new Intent("com.witwatersrand.androidapplication.ORDERPROGRESS");
		myIntent.putExtra("Order", selectedOrder.replace("Order ", ""));
		startActivity(myIntent);
	}
}