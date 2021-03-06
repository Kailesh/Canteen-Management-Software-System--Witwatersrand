/**
 * 
 */
package com.witwatersrand.androidapplication.menuitems;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.witwatersrand.androidapplication.InvalidPriceException;
import com.witwatersrand.androidapplication.MenuItem;

import android.util.Log;

/**
 * Parser that parses the menu items
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class MenuParser {
	
	final String LOGGER_TAG = "WITWATERSRAND";
	MenuItem[] _menu;
	JSONObject _jsonObject;

	String JSON_MENU_OBJECT_KEY = "menu";
	String JSON_ITEM_KEY = "item";
	String JSON_PRICE_KEY = "price";
	String JSON_STATION_KEY = "station";
	String JSON_AVAILABILITY_KEY = "availability";
	int _numberOfMenuItems;

	public MenuParser(String jsonMenuMessage) throws NumberFormatException {
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
				
			setMenuItems();
			Log.i(LOGGER_TAG, "MenuParser -- parseMenuData() -- Data parsed");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(LOGGER_TAG, "Exception -- " + e.getMessage());
		}
	}

	/**
	 * @return the menu
	 */
	public MenuItem[] getMenu() {
		Log.i(LOGGER_TAG, "MenuParser -- getMenu()");
		return _menu;
	}

	/**
	 * @throws InvalidPriceException 
	 * @throws NumberFormatException 
	 *
	 */
	private void setMenuItems() throws NumberFormatException {
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
			try {
				_menu[i].setPrice((Float.valueOf((String) currentObject.get(JSON_PRICE_KEY))).floatValue());
			} catch (InvalidPriceException e) {
				e.printStackTrace();
				Log.i(LOGGER_TAG, "MenuParser -- setMenuItems() -- Should never reach here");
			}
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