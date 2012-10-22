package com.witwatersrand.androidapplication.test.authentication;

import com.witwatersrand.androidapplication.authetication.Authentication;

import android.test.ActivityInstrumentationTestCase2;

public class AuthenticationTests extends ActivityInstrumentationTestCase2<Authentication> {

	public AuthenticationTests() {
		super("com.witwatersrand.androidapplication.LOGIN", Authentication.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		// TODO set up views
		// Authentication authentication = getActivity();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testCorrectCredentials() {
		fail("Not yet implemented"); // TODO
	}
	
	public void testIncorrectUserName() {
		fail("Not yet implemented"); // TODO
	}
	
	public void testIncorrectPassword() {
		fail("Not yet implemented"); // TODO
	}
	
	public void testUnregisteredDevice() {
		fail("Not yet implemented"); // TODO
	}
}
