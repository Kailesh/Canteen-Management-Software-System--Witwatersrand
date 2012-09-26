package com.witwatersrand.androidapplication;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Canteen extends ListActivity {

	String mySelection[] = { "Station", "Items" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(Canteen.this,
				android.R.layout.simple_list_item_1, mySelection));
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView,
	 * android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String selectedClass = mySelection[position];

		try {
			Class myClass;

			myClass = Class.forName("com.witwatersrand.androidapplication."
					+ selectedClass.replaceAll("\\s", ""));

			Intent myIntent = new Intent(Canteen.this, myClass);
			startActivity(myIntent);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
