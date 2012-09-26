package com.witwatersrand.androidapplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UserInformation extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_user_information, menu);
        return true;
    }
}
