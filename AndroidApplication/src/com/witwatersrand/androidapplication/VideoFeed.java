package com.witwatersrand.androidapplication;

import java.io.InputStream;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class VideoFeed extends Activity {
	final static private String LOGGER_TAG = "WITWATERSRAND";
	ImageView videoFeedIV;
	Bitmap canteenBitmap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_feed);
		videoFeedIV = (ImageView) findViewById(R.id.ivFeed);
		
		
		final Button refreshB = (Button) findViewById(R.id.bRefresh);
		refreshB.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x00000000));	
		refreshB.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					refreshB.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFF00, 0xFF0064FF));						
					break;
				case MotionEvent.ACTION_UP:

					refreshB.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x00000000));	
					break;
				}
				return false;
			}
		});
		refreshB.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				RetrieveFeed task = new RetrieveFeed();
				task.execute(new String[] { "http://" + ApplicationPreferences.getServerIPAddress(getBaseContext()) + "/yii/index.php/mobile/videofeed" });
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_video_feed, menu);
		return true;
	}

	private class RetrieveFeed extends AsyncTask<String, Void, Drawable> {
		@Override
		protected Drawable doInBackground(String... urls) {
			return sendHTTPRequest(urls);
		}

		private Drawable sendHTTPRequest(String[] urls) {
			for (String url : urls) {
				Log.i(LOGGER_TAG, "VideoFeed -- RetrieveFeed -- doInBackground()");
				try {
					InputStream is = (InputStream) new URL(url).getContent();
					Drawable d = Drawable.createFromStream(is, "live_feed");
					return d;

				} catch (Exception e) {
					e.printStackTrace();
					Log.i(LOGGER_TAG, "VideoFeed -- RetrieveFeed -- doInBackground() -- Exception = |" + e.getMessage() + "|");
				}
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Drawable result) {
			super.onPostExecute(result);
			Log.i(LOGGER_TAG, "VideoFeed -- RetrieveFeed -- onPostExecute()");
			videoFeedIV.setImageDrawable(result);
		}
	}
}