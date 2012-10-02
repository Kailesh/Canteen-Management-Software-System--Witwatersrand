/**
 * 
 */
package com.witwatersrand.androidapplication;

import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import android.util.Log;

/**
 * @author Kailesh
 *
 */
public class OrderEncoder {

	final String loggerTag = "WITWATERSRAND";
	private MenuItem[] _orderList;
	private String _orderJsonMessage;
	float _total;
	boolean _delivery;
	String _deliveryLocation;
	String _deviceID;


	String TOTAL_TAG = "total";
	String DELIVERY_TAG = "deliveryLocation";
	String BASKET_TAG = "basket";
	String ITEM_NAME_TAG = "item";
	String STATION_TAG = "station";
	String PURCHASE_QUANTITY_TAG = "quantity";
	String DEVICE_ID_TAG = "deviceID";
	
	
	
	public OrderEncoder(MenuItem[] order) {
		this._orderList = order;
		_total = 0;
		_delivery = false;
		_deliveryLocation = "Random";
		_deviceID = "56:78:3D:E5:8F:N1";
		
		encodeOrderIntoJson();
	}
	
	/**
	 * @return the _deviceID
	 */
	

	private void encodeOrderIntoJson() {
		JSONObject myJsonObject = new JSONObject();
		JSONArray purchaseOrderList = new JSONArray();
		for(int i = 0; i<_orderList.length; i++) {
			JSONObject myMenuItem = new JSONObject();
			// TODO Sort out these warnings
			myMenuItem.put(PURCHASE_QUANTITY_TAG, _orderList[i].getQuantity());
			myMenuItem.put(STATION_TAG, _orderList[i].getStationName());
			myMenuItem.put(ITEM_NAME_TAG, _orderList[i].getItemName());
			purchaseOrderList.add(myMenuItem);
		}
		myJsonObject.put(BASKET_TAG, purchaseOrderList);
		myJsonObject.put(DELIVERY_TAG, _deliveryLocation);
		myJsonObject.put(TOTAL_TAG, _total);
		myJsonObject.put(DEVICE_ID_TAG, _deviceID);

		StringWriter myStringWriter = new StringWriter();
		try {
			myJsonObject.writeJSONString(myStringWriter);
			_orderJsonMessage = myStringWriter.toString();
			Log.i(loggerTag, _orderJsonMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i(loggerTag, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @return the orderJsonMessage
	 */
	public String getOrderJsonMessage() {
		return _orderJsonMessage;
	}

	/**
	 * @param orderJsonMessage the orderJsonMessage to set
	 */
	public void setOrderJsonMessage(String orderJsonMessage) {
		this._orderJsonMessage = orderJsonMessage;
	}
	
	/**
	 * @return the total
	 */
	public float getTotal() {
		return _total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(float total) {
		this._total = total;
	}

	/**
	 * @return the delivery
	 */
	public boolean isToBeDelivered() {
		return _delivery;
	}

	/**
	 * @param delivery the delivery to set
	 */
	public void setDelivery(boolean delivery) {
		this._delivery = delivery;
	}
	
	/**
	 * @return the _deliveryLocation
	 */
	public String get_deliveryLocation() {
		return _deliveryLocation;
	}

	/**
	 * @param _deliveryLocation the _deliveryLocation to set
	 */
	public void set_deliveryLocation(String _deliveryLocation) {
		this._deliveryLocation = _deliveryLocation;
	}
	
	public String get_deviceID() {
		return _deviceID;
	}

	/**
	 * @param _deviceID the _deviceID to set
	 */
	public void set_deviceID(String _deviceID) {
		this._deviceID = _deviceID;
	}
}