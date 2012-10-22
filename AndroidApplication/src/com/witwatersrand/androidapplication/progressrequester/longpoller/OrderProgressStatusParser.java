/**
 * 
 */
package com.witwatersrand.androidapplication.progressrequester.longpoller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.util.Log;

import com.witwatersrand.androidapplication.progressrequester.Progress;

/**
 * Parser that parses the long poller order completion response
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class OrderProgressStatusParser {
	final private static String LOGGER_TAG = "WITWATERSRAND";
	
	private static String _jsonMessage;
	
	final private static String JSON_ORDER_NUMBER = "orderNumber";
	final private static String JOSN_PROGRESS_STATUS_KEY = "status";
	
	private int _orderNumber;
	private Progress _orderProgress;

	
	OrderProgressStatusParser (String message) {
		Log.i(LOGGER_TAG, "OrderProgressStatusParser -- Constructor");
		_jsonMessage = message;
		_orderNumber = 0;
		_orderProgress = Progress.NONE;
		parseMessage();
	}

	/**
	 * Parses the order completion message
	 */
	private void parseMessage() {
		Log.i(LOGGER_TAG, "OrderProgressStatusParser -- parseMessage()");
		JSONParser parser = new JSONParser();
		try {
			
			JSONObject jsonObject = (JSONObject) parser.parse(_jsonMessage);
			Log.d(LOGGER_TAG, "01");
			long tempOrderNumber = (Long) jsonObject.get(JSON_ORDER_NUMBER);
			_orderNumber = (int) tempOrderNumber;
			Log.d(LOGGER_TAG, "02 -- _orderNumber = " + _orderNumber);
			_orderProgress = Progress.valueOf( ((String) jsonObject.get(JOSN_PROGRESS_STATUS_KEY)).toUpperCase());
			Log.d(LOGGER_TAG, "03");
			
			Log.i(LOGGER_TAG, "OrderProgressStatusParser -- parseMessage() -- Data parsed");
		} catch (ParseException e) {
			e.printStackTrace();
			Log.d(LOGGER_TAG, "OrderProgressStatusParser -- parseMessage() -- Exception = |" + e.getMessage() + "|");
		}	
	}

	/**
	 * @return the order number
	 */
	public int getOrderNumber() {
		return _orderNumber;
	}


	/**
	 * @return the item progress
	 */
	public Progress getItemProgress() {
		return _orderProgress;
	}
	
}
