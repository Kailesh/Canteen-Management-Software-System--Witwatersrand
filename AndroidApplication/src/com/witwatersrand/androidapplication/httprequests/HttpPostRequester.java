package com.witwatersrand.androidapplication.httprequests;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * An HTTP POST request class
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class HttpPostRequester extends HttpRequester {
	final private String LOGGER_TAG = "WITWATERSRAND";
	final static String POST_MESSAGE_NOT_SET = "post_message_not_set";
	private HttpPost _postRequest;
	private String _postMessage;

	/**
	 * 
	 */
	public HttpPostRequester(String uri) {
		super(uri);
		Log.i(LOGGER_TAG, "HttpPostRequester -- Constructor");
		_postRequest = new HttpPost(uri);
		_postMessage = POST_MESSAGE_NOT_SET;
	}
	
	/**
	 * @return the post message
	 */
	public String getPostMessage() {
		return _postMessage;
	}
	
	/**
	 * Set the POST message
	 * @param postMessage the post message to set
	 */
	public void setPostMessage(String postMessage) {
		_postMessage = postMessage;
		
		StringEntity message;
		try {
			message = new StringEntity(postMessage);
			_postRequest.addHeader("content-type", "applcation/json");
			_postRequest.setEntity(message);
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(LOGGER_TAG, "HttpPostRequester -- setPostMessage() -- Exception = |" + e.getMessage() + "|");
		}
	}

	/**
	 * Executes the HTTP POST request
	 * @return the response message
	 */
	public String receiveResponse() {
		Log.i(LOGGER_TAG, "HttpPostRequester -- receiveResponse()");
		
		try {
			HttpResponse response = _client.execute(_postRequest);
			Log.i(LOGGER_TAG, "HttpPostRequester -- receiveResponse() -- HTTP request complete");
			Log.i(LOGGER_TAG, "HttpPostRequester -- receiveResponse() -- Status Code = |" + response.getStatusLine().getStatusCode() + "|");
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Log.i(LOGGER_TAG, "HttpPostRequester -- receiveResponse() -- HTTP OK");
				responseMessage = EntityUtils.toString(response.getEntity());
				Log.i(LOGGER_TAG, "HttpPostRequester -- receiveResponse() -- responseMessage = |" + responseMessage + "|");
				return responseMessage;
			} else {
				Log.i(LOGGER_TAG, "HttpPostRequester -- receiveResponse() -- HTTP Response status code not OK");
				return RESPONSE_NOT_OK;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(LOGGER_TAG, "HttpPostRequester -- receiveResponse() -- Exception = |" + e.getMessage() + "|");
			return EXCEPTION_THROWN;
		}
	}
}