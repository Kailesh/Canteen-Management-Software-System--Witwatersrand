/**
 * 
 */
package com.witwatersrand.androidapplication.cart;

import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.witwatersrand.androidapplication.ApplicationPreferences;
import com.witwatersrand.androidapplication.DeviceIDGenerator;
import com.witwatersrand.androidapplication.OrderItem;

import android.content.Context;
import android.util.Log;

/**
 * @author Kailesh
 *
 */
public class OrderEncoder {

	final private static String LOGGER_TAG = "WITWATERSRAND";
	private OrderItem[] _orderList;
	private String _orderJsonMessage;
	float _total;
	boolean _delivery;
	String _deliveryLocation;
	String _deviceID;
	int _orderNumber;
	Context _context;

	final private static String TOTAL_TAG = "total";
	final private static String DELIVERY_TAG = "deliveryLocation";
	final private static String BASKET_TAG = "basket";
	final private static String ITEM_NAME_TAG = "item";
	final private static String STATION_TAG = "station";
	final private static String PURCHASE_QUANTITY_TAG = "quantity";
	final private static String DEVICE_ID_TAG = "deviceID";
	final private static String ORDER_NUMBER_KEY = "orderNumber";
	final private static String BUYER_NAME = "name";

	
	public OrderEncoder(OrderItem[] order, int orderNumber, Context context) {
		Log.i(LOGGER_TAG, "OrderEncoder -- Constructor");
		this._orderList = order;
		_total = 0;
		_delivery = false;
		_deliveryLocation = "-";	
		_deviceID = DeviceIDGenerator.getWifiMacAddress(context);
		_context = context;
		_orderNumber = orderNumber;
	}
	
	// TODO Unchecked conversion - Type safety: The method put(Object, Object) 
	// belongs to the raw type HashMap. References to generic type HashMap<K,V> 
	// should be parameterized
	@SuppressWarnings("unchecked")
	public void encodeOrderIntoJson() {
		Log.i(LOGGER_TAG, "OrderEncoder -- encodeOrderIntoJson()");
		JSONObject myJsonObject = new JSONObject();
		JSONArray purchaseOrderList = new JSONArray();
		for(int i = 0; i<_orderList.length; i++) {
			JSONObject myMenuItem = new JSONObject();
			
			myMenuItem.put(PURCHASE_QUANTITY_TAG, _orderList[i].getPurchaseQuantity());
			myMenuItem.put(STATION_TAG, _orderList[i].getStationName());
			myMenuItem.put(ITEM_NAME_TAG, _orderList[i].getItemName());
			purchaseOrderList.add(myMenuItem);
		}
		myJsonObject.put(BASKET_TAG, purchaseOrderList);
		if (isToBeDelivered()) {
			myJsonObject.put(DELIVERY_TAG, _deliveryLocation);
		} else {
			myJsonObject.put(DELIVERY_TAG, "-");
		}
		myJsonObject.put(DELIVERY_TAG, _deliveryLocation);
		myJsonObject.put(TOTAL_TAG, _total);
		myJsonObject.put(DEVICE_ID_TAG, _deviceID);
		myJsonObject.put(ORDER_NUMBER_KEY, _orderNumber);
		myJsonObject.put(BUYER_NAME, ApplicationPreferences.getUserName(_context));

		StringWriter myStringWriter = new StringWriter();
		try {
			myJsonObject.writeJSONString(myStringWriter);
			_orderJsonMessage = myStringWriter.toString();
			Log.i(LOGGER_TAG, _orderJsonMessage);
		} catch (IOException e) {
			Log.i(LOGGER_TAG, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @return the orderJsonMessage
	 */
	public String getOrderJsonMessage() {
		Log.i(LOGGER_TAG, "OrderEncoder -- getOrderJsonMessage()");
		return _orderJsonMessage;
	}

	/**
	 * @param orderJsonMessage the orderJsonMessage to set
	 */
	public void setOrderJsonMessage(String orderJsonMessage) {
		Log.i(LOGGER_TAG, "OrderEncoder -- setOrderJsonMessage()");
		this._orderJsonMessage = orderJsonMessage;
	}
	
	/**
	 * @return the total
	 */
	public float getTotal() {
		Log.i(LOGGER_TAG, "OrderEncoder -- getTotal()");
		return _total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(float total) {
		Log.i(LOGGER_TAG, "OrderEncoder -- setTotal()");
		this._total = total;
	}

	/**
	 * @return the delivery
	 */
	public boolean isToBeDelivered() {
		Log.i(LOGGER_TAG, "OrderEncoder -- isToBeDelivered()");
		return _delivery;
	}

	/**
	 * @param delivery the delivery to set
	 */
	public void setDelivery(boolean delivery) {
		Log.i(LOGGER_TAG, "OrderEncoder -- setDelivery()");
		this._delivery = delivery;
	}
	
	/**
	 * @return the _deliveryLocation
	 */
	public String getDeliveryLocation() {
		Log.i(LOGGER_TAG, "OrderEncoder -- getDeliveryLocation()");
		return _deliveryLocation;
	}

	/**
	 * @param _deliveryLocation the _deliveryLocation to set
	 */
	public void setDeliveryLocation(String _deliveryLocation) {
		Log.i(LOGGER_TAG, "OrderEncoder -- setDeliveryLocation()");
		Log.i(LOGGER_TAG, "OrderEncoder -- _deliveryLocation = |" + _deliveryLocation + "|");
		this._deliveryLocation = _deliveryLocation;
	}
	
	public String getDeviceID() {
		Log.i(LOGGER_TAG, "OrderEncoder -- setDeliveryLocation()");
		return _deviceID;
	}

	/**
	 * @param _deviceID the _deviceID to set
	 */
	public void setDeviceID(String _deviceID) {
		Log.i(LOGGER_TAG, "OrderEncoder -- setDeviceID()");
		this._deviceID = _deviceID;
	}
}