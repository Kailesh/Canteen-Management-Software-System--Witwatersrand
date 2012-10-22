package com.witwatersrand.androidapplication.authetication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

/**
 * An MD5 encryptor with a salt
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class MD5Encryptor {
	final private static String LOGGER_TAG = "WITWATERSRAND";
	final private static String SALT = "28b206548469ce62182048fd9cf91760";
	
	/**
	 * Creates the MD5 encryption for a given string
	 * @param message the message to be encrypted
	 * @return the encrypted message
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String createMD5(String message)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Log.i(LOGGER_TAG, "MD5Encryptor -- createMD5()");

		String saltedText = SALT + message;
		
		MessageDigest myMessageDigest = java.security.MessageDigest.getInstance("MD5");
		myMessageDigest.update(saltedText.getBytes());
		byte md5[] = myMessageDigest.digest();
		
		StringBuffer hexString = new StringBuffer();
		
		for (int i=0; i<md5.length; i++) {
			hexString.append(Integer.toHexString(0xFF & md5[i]));
		}

		Log.i(LOGGER_TAG, "MD5Encryptor -- createMD5() -- hexString.toString() = " + hexString.toString());
		return hexString.toString();
	}
}