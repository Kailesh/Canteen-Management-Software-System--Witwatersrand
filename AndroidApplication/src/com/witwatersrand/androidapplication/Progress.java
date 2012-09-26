package com.witwatersrand.androidapplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Progress extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_progress, menu);
        return true;
    }
}
