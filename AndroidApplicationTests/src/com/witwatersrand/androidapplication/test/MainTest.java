/**
 * 
 */
package com.witwatersrand.androidapplication.test;

import junit.framework.TestCase;

/**
 * @author Kailesh
 *
 */
public class MainTest extends TestCase {

	/**
	 * @param name
	 */
	public MainTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testMyFirstTest() {
		assertEquals("4","4");
	}

}
