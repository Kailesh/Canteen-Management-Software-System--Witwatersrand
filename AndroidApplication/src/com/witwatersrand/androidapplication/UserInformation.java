package com.witwatersrand.androidapplication;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * The user preferences activity, currently displaying the user name, delivery location options, remember me status
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class UserInformation extends PreferenceActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_preferences);
    }
}