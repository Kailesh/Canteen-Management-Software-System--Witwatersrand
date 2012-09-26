package com.witwatersrand.androidapplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class VideoFeed extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_feed);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_video_feed, menu);
        return true;
    }
}
