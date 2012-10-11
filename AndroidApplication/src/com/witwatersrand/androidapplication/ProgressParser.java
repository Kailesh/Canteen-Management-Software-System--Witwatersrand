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
	private static final String JSON_STATE_KEY = "status";
	
	
	public ProgressParser(String jsonString, OrderItem[] order) {
		Log.i(LOGGER_TAG, "ProgressParser -- Constructor");
		_orderList = new OrderedItems(order);
		parseStates(jsonString);
	}

	private void parseStates(String message) {
		Log.i(LOGGER_TAG, "ProgressParser -- parseStates()");
		JSONParser parser = new JSONParser();
		try {
			JSONArray jsonArray = (JSONArray) parser.parse(message);
			Iterator<?> progressIterator = jsonArray.iterator();
			
			while (progressIterator.hasNext()) {
				Log.i(LOGGER_TAG, "ProgressParser -- parseStates() -- iterating through the JSONArray");
				JSONObject currentObject = (JSONObject) progressIterator.next();
				
				for(int i = 0; i != _orderList._myOrder.length ; i++) {
					if (_orderList._myOrder[i].getItemName().equals(currentObject.get(JSON_ITEM_KEY))) {
						Progress temp = Progress.valueOf( ((String) currentObject.get(JSON_STATE_KEY)).toUpperCase());
						Log.d(LOGGER_TAG, "temp = " +temp);
						_orderList._states[i] = temp;
						break;
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			Log.d(LOGGER_TAG, "ProgressParser -- parseStates() -- Exception -- " + e.getMessage());
		}
	}

	/**
	 * @return the orderList
	 */
	public OrderedItems getOrderList() {
		return _orderList;
	}
	
	public OrderedItem[] getOrderedItemList() {
		OrderedItem[] myList = new OrderedItem[_orderList._myOrder.length];
		
		for(int i = 0; i < _orderList._myOrder.length; i++) {
			Log.i(LOGGER_TAG, "ProgressParser -- getOrderedItemList() -- i = " + i);

			myList[i] = new OrderedItem();
			myList[i].setItemName(_orderList._myOrder[i].getItemName());
			myList[i].setStationName(_orderList._myOrder[i].getStationName());
			myList[i].setPrice(_orderList._myOrder[i].getPrice());
			myList[i].setPurchaseQuantity(_orderList._myOrder[i].getPurchaseQuantity());
			myList[i].setState(_orderList._states[i]);
		}
		return myList;
	}
}
