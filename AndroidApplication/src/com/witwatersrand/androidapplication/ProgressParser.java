package com.witwatersrand.androidapplication;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.util.Log;

public class ProgressParser {
	final String LOGGER_TAG = "WITWATERSRAND";
	private OrderedItems _orderList;
	private static final String JSON_ITEM_KEY = "item";
	private static final String STATE_KEY = "progressStatus";
	
	public ProgressParser(String jsonString, OrderItem[] order) {
		Log.i(LOGGER_TAG, "ProgressParser -- Constructor");
		_orderList = new OrderedItems(order);
		parseStates(jsonString);
	}

	private void parseStates(String message) {
		Log.i(LOGGER_TAG, "ProgressParser -- parseStates");
		JSONParser parser = new JSONParser();
		try {
			JSONArray jsonArray = (JSONArray) parser.parse(message);
			Iterator<?> progressIterator = jsonArray.iterator();
			
			while (progressIterator.hasNext()) {
				Log.i(LOGGER_TAG, "ProgressParser -- iterating through the JSONArray");
				JSONObject currentObject = (JSONObject) progressIterator.next();
				
				for(int i = 0; i != _orderList._myOrder.length ; i++) {
					if (_orderList._myOrder[i].getItemName().equals(currentObject.get(JSON_ITEM_KEY))) {
						Progress temp = Progress.valueOf((String) currentObject.get(STATE_KEY));
						Log.d(LOGGER_TAG, "temp = " +temp);
						_orderList._states[i] = temp;
						break;
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			Log.d(LOGGER_TAG, "Exception -- " + e.getMessage());
		}
	}

	/**
	 * @return the orderList
	 */
	public OrderedItems getOrderList() {
		return _orderList;
	}
}
