/**
 * 
 */
package com.witwatersrand.androidapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Kailesh
 * 
 */
public class CanteenManagerDatabase {
	
	private static final String loggerTag = "WITWATERSRAND";

	public static final String KEY_ROWID = "_id";
	public static final String KEY_ITEM_NAME = "item_name ";
	public static final String KEY_STATION = "station";
	public static final String KEY_PRICE = "price";
	public static final String KEY_AVAILABILITY = "availability";
	public static final String KEY_PURCHASE_QUANTITY = "purchase_quantity";

	private static final String DATABASE_NAME = "canteen_manager_database";
	private static final String DATABASE_TABLE_MENU_ITEMS = "menu_items_table";
	private static final String DATABASE_TABLE_ORDER = "order_table_";
	private static final int DATABASE_VERSION = 1;
	
	private int _numberOrders = 0;

	private DBHelper _helper;
	private final Context _context;
	private SQLiteDatabase _database;
	
	public CanteenManagerDatabase(Context context) {
		Log.i(loggerTag, "CanteenManagerDatabase -- Constructor");
		this._context = context;
	}
	
	public CanteenManagerDatabase open() throws SQLException {
		Log.i(loggerTag, "CanteenManagerDatabase -- open()");
		_helper = new DBHelper(_context);
		_database = _helper.getWritableDatabase();
		return this;
	}
	
	public void close() throws SQLException {
		Log.i(loggerTag, "CanteenManagerDatabase -- close()");
		_helper.close();
	}
	
	public long addMenuItem(MenuItem item) throws SQLException  {
		Log.i(loggerTag, "CanteenManagerDatabase -- addmenuItem()");
		ContentValues newRow = new ContentValues();
		newRow.put(KEY_ITEM_NAME, item.getItemName());
		newRow.put(KEY_STATION, item.getStationName());
		newRow.put(KEY_PRICE, item.getPrice());
		newRow.put(KEY_AVAILABILITY, item.getAvailability());
		return _database.insert(DATABASE_TABLE_MENU_ITEMS, null, newRow);
	}
	
	public void setMenu(MenuItem[] menu) throws SQLException {
		Log.i(loggerTag, "CanteenManagerDatabase -- setMenu()");
		for(int i = 0; i < menu.length; i++) {
			addMenuItem(menu[i]);
		}
	}
	
	public MenuItem[] getMenu() throws SQLException {
		Log.i(loggerTag, "CanteenManagerDatabase -- getMenu");
		String[] columns = new String[]{KEY_ROWID, KEY_ITEM_NAME, KEY_STATION, KEY_PRICE, KEY_AVAILABILITY, KEY_PURCHASE_QUANTITY};
		Cursor myCursor = _database.query(DATABASE_TABLE_MENU_ITEMS, columns, null, null, null, null, null, null);
		
		int iName = myCursor.getColumnIndex(KEY_ITEM_NAME);
		int iStation = myCursor.getColumnIndex(KEY_STATION);
		int iPrice = myCursor.getColumnIndex(KEY_PRICE);
		int iAvailability = myCursor.getColumnIndex(KEY_AVAILABILITY);
		int iQuantity = myCursor.getColumnIndex(KEY_PRICE);
		
		MenuItem[] menu = new MenuItem[myCursor.getCount()];
		
		int i = 0;	
		for (myCursor.moveToFirst(); !myCursor.isAfterLast(); myCursor.moveToNext()) {
			menu[i].setItemName(myCursor.getString(iName));
			menu[i].setStationName(myCursor.getString(iStation));
			menu[i].setPrice(myCursor.getFloat(iPrice));
			
			boolean tempAvailability = (myCursor.getInt(iAvailability) == 1);
			menu[i].setAvailability(tempAvailability);
			menu[i].setQuantity(myCursor.getInt(iQuantity));
		}
		
		return menu;
	}
	
	public void deleteAllItems() throws SQLException {
		Log.i(loggerTag, "CanteenManagerDatabase -- deleteallItems()");
		_database.delete(DATABASE_TABLE_MENU_ITEMS, null, null);
	}
	
	
	public void updateAvailability(String name, boolean availability) throws SQLException {
		Log.i(loggerTag, "CanteenManagerDatabase -- updateAvailability()");
		ContentValues updateRow =  new ContentValues();
		updateRow.put(KEY_AVAILABILITY, availability);
		_database.update(DATABASE_TABLE_MENU_ITEMS, updateRow, KEY_ITEM_NAME + "=" + name, null);
	}
	
	public void updatePurchaseQuantity(String name, int quantity) throws SQLException {
		Log.i(loggerTag, "CanteenManagerDatabase -- updatePurchaseQuantity()");
		ContentValues updateRow =  new ContentValues();
		updateRow.put(KEY_PURCHASE_QUANTITY, quantity);
		_database.update(DATABASE_TABLE_MENU_ITEMS, updateRow, KEY_ITEM_NAME + "=" + name, null); // Change the table name here
	}
	
	public MenuItem[] getStationItems(String station) throws SQLException {
		Log.i(loggerTag, "CanteenManagerDatabase -- getStationItems()");
		String[] columns = new String[]{KEY_ROWID, KEY_ITEM_NAME, KEY_STATION, KEY_PRICE, KEY_AVAILABILITY, KEY_PURCHASE_QUANTITY};
		Cursor stationCursor = _database.query(DATABASE_TABLE_MENU_ITEMS, columns, KEY_STATION + "=" + station, null, null, null, null);

		int iName = stationCursor.getColumnIndex(KEY_ITEM_NAME);
		int iStation = stationCursor.getColumnIndex(KEY_STATION);
		int iPrice = stationCursor.getColumnIndex(KEY_PRICE);
		int iAvailability = stationCursor.getColumnIndex(KEY_AVAILABILITY);
		int iQuantity = stationCursor.getColumnIndex(KEY_PRICE);
		
		MenuItem[] menu = new MenuItem[stationCursor.getCount()];
		
		int i = 0;	
		for (stationCursor.moveToFirst(); !stationCursor.isAfterLast(); stationCursor.moveToNext()) {
			menu[i].setItemName(stationCursor.getString(iName));
			menu[i].setStationName(stationCursor.getString(iStation));
			menu[i].setPrice(stationCursor.getFloat(iPrice));
			
			boolean tempAvailability = (stationCursor.getInt(iAvailability) == 1);
			menu[i].setAvailability(tempAvailability);
			menu[i].setQuantity(stationCursor.getInt(iQuantity));
		}
		return menu;
	}
	
	
	
	/**
	 * @return the numberOrders
	 */
	public int getNumberOrders() {
		Log.i(loggerTag, "CanteenManagerDatabase -- getNumberOrders()");
		return _numberOrders;
	}

	/**
	 * @param numberOrders the numberOrders to set
	 */
	public void setNumberOrders(int numberOrders) {
		Log.i(loggerTag, "CanteenManagerDatabase -- setNumber()");
		this._numberOrders = numberOrders;
	}
	
	public void createOrderTable(MenuItem[] order) throws SQLException {
		Log.i(loggerTag, "CanteenManagerDatabase -- createOrderTable()");
		_numberOrders++;
		String newTableExecutionString = "CREATE TABLE " + 
					DATABASE_TABLE_ORDER + _numberOrders + " (" +
					KEY_ITEM_NAME + " TEXT NOT NULL, " +
					KEY_STATION + " TEXT NOT NULL, " +
					KEY_PURCHASE_QUANTITY + " INTEGER NOT NULL DEFAULT 0);";
		// TODO PLACE ORDERS IN TABLE
		_database.execSQL(newTableExecutionString);
	}

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
		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.i(loggerTag, "CanteenManagerDatabase -- DBHelper -- Constructor");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
		 	// Called the first time the database is ever created
			Log.i(loggerTag, "CanteenManagerDatabase -- DBHelper -- onCreate()");
			db.execSQL( "CREATE TABLE " +  DATABASE_TABLE_MENU_ITEMS + " (" + 
					KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					KEY_ITEM_NAME + " TEXT NOT NULL, " + 
					KEY_STATION + " TEXT NOT NULL, " + 
					KEY_PRICE + " REAL NOT NULL DEFAULT 0," +
					KEY_AVAILABILITY + " INTEGER NOT NULL DEFAULT 1, " +
					KEY_PURCHASE_QUANTITY + " INTEGER NOT NULL DEFAULT 0);"
					);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Called if the table exists
			Log.i(loggerTag, "CanteenManagerDatabase -- DBHelper -- onUpgrade()");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_MENU_ITEMS );
			onCreate(db);

		}
	}
}