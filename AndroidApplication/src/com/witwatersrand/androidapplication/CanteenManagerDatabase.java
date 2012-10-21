/**
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering 
 */
package com.witwatersrand.androidapplication;

import com.witwatersrand.androidapplication.progressrequester.Progress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Provides functionality to manage the local database on the Android phone
 */
public class CanteenManagerDatabase {
	
	// TODO make sure that this class is not prone to SQL injection attacks
	private static final String LOGGER_TAG = "WITWATERSRAND";
	private static final String KEY_ITEM_NAME = "item_name";
	private static final String KEY_STATION = "station";
	private static final String KEY_PRICE = "price";
	private static final String KEY_AVAILABILITY = "availability";
	private static final String KEY_PURCHASE_QUANTITY = "purchase_quantity";
	private static final String KEY_ORDER = "order_number";
	private static final String KEY_ITEM_STATUS = "item_status";

	private static final String DATABASE_NAME = "canteen_manager_database";
	private static final String DATABASE_TABLE_MENU_ITEMS = "menu_items_table";
	private static final String DATABASE_TABLE_ORDER = "order_table";
	
	private static int DATABASE_VERSION = 1;

	private DBHelper _helper;
	private final Context _context;
	private SQLiteDatabase _database;
	
	/**
	 * Constructor
	 * @param context
	 */
	public CanteenManagerDatabase(Context context) {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- Constructor");
		this._context = context;
	}
	
	/**
	 * Opens the database (make sure to close the database using close())
	 * @see close()
	 * @return An instance of the database
	 * @throws SQLException
	 */
	public CanteenManagerDatabase open() throws SQLException {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- open()");
		_helper = new DBHelper(_context, DATABASE_VERSION);
		_database = _helper.getWritableDatabase();
		return this;
	}
	
	/**
	 * Closes the database (need to open the database using open() first)
	 * @see open()
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- close()");
		_helper.close();
	}
	
	/**
	 * Add a menu item to the menu items table
	 * @see MenuItem
	 * @param item A MenuItem object
	 * @throws SQLException
	 */
	public void addMenuItem(MenuItem item) throws SQLException  {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- addmenuItem()");
		ContentValues newRow = new ContentValues();
		newRow.put(KEY_ITEM_NAME, item.getItemName());
		newRow.put(KEY_STATION, item.getStationName());
		newRow.put(KEY_PRICE, item.getPrice());
		newRow.put(KEY_AVAILABILITY, item.isAvailable());
		_database.insert(DATABASE_TABLE_MENU_ITEMS, null, newRow);
	}
	
	/**
	 * Add menu items to the menu items table
	 * @see MenuItem
	 * @param menu A MenuItem array that contains a menu
	 * @throws SQLException
	 */
	public void setMenu(MenuItem[] menu) throws SQLException {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- setMenu()");
		for(int i = 0; i < menu.length; i++) {
			addMenuItem(menu[i]);
		}
	}
	
	/**
	 * Get the menu currently stored in the menu table
	 * @see MenuItem
	 * @return MenuItem array
	 * @throws SQLException
	 */
	public MenuItem[] getMenu() throws SQLException {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- getMenu");
		String[] columns = new String[]{KEY_ITEM_NAME, KEY_STATION, KEY_PRICE, KEY_AVAILABILITY};
		Cursor myCursor = _database.query(DATABASE_TABLE_MENU_ITEMS, columns, null, null, null, null, null, null);
		
		int iName = myCursor.getColumnIndex(KEY_ITEM_NAME);
		int iStation = myCursor.getColumnIndex(KEY_STATION);
		int iPrice = myCursor.getColumnIndex(KEY_PRICE);
		int iAvailability = myCursor.getColumnIndex(KEY_AVAILABILITY);
		
		MenuItem[] menu = new MenuItem[myCursor.getCount()];
		
		int i = 0;	
		for (myCursor.moveToFirst(); !myCursor.isAfterLast(); myCursor.moveToNext()) {			
			menu[i] = new MenuItem();
			menu[i].setItemName(myCursor.getString(iName));
			menu[i].setStationName(myCursor.getString(iStation));
			try {
				menu[i].setPrice(myCursor.getFloat(iPrice));
			} catch (InvalidPriceException e) {
				e.printStackTrace();
				Log.i(LOGGER_TAG, "CanteenManagerDatabase -- getMenu() -- Should never reach here");
			}
			boolean tempAvailability = (myCursor.getInt(iAvailability) == 1);
			menu[i].setAvailability(tempAvailability);
			i++;
		}
		myCursor.close();
		return menu;
	}
	
	/**
	 * Removes all the rows in the menu items table
	 * @throws SQLException
	 */
	public void removeAllMenuItems() throws SQLException {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- removeAllMenuItems()");
		_database.delete(DATABASE_TABLE_MENU_ITEMS, null, null);
	}
	
	/**
	 * Update the availability status of an item in the menu table 
	 * @param name the name of the product that has to be updated
	 * @param availability the new availability status will be updated to this parameter
	 * @throws SQLException
	 */
	public void updateAvailability(String name, boolean availability) throws SQLException {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- updateAvailability()");
		ContentValues updateRow =  new ContentValues();
		updateRow.put(KEY_AVAILABILITY, availability);
		_database.update(DATABASE_TABLE_MENU_ITEMS, updateRow, KEY_ITEM_NAME + "=" + name, null);
	}
	
	/**
	 * Get the list of menu items for a specified station
	 * @param station the station name
	 * @return MenuItem array for a specific station
	 * @throws SQLException
	 */
	public MenuItem[] getStationItems(String station) throws SQLException {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- getStationItems()");
		String[] columns = new String[]{KEY_ITEM_NAME, KEY_STATION, KEY_PRICE, KEY_AVAILABILITY, KEY_PURCHASE_QUANTITY};
		Cursor stationCursor = _database.query(DATABASE_TABLE_MENU_ITEMS, columns, KEY_STATION + "=" + station, null, null, null, null);

		int iName = stationCursor.getColumnIndex(KEY_ITEM_NAME);
		int iStation = stationCursor.getColumnIndex(KEY_STATION);
		int iPrice = stationCursor.getColumnIndex(KEY_PRICE);
		int iAvailability = stationCursor.getColumnIndex(KEY_AVAILABILITY);
		
		MenuItem[] menu = new MenuItem[stationCursor.getCount()];
		
		int i = 0;	
		for (stationCursor.moveToFirst(); !stationCursor.isAfterLast(); stationCursor.moveToNext()) {
			menu[i].setItemName(stationCursor.getString(iName));
			menu[i].setStationName(stationCursor.getString(iStation));
			try {
				menu[i].setPrice(stationCursor.getFloat(iPrice));
			} catch (InvalidPriceException e) {
				e.printStackTrace();
				Log.i(LOGGER_TAG, "CanteenManagerDatabase -- getStationItems() -- Should never reach here");
			}
			boolean tempAvailability = (stationCursor.getInt(iAvailability) == 1);
			menu[i].setAvailability(tempAvailability);
		}
		stationCursor.close();
		return menu;
	}
	
	/**
	 * Add a purchase item to the order table
	 * @param menuItemToPurchase a MenuItem
	 * @param quantity the quantity for this item
	 * @param orderNumber the order number to which this item is to be added
	 * @throws SQLException
	 */
	public void addPurchaseItemToOrder(MenuItem menuItemToPurchase, int quantity, int orderNumber)  throws SQLException {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- addPurchaseItemToOrder()");
		ContentValues newRow = new ContentValues();
		if (isInOrderTable(menuItemToPurchase.getItemName(), orderNumber) ) {
			updatePurchaseQuantity(menuItemToPurchase.getItemName(), quantity, orderNumber); 
		} else {
			newRow.put(KEY_ITEM_NAME, menuItemToPurchase.getItemName());
			newRow.put(KEY_STATION, menuItemToPurchase.getStationName());
			newRow.put(KEY_PRICE, menuItemToPurchase.getPrice());
			newRow.put(KEY_PURCHASE_QUANTITY, quantity);
			newRow.put(KEY_ORDER, orderNumber);
			_database.insert(DATABASE_TABLE_ORDER, null, newRow);
		}
	}
	
	/**
	 * Updates an order item already in the order table
	 * @param name the name of the order item
	 * @param quantity the quantity for the item
	 * @param orderNumber the order number to which this item is to be added
	 * @throws SQLException
	 */
	public void updatePurchaseQuantity(String name, int quantity, int orderNumber) throws SQLException {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- updatePurchaseQuantity()");
		ContentValues updateRow =  new ContentValues();
		updateRow.put(KEY_PURCHASE_QUANTITY, quantity);
		_database.update(DATABASE_TABLE_ORDER, updateRow, KEY_ITEM_NAME + "='" + name +"' AND " +  KEY_ORDER + "='" + orderNumber + "'", null); // Change the table name here
	}
	
	/**
	 * Check for whether an item is is in the order table or not
	 * @param itemName the item name
	 * @param orderNumber the order number
	 * @return true if the item is in the order table
	 */
	public boolean isInOrderTable(String itemName, int orderNumber) {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- isInOrderTable()");
		String[] columns = new String[]{KEY_ITEM_NAME, KEY_STATION, KEY_PRICE, KEY_PURCHASE_QUANTITY, };
		Cursor stationCursor = _database.query(DATABASE_TABLE_ORDER, columns, KEY_ITEM_NAME + "='" + itemName + "'" + " AND " + KEY_ORDER + "='" + orderNumber + "'", null, null, null, null);

		if (stationCursor.getCount() == 0) {
			stationCursor.close();
			return false;
		} else {
			stationCursor.close();
			return true;
		}
	}
	
	/**
	 * Removes all the order items in the order table
	 */
	public void removeAllOrderItems() {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- removeAllOrderItems()");
		_database.delete(DATABASE_TABLE_ORDER, null, null);
	}
	
	/**
	 * Get the order for a specified order number from the order table
	 * @see OrderItem
	 * @param orderNumner the order number for which the OrderItem array is to be retrieved
	 * @return OrderItem[] for the specified order number
	 */
	public OrderItem[] getOrder(int orderNumber) {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- getOrder()");
		String[] columns = new String[]{KEY_ITEM_NAME, KEY_STATION, KEY_PRICE, KEY_PURCHASE_QUANTITY, KEY_ORDER};
		Cursor orderCursor = _database.query(DATABASE_TABLE_ORDER, columns, KEY_ORDER + "='" + orderNumber + "'", null, null, null, null);
		int iName = orderCursor.getColumnIndex(KEY_ITEM_NAME);
		int iStation = orderCursor.getColumnIndex(KEY_STATION);
		int iPrice = orderCursor.getColumnIndex(KEY_PRICE);
		int iQuantity = orderCursor.getColumnIndex(KEY_PURCHASE_QUANTITY);

		OrderItem[] orderList = new OrderItem[orderCursor.getCount()];

		int i = 0;
		for (orderCursor.moveToFirst(); !orderCursor.isAfterLast(); orderCursor.moveToNext()) {
			orderList[i] = new OrderItem();
			orderList[i].setItemName(orderCursor.getString(iName));
			orderList[i].setStationName(orderCursor.getString(iStation));
			try {
				orderList[i].setPrice(orderCursor.getFloat(iPrice));
			} catch (InvalidPriceException e) {
				e.printStackTrace();
				Log.i(LOGGER_TAG, "CanteenManagerDatabase -- getOrder() -- Should never reach here");
			}
			orderList[i].setPurchaseQuantity(orderCursor.getInt(iQuantity));
			i++;
		}
		orderCursor.close();
		return orderList;
	}
	
	/**
	 * Get the purchased order for a specified order number from the order table
	 * @see OrderedItem
	 * @param orderNumber the order number for which the OrderedItem array is to be retrieved
	 * @return OrderedItem[] for the specified order number
	 */
	public OrderedItem[] getOrderedItemList(int orderNumber) {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- getOrderedItemList()");
				
		String[] columns = new String[]{KEY_ITEM_NAME, KEY_STATION, KEY_PRICE, KEY_PURCHASE_QUANTITY, KEY_ORDER, KEY_ITEM_STATUS};
		Cursor orderCursor = _database.query(DATABASE_TABLE_ORDER, columns, KEY_ORDER + "='" + orderNumber + "'", null, null, null, null);
		int iName = orderCursor.getColumnIndex(KEY_ITEM_NAME);
		int iStation = orderCursor.getColumnIndex(KEY_STATION);
		int iPrice = orderCursor.getColumnIndex(KEY_PRICE);
		int iQuantity = orderCursor.getColumnIndex(KEY_PURCHASE_QUANTITY);
		int iProgressStatus = orderCursor.getColumnIndex(KEY_ITEM_STATUS);

		OrderedItem[] orderedList = new OrderedItem[orderCursor.getCount()];
		int i = 0;
		for (orderCursor.moveToFirst(); !orderCursor.isAfterLast(); orderCursor.moveToNext()) {
			orderedList[i] = new OrderedItem();
			orderedList[i].setItemName(orderCursor.getString(iName));
			orderedList[i].setStationName(orderCursor.getString(iStation));
			try {
				orderedList[i].setPrice(orderCursor.getFloat(iPrice));
			} catch (InvalidPriceException e) {
				e.printStackTrace();
				Log.i(LOGGER_TAG, "CanteenManagerDatabase -- getOrderedItemList() -- Should never reach here");
			}
			orderedList[i].setPurchaseQuantity(orderCursor.getInt(iQuantity));
			orderedList[i].setState(Progress.valueOf(orderCursor.getString(iProgressStatus).toUpperCase()));
			i++;
		}
		orderCursor.close();
		return orderedList;
	}
	
	/**
	 * Updates the progress status of a purchased item in the order table
	 * @param name the name of the purchased item in the order table
	 * @param orderNumber the order number
	 * @param status the new progress status
	 */
	public void updateItemProgress(String name, int orderNumber, Progress status) {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- updateItemProgress()");
		ContentValues updateRow =  new ContentValues();
		Log.d(LOGGER_TAG, "CanteenManagerDatabase -- updateItemProgress() -- status = |" + status.toString() + "|");
		updateRow.put(KEY_ITEM_STATUS, "" + status.toString());
		_database.update(DATABASE_TABLE_ORDER, updateRow, KEY_ITEM_NAME + "='" + name +"' AND " +  KEY_ORDER + "='" + orderNumber + "'", null); // Change the table name here
	}
	
	/**
	 * Updates the progress of an entire order
	 * @param orderNumber the order number
	 * @param status the new progres status
	 */
	public void updateOrderProgress(int orderNumber, Progress status) {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- updateItemProgress()");
		ContentValues updateRow =  new ContentValues();
		updateRow.put(KEY_ITEM_STATUS, "" + status);
		
		_database.update(DATABASE_TABLE_ORDER, updateRow, KEY_ORDER + "='" + orderNumber + "'", null); // Change the table name here
	}
	
	/**
	 * Calculates the total for a specific order
	 * @param orderNumber the order number
	 * @return the total
	 */
	public float getTotalForOrder(int orderNumber) {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- getTotalForOrder()");
		final String sumOfOrders = "SELECT TOTAL(" + KEY_PURCHASE_QUANTITY + "*" + KEY_PRICE + ") AS my_sum FROM " + DATABASE_TABLE_ORDER + " WHERE " + KEY_ORDER + "='" + orderNumber + "'";
		Cursor totalCursor = _database.rawQuery(sumOfOrders, null);
		if(totalCursor.moveToFirst()) {
		    return totalCursor.getFloat(0);
		}
		totalCursor.close();
		return -1;
	}
	
	/**
	 * Check whether an order has been completed or not (status = DONE or DELIVERED)
	 * @param orderNumber the order number
	 * @return true if the order is completed
	 */
	public boolean isOrderReceived(int orderNumber) {
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- isOrderReceived()");

		final String sqlNumberOfItemsInOrder = "SELECT COUNT(*) AS my_items FROM " + DATABASE_TABLE_ORDER + " WHERE " + KEY_ORDER + " = " + orderNumber;
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- isOrderReceived() -- sqlNumberOfItemsInOrder = |" + sqlNumberOfItemsInOrder + "|");

		final String sqlNumberOfItemsInOrderDoneOrDelivered = "SELECT COUNT(*) AS my_completed_items FROM " + DATABASE_TABLE_ORDER + " WHERE (" + KEY_ITEM_STATUS + " = 'DONE' OR " + KEY_ITEM_STATUS + " = 'DELIVERED') AND " + KEY_ORDER +" = " + orderNumber;
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- isOrderReceived() -- sqlNumberOfItemsInOrderDoneOrDelivered = |" + sqlNumberOfItemsInOrderDoneOrDelivered + "|");


		int numberOfItems = -1;
		Cursor numberOfItemsC = _database.rawQuery(sqlNumberOfItemsInOrder, null);
		if(numberOfItemsC.moveToFirst()) {
			numberOfItems = numberOfItemsC.getInt(0);
			Log.i(LOGGER_TAG, "CanteenManagerDatabase -- isOrderReceived() -- numberOfItem = |" + numberOfItems + "|");

		}
		numberOfItemsC.close();

		int numberOfItemsDoneOrDelivered = -2;
		Cursor numberOfItemsDoneOrDeliveredC = _database.rawQuery(sqlNumberOfItemsInOrderDoneOrDelivered, null);
		if(numberOfItemsDoneOrDeliveredC.moveToFirst()) {
			numberOfItemsDoneOrDelivered = numberOfItemsDoneOrDeliveredC.getInt(0);
			Log.i(LOGGER_TAG, "CanteenManagerDatabase -- isOrderReceived() -- numberOfItem = |" + numberOfItemsDoneOrDelivered + "|");
		}
		numberOfItemsDoneOrDeliveredC.close();
		if (numberOfItems == numberOfItemsDoneOrDelivered) {
			Log.i(LOGGER_TAG, "CanteenManagerDatabase -- isOrderReceived() -- All status received" );	
			return true;
		}
		Log.i(LOGGER_TAG, "CanteenManagerDatabase -- isOrderReceived() -- Not all status received" );
		return false;
	}
	
	/**
	 * Remove a specified item from the order table
	 * @param name the name of the item
	 * @param orderNumber the order number
	 */
	public void removeItemFromOrderList(String name, int orderNumber) {
		_database.delete(DATABASE_TABLE_ORDER, KEY_ITEM_NAME + " = '" + name + "' AND " + KEY_ORDER + " = " + orderNumber , null);	
	}
	
	/**
	 * A database helper class that extends SQLiteOpenHelper
	 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
	 *
	 */
	private static class DBHelper extends SQLiteOpenHelper {

		/*		
			*****Normal Constructor parameters******
			----------------------------------------	
 			public DBHelper(Context context, String name, CursorFactory factory, int version) {
				super(context, name, factory, version); 
				}
		}*/
		
		/*
		 * Removing constructor parameters defining the name of the database,
		 * a cursor factory, and version of the database.
		 * 
		 */
		public DBHelper(Context context, int databaseVersion) {
			super(context, DATABASE_NAME, null, databaseVersion);
			Log.i(LOGGER_TAG, "CanteenManagerDatabase -- DBHelper -- Constructor");
		}

		/**
		 * Called when the database is initially created 
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
		 	// Called the first time the database is ever created
			Log.i(LOGGER_TAG, "CanteenManagerDatabase -- DBHelper -- onCreate()");
			db.execSQL( "CREATE TABLE " +  DATABASE_TABLE_MENU_ITEMS + " (" + 
					KEY_ITEM_NAME + " TEXT NOT NULL, " + 
					KEY_STATION + " TEXT NOT NULL, " + 
					KEY_PRICE + " REAL NOT NULL DEFAULT 0," +
					KEY_AVAILABILITY + " INTEGER NOT NULL DEFAULT 1);"
					);
			db.execSQL( "CREATE TABLE " +  DATABASE_TABLE_ORDER + " (" + 
					KEY_ITEM_NAME + " TEXT NOT NULL, " + 
					KEY_STATION + " TEXT NOT NULL, " +
					KEY_PRICE + " REAL NOT NULL DEFAULT 0," +
					KEY_PURCHASE_QUANTITY + " INTEGER NOT NULL DEFAULT 0," +
					KEY_ORDER + " INTEGER NOT NULL DEFAULT 0," +
					KEY_ITEM_STATUS + " TEXT NOT NULL DEFAULT NONE);"
					);
		}

		/**
		 * Called when the database is upgraded
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				Log.i(LOGGER_TAG, "CanteenManagerDatabase -- DBHelper -- onUpgrade()");
				db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_MENU_ITEMS);
				db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_ORDER);
				onCreate(db);
		}
	}
}
