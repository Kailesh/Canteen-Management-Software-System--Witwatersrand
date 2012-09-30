package com.witwatersrand.androidapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Cart extends Activity {
	final String loggerTag = "WITWATERSRAND";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_cart, menu);
        return true;
    }
    
    private class UploadOrder extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			return null;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			
		}
		
		
    	
    }
    
    
}
