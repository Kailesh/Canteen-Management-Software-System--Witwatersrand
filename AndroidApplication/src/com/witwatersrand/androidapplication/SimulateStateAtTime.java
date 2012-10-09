package com.witwatersrand.androidapplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SimulateStateAtTime extends Activity implements OnClickListener{
	
	Button sevenAmB;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulate_state_at_time);
        sevenAmB = (Button) findViewById(R.id.bSevenAM);
        sevenAmB.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_simulate_state_at_time, menu);
        return true;
    }

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
					Toast.LENGTH_SHORT).show();

			break;
		}
	}
}