package com.witwatersrand.androidapplication;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class CartAdapter extends ArrayAdapter<OrderItem>{
	
	
	

	public CartAdapter(Context context, int textViewResourceId, List<OrderItem> objects) {
		super(context, textViewResourceId, objects);

	}

}
