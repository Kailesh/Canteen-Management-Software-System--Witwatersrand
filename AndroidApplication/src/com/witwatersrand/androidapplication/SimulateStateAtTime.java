package com.witwatersrand.androidapplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
			break;
		}
		
	}
}
