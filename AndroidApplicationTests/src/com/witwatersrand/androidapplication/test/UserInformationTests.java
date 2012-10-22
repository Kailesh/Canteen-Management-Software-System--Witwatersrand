package com.witwatersrand.androidapplication.test;

import com.witwatersrand.androidapplication.UserInformation;

import android.test.ActivityInstrumentationTestCase2;

public class UserInformationTests extends ActivityInstrumentationTestCase2<UserInformation> {

	public UserInformationTests(String name) {
		super("com.witwatersrand.androidapplication.USERINFORMATION", UserInformation.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testIfTheUserNameCanBeChanged() {
		fail("Not yet implemented"); // TODO
	}

	public void testIfDeliveryLocationStatusCanBeChanged() {
		fail("Not yet implemented"); // TODO
	}
	
	public void testDeliveryLocationFloor() {
		fail("Not yet implemented"); // TODO
	}
	
	public void testDeliveryLocationRegion() {
		fail("Not yet implemented"); // TODO
	}
	
	public void testRemeberMeStatusChangeSuccess() {
		fail("Not yet implemented"); // TODO
	}
}
