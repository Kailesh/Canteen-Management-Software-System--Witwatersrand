/**
 * 
 */
package com.witwatersrand.androidapplication;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Kailesh
 *
 */
public class MenuParser {
	
	final String LOGGER_TAG = "WITWATERSRAND";
	boolean _updated;
	MenuItem[] _menu;
	JSONObject _jsonObject;
	String JSON_UPDATE_KEY = "updated";
	String JSON_MENU_OBJECT_KEY = "menu";
	String JSON_ITEM_KEY = "item";
	String JSON_PRICE_KEY = "price";
	String JSON_STATION_KEY = "station";
	String JSON_AVAILABILITY_KEY = "availability";
	int _numberOfMenuItems;

	public MenuParser(String jsonMenuMessage) {
		this._updated = false;
		Log.i(LOGGER_TAG, "MenuParser -- Constructor");
		Log.i(LOGGER_TAG, "MenuParser -- JSON message = " + jsonMenuMessage);
		
		parseMenuData(jsonMenuMessage);
		
		Log.i(LOGGER_TAG, "MenuParser contruction complete");
	}
	
	private void parseMenuData(String jsonMenuMessage) {
		Log.i(LOGGER_TAG, "MenuParser -- parseMenuData() -- Parsing menu data");
		JSONParser parser = new JSONParser();
		try {
			_jsonObject = (JSONObject) parser.parse(jsonMenuMessage);
			_menu = new MenuItem[numberOfMenuItems()];
			
			Log.i(LOGGER_TAG, "MenuParser -- parseMenuData() -- _menu.lenght = " + _menu.length);
				
			setUpdate(Boolean.parseBoolean((String) _jsonObject.get(JSON_UPDATE_KEY)));
			setMenuItems();
			Log.i(LOGGER_TAG, "MenuParser -- parseMenuData() -- Data parsed");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(LOGGER_TAG, "Exception -- " + e.getMessage());
		}
	}
	
	/**
	 * @return the _updated
	 */
	public boolean isUpdated() {
		return _updated;
	}

	/**
	 * @param _updated the _updated to set
	 */
	private void setUpdate(boolean _updated) {
		Log.i(LOGGER_TAG, "MenuParser -- setUpdate() - Sets whether the menu is the most updated");
		this._updated = _updated;
	}

	/**
	 * @return the _menu
	 */
	public MenuItem[] getMenu() {
		Log.i(LOGGER_TAG, "MenuParser -- getMenu()");
		return _menu;
	}

	/**
	 *
	 */
	private void setMenuItems() {
		Log.i(LOGGER_TAG, "MenuParser -- setMenuItems()");
		JSONArray jsonArrayMenu = (JSONArray) _jsonObject.get(JSON_MENU_OBJECT_KEY);
		Iterator<?> menuArrayIterator = jsonArrayMenu.iterator(); // Infer a generic type and cast the returns of the iterator methods in the loop. Cannot cast menuIterator to Iterator<JSONObject> here
		int i = 0;
		while (menuArrayIterator.hasNext()) {
			Log.i(LOGGER_TAG, "MenuParser -- iterating through the JSONArray");
			Log.i(LOGGER_TAG, "MenuParser -- i = " + i);

			JSONObject currentObject = (JSONObject) menuArrayIterator.next();
			//  Place items in a MenuItem[]
			_menu[i] = new MenuItem();
			_menu[i].setItemName((String) currentObject.get(JSON_ITEM_KEY));
			_menu[i].setPrice((Float.valueOf((String) currentObject.get(JSON_PRICE_KEY))).floatValue());
			_menu[i].setStationName((String) currentObject.get(JSON_STATION_KEY));
			_menu[i].setAvailability(Boolean.parseBoolean((String) currentObject.get(JSON_AVAILABILITY_KEY)));
			++i;
		}
	}
	private int numberOfMenuItems() {
		Log.i(LOGGER_TAG, "MenuParser -- numberOfMenuItems()");
		int menuSize = ((JSONArray) _jsonObject.get(JSON_MENU_OBJECT_KEY)).size();
		Log.i(LOGGER_TAG, "MenuParser -- numberOfMenuItems() -- menuSize = " + menuSize);
		
		return menuSize;
	}
}