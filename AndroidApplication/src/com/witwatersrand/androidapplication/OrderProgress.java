package com.witwatersrand.androidapplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class OrderProgress extends Activity {
	private static final String ORDER_KEY = "Order";
	TextView orderNameTV;
	int orderNumber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_progress);
        orderNameTV = (TextView) findViewById(R.id.tvOrderName);
        orderNumber = Integer.parseInt(getIntent().getExtras().getString(ORDER_KEY));
        orderNameTV.setText("Order " + orderNumber);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_order_progress, menu);
        return true;
    }
}
