/**
 * 
 */
package com.witwatersrand.androidapplication.httprequests;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * An HTTP GET request class
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class HttpGetRequester extends HttpRequester {
	final private String LOGGER_TAG = "WITWATERSRAND";
	private HttpGet _getRequest;
	
	
	public HttpGetRequester(String uri) {
		super(uri);
		Log.i(LOGGER_TAG, "HttpGetRequester -- Constructor");
		_getRequest = new HttpGet(uri);
	}
	
	/**
	 * Executes the HTTP GET request
	 * @return the response message
	 */
	public String receiveResponse() {
		Log.i(LOGGER_TAG, "HttpGetRequester -- receiveResponse()");
		
		try {
			HttpResponse response = _client.execute(_getRequest);
			Log.i(LOGGER_TAG, "HttpGetRequester -- receiveResponse() -- HTTP request complete");
			Log.i(LOGGER_TAG, "HttpGetRequester -- receiveResponse() -- Status Code = |" + response.getStatusLine().getStatusCode() + "|");
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Log.i(LOGGER_TAG, "HttpGetRequester -- receiveResponse() -- HTTP OK");
				responseMessage = EntityUtils.toString(response.getEntity());
				Log.i(LOGGER_TAG, "HttpGetRequester -- receiveResponse() -- responseMessage = |" + responseMessage + "|");
				return responseMessage;
			} else {
				Log.i(LOGGER_TAG, "HttpGetRequester -- receiveResponse() -- HTTP Response status code not OK");
				return RESPONSE_NOT_OK;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(LOGGER_TAG, "HttpGetRequester -- receiveResponse() -- Exception = |" + e.getMessage() + "|");
			return EXCEPTION_THROWN;
		}
	}
}