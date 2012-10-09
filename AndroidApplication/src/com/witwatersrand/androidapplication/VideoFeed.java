package com.witwatersrand.androidapplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
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
		
		
		Button refreshB = (Button) findViewById(R.id.bRefresh);
		refreshB.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Retrieve task = new Retrieve();
				task.execute(new String[] { "http://146.141.125.108/yii/index.php/mobile/VideoFeed" });
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_video_feed, menu);
		return true;
	}

	private class Retrieve extends AsyncTask<String, Void, Drawable> {
		@Override
		protected Drawable doInBackground(String... urls) {
			Log.d(LOGGER_TAG, "0122");
			return sendHTTPRequest(urls);
		}

		private Drawable sendHTTPRequest(String[] urls) {
			for (String url : urls) {
				Log.d(LOGGER_TAG, "Inside for inside doInBackground()");
				try {
					InputStream is = (InputStream) new URL(url).getContent();
					Drawable d = Drawable.createFromStream(is, "live_feed");
					return d;

				} catch (IOException e) {
					e.printStackTrace();
					Log.d(LOGGER_TAG, e.getMessage());
				} catch (Exception b) {
					b.printStackTrace();
					Log.d(LOGGER_TAG, b.getMessage());
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
			Log.d(LOGGER_TAG, "01");
			Log.d(LOGGER_TAG, "result = " + result.toString());
			Log.d(LOGGER_TAG, "result min heigtht = " + result.getMinimumHeight());
			videoFeedIV.setImageDrawable(result);
			Log.d(LOGGER_TAG, "02");
		}
	}
}