package com.witwatersrand.androidapplication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class MD5Encryptor {
	final private static String LOGGER_TAG = "WITWATERSRAND";

	final private static String SALT = "28b206548469ce62182048fd9cf91760";
	public static String createMD5(String text)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Log.i(LOGGER_TAG, "MD5Encryptor -- createMD5()");

		String saltedText = SALT + text;
		
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

//	private static String convertedToHex(byte[] data) {
//		Log.i(LOGGER_TAG, "MD5Encryptor -- convertedToHex()");
//		
//		StringBuffer myStringBuffer = new StringBuffer();
//		for (int i = 0; i < data.length; i++) {
//			int halfOfByte = (data[i] >>> 4) & 0x0F;
//			int twoHalfBytes = 0;
//
//			do {
//				if ((0 <= halfOfByte) && (halfOfByte <= 9)) {
//					myStringBuffer.append((char) ('0' + halfOfByte));
//				} else {
//					myStringBuffer.append((char) ('a' + (halfOfByte - 10)));
//				}
//				halfOfByte = data[i] & 0x0F;
//			} while (twoHalfBytes++ < 1);
//		}
//		Log.i(LOGGER_TAG, "MD5Encryptor -- convertedToHex() -- myStringBuffer.toString() = " + myStringBuffer.toString());
//		return myStringBuffer.toString();
//	}
}