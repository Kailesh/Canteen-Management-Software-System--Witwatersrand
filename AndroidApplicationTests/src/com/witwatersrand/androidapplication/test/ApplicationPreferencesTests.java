package com.witwatersrand.androidapplication.test;

import android.test.AndroidTestCase;

import com.witwatersrand.androidapplication.ApplicationPreferences;


public class ApplicationPreferencesTests extends AndroidTestCase {

	public ApplicationPreferencesTests() {
		super();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetOrderNumber() {
		int orderNumberState = ApplicationPreferences.getOrderNumber(getContext());
		int temporaryOrderNumber = 4;
		ApplicationPreferences.setOrderNumber(getContext(), temporaryOrderNumber);
		assertEquals(temporaryOrderNumber, ApplicationPreferences.getOrderNumber(getContext()));
		ApplicationPreferences.setOrderNumber(getContext(), orderNumberState);
	}

	public void testSetOrderNumber() {
		int orderNumberState = ApplicationPreferences.getOrderNumber(getContext());
		int temporaryOrderNumber = 5;
		ApplicationPreferences.setOrderNumber(getContext(), temporaryOrderNumber);
		assertEquals(temporaryOrderNumber, ApplicationPreferences.getOrderNumber(getContext()));
		ApplicationPreferences.setOrderNumber(getContext(), orderNumberState);
	}

	public void testGetLatestOrderTotal() {
		float orderTotalState = ApplicationPreferences.getLatestOrderTotal(getContext());
		float temporaryOrderTotal = (float) 100.66;
		ApplicationPreferences.setLatestOrderTotal(getContext(), temporaryOrderTotal);
		assertEquals(temporaryOrderTotal, ApplicationPreferences.getLatestOrderTotal((getContext())));
		ApplicationPreferences.setLatestOrderTotal(getContext(), orderTotalState);
	}

	public void testSetLatestOrderTotal() {
		float orderTotalState = ApplicationPreferences.getLatestOrderTotal(getContext());
		float temporaryOrderTotal = (float) 256.87;
		ApplicationPreferences.setLatestOrderTotal(getContext(), temporaryOrderTotal);
		assertEquals(temporaryOrderTotal, ApplicationPreferences.getLatestOrderTotal((getContext())));
		ApplicationPreferences.setLatestOrderTotal(getContext(), orderTotalState);
	}

	public void testGetAccountBalance() {
		float accountBalanceState = ApplicationPreferences.getAccountBalance(getContext());
		float temporaryAccountBalance = (float) 1000.45;
		ApplicationPreferences.setAccountBalance(getContext(), temporaryAccountBalance);
		assertEquals(temporaryAccountBalance, ApplicationPreferences.getAccountBalance((getContext())));
		ApplicationPreferences.setLatestOrderTotal(getContext(), accountBalanceState);
	}

	public void testSetAccountBalance() {
		float accountBalanceState = ApplicationPreferences.getAccountBalance(getContext());
		float temporaryAccountBalance = (float) 1000.45;
		ApplicationPreferences.setAccountBalance(getContext(), temporaryAccountBalance);
		assertEquals(temporaryAccountBalance, ApplicationPreferences.getAccountBalance((getContext())));
		ApplicationPreferences.setLatestOrderTotal(getContext(), accountBalanceState);
	}

	public void testIsUserRemembered() {
		boolean rememberMeState = ApplicationPreferences.isUserRemembered(getContext());
		boolean temporaryRememberMeStatus = true;
		ApplicationPreferences.setRememberMeStatus(getContext(), temporaryRememberMeStatus);
		assertEquals(temporaryRememberMeStatus, ApplicationPreferences.isUserRemembered((getContext())));
		ApplicationPreferences.setRememberMeStatus(getContext(), rememberMeState);
	}

	public void testSetRememberMeStatus() {
		boolean rememberMeState = ApplicationPreferences.isUserRemembered(getContext());
		boolean temporaryRememberMeStatus = true;
		ApplicationPreferences.setRememberMeStatus(getContext(), temporaryRememberMeStatus);
		assertEquals(temporaryRememberMeStatus, ApplicationPreferences.isUserRemembered((getContext())));
		ApplicationPreferences.setRememberMeStatus(getContext(), rememberMeState);
	}

	public void testGetUserName() {
		String userNameState = ApplicationPreferences.getUserName(getContext());
		String temporaryUserName = "John";
		ApplicationPreferences.setUserName(getContext(), temporaryUserName);
		assertEquals(temporaryUserName, ApplicationPreferences.getUserName((getContext())));
		ApplicationPreferences.setUserName(getContext(), userNameState);
	}

	public void testSetUserName() {
		String userNameState = ApplicationPreferences.getUserName(getContext());
		String temporaryUserName = "John";
		ApplicationPreferences.setUserName(getContext(), temporaryUserName);
		assertEquals(temporaryUserName, ApplicationPreferences.getUserName((getContext())));
		ApplicationPreferences.setUserName(getContext(), userNameState);
	}

	public void testGetPassword() {
		String userNameState = ApplicationPreferences.getUserName(getContext());
		String temporaryUserName = "John";
		ApplicationPreferences.setUserName(getContext(), temporaryUserName);
		assertEquals(temporaryUserName, ApplicationPreferences.getUserName((getContext())));
		ApplicationPreferences.setUserName(getContext(), userNameState);
	}

	public void testSetPassword() {
		String passwordState = ApplicationPreferences.getPassword(getContext());
		String temporaryPassword = "password";
		ApplicationPreferences.setPassword(getContext(), temporaryPassword);
		assertEquals(temporaryPassword, ApplicationPreferences.getUserName((getContext())));
		ApplicationPreferences.setUserName(getContext(), passwordState);
		
	}

	public void testIsMenuUpated() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetMenuUpdated() {
		fail("Not yet implemented"); // TODO
	}

	public void testHaveMenu() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetHaveMenu() {
		fail("Not yet implemented"); // TODO
	}

	public void testIsStatusPending() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetPendingStatus() {
		fail("Not yet implemented"); // TODO
	}

	public void testGetServerIPAddress() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetServerIPAddress() {
		fail("Not yet implemented"); // TODO
	}

}
