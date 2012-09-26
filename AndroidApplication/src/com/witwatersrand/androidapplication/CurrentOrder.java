package com.witwatersrand.androidapplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CurrentOrder extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_current_order, menu);
        return true;
    }
}
