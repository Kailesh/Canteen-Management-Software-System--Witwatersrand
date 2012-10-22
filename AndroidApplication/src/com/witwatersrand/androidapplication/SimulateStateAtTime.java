package com.witwatersrand.androidapplication;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.LightingColorFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Simulates the task of the scheduler with a button press
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class SimulateStateAtTime extends Activity implements OnClickListener{
	
	Button _sevenAmB;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulate_state_at_time);
        _sevenAmB = (Button) findViewById(R.id.bSevenAM);
        _sevenAmB.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF006400	));
        _sevenAmB.setOnClickListener(this);
    }

    /**
     * Implements the functionality for a button press for resetting the application
     */
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.bSevenAM:
			CanteenManagerDatabase myDatabase = new CanteenManagerDatabase(this);
			myDatabase.open();
			myDatabase.removeAllMenuItems();
			myDatabase.removeAllOrderItems();
			
			ApplicationPreferences.setOrderNumber(this, 1);
			ApplicationPreferences.setLatestOrderTotal(this, 0);
			ApplicationPreferences.setMenuUpdated(this, false);
			ApplicationPreferences.setHaveMenu(this, false);
			Toast.makeText(
					this,
					"Menu table, order table emptied and order number reset to 1, set the boolean for updated menu at the server and currently have items in the database",
					Toast.LENGTH_LONG).show();

			break;
		}
	}
}