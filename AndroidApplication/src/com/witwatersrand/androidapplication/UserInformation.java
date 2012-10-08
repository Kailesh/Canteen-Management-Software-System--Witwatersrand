package com.witwatersrand.androidapplication;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class UserInformation extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_preferences);
    }
    
}
