package com.witwatersrand.androidapplication.progressrequester;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.witwatersrand.androidapplication.InvalidPriceException;
import com.witwatersrand.androidapplication.OrderItem;
import com.witwatersrand.androidapplication.OrderedItem;
import com.witwatersrand.androidapplication.OrderedItemsAdapter;

import android.util.Log;

/**
 * Parser that parses the progress HTTP response message
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class ProgressParser {
	final String LOGGER_TAG = "WITWATERSRAND";
	private OrderedItemsAdapter _orderList;
	private static final String JSON_ITEM_KEY = "item";
	private static final String JSON_STATE_KEY = "status";
	
	
	public ProgressParser(String jsonString, OrderItem[] order) {
		Log.i(LOGGER_TAG, "ProgressParser -- Constructor");
		_orderList = new OrderedItemsAdapter(order);
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
				
				for(int i = 0; i != _orderList.getOrder().length ; i++) {
					if (_orderList.getOrder()[i].getItemName().equals(currentObject.get(JSON_ITEM_KEY))) {
						Progress temp = Progress.valueOf( ((String) currentObject.get(JSON_STATE_KEY)).toUpperCase());
						Log.d(LOGGER_TAG, "temp = " +temp);
						_orderList.getProgressStates()[i] = temp;
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
	 * @return the order list
	 */
	public OrderedItemsAdapter getOrderList() {
		return _orderList;
	}
	
	public OrderedItem[] getOrderedItemList() {
		OrderedItem[] myList = new OrderedItem[_orderList.getOrder().length];
		
		for(int i = 0; i < _orderList.getOrder().length; i++) {
			Log.i(LOGGER_TAG, "ProgressParser -- getOrderedItemList() -- i = " + i);

			myList[i] = new OrderedItem();
			myList[i].setItemName(_orderList.getOrder()[i].getItemName());
			myList[i].setStationName(_orderList.getOrder()[i].getStationName());
			try {
				myList[i].setPrice(_orderList.getOrder()[i].getPrice());
			} catch (InvalidPriceException e) {
				e.printStackTrace();
			}
			myList[i].setPurchaseQuantity(_orderList.getOrder()[i].getPurchaseQuantity());
			myList[i].setState(_orderList.getProgressStates()[i]);
		}
		return myList;
	}
}
