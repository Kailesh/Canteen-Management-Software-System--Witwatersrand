package com.witwatersrand.androidapplication.test.startmenu;

import com.witwatersrand.androidapplication.startmenu.StartMenu;

import android.test.ActivityInstrumentationTestCase2;

public class StartMenuTests extends ActivityInstrumentationTestCase2<StartMenu> {

	public StartMenuTests() {
		super("com.witwatersrand.androidapplication.STARTMENU", StartMenu.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		// TODO set up views
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testApplicationExitsWhenExitIsSelected() {
		fail("Not yet implemented"); // TODO
	}

}
