/**
 * 
 */
package com.witwatersrand.androidapplication.progressrequester;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.util.Log;

import com.witwatersrand.androidapplication.Progress;

/**
 * @author Kailesh
 *
 */
public class SingleProgressStatusParser {
	final private static String LOGGER_TAG = "WITWATERSRAND";
	
	private static String _jsonMessage;
	
	final private static String JSON_ITEM_NAME_KEY = "item";
	final private static String JSON_ORDER_NUMBER = "orderNumber";
	final private static String JOSN_PROGRESS_STATUS_KEY = "status";
	
	private String _itemName;
	private int _orderNumber;
	private Progress _itemProgress;
	
	private String UNKNOWN = "Unknown";
	
	SingleProgressStatusParser (String message) {
		Log.i(LOGGER_TAG, "SingleProgressStatusParser -- Constructor");
		_jsonMessage = message;
		_itemName = UNKNOWN;
		_orderNumber = 0;
		_itemProgress = Progress.NONE;
		parseMessage();
	}


	private void parseMessage() {
		Log.i(LOGGER_TAG, "SingleProgressStatusParser -- parseMessage()");
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(_jsonMessage);
					
			_itemName = (String) jsonObject.get(JSON_ITEM_NAME_KEY);
			_orderNumber = (Integer) jsonObject.get(JSON_ORDER_NUMBER);
			_itemProgress = Progress.valueOf( ((String) jsonObject.get(JOSN_PROGRESS_STATUS_KEY)).toUpperCase());
			Log.i(LOGGER_TAG, "SingleProgressStatusParser -- parseMessage() -- Data parsed");
		} catch (ParseException e) {
			e.printStackTrace();
			Log.d(LOGGER_TAG, "SingleProgressStatusParser -- parseMessage() -- Exception = |" + e.getMessage() + "|");
		}	
	}


	/**
	 * @return the _itemName
	 */
	public String getItemName() {
		return _itemName;
	}


	/**
	 * @return the _orderNumber
	 */
	public int getOrderNumber() {
		return _orderNumber;
	}


	/**
	 * @return the _itemProgress
	 */
	public Progress getItemProgress() {
		return _itemProgress;
	}
	
}
