package com.witwatersrand.androidapplication;

/**
 * An exception that is thrown for an invalid item price
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class InvalidPriceException extends Exception {

	private static final long serialVersionUID = 1L;


	public InvalidPriceException() {
		super(); 
	}

	public InvalidPriceException(String message) {
		super(message); 
	}

	public InvalidPriceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPriceException(Throwable cause) {
		super(cause);
	}
}
