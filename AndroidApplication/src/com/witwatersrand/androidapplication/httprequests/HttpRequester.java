/**
 * 
 */
package com.witwatersrand.androidapplication.httprequests;

import java.net.URI;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

/**
 * @author Kailesh
 *
 */
public abstract class HttpRequester {
	
	final private String LOGGER_TAG = "WITWATERSRAND";
	private int TIMEOUT_MILLISEC = 60000; // = 60 seconds
	final static String RESPONSE_NOT_OK = "Response Not OK";
	final static String EXCEPTION_THROWN = "Exception Thrown";
	URI _uri;
	HttpClient _client;
	String responseMessage;
	
	/**
	 * @param uri 
	 * 
	 */
	public HttpRequester(String uri) {
		Log.i(LOGGER_TAG, "HttpRequester -- Constructor");
		setUri(uri);
		setTimeout(TIMEOUT_MILLISEC);
	}
	
	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		Log.i(LOGGER_TAG, "HttpRequester -- getTimeout()");
		return TIMEOUT_MILLISEC;
	}

	/**
	 * @param timeoutMiliseconds the timeout to set
	 */
	public void setTimeout(int timeoutMiliseconds) {
		Log.i(LOGGER_TAG, "HttpRequester -- setTimeout()");
		TIMEOUT_MILLISEC = timeoutMiliseconds;
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
		_client = new DefaultHttpClient(httpParams);
	}
	
	/**
	 * @return the responseMessage
	 */
	public String getResponseMessage() {
		Log.i(LOGGER_TAG, "HttpRequester -- getResponseMessage()");
		return responseMessage;
	}
	
	
	
	/**
	 * @return the responseNotOk
	 */
	public static String getResponseNotOkMessage() {
		return RESPONSE_NOT_OK;
	}

	/**
	 * @return the eXCEPTION_THROWN
	 */
	public static String getExceptionThrownMessage() {
		return EXCEPTION_THROWN;
	}

	abstract String receiveResponse();
	
	public void setUri(String uriAsString) {
		Log.i(LOGGER_TAG, "HttpRequester -- setUri()");		
		try {
			_uri = new URI(uriAsString);
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(LOGGER_TAG, "HttpRequester -- setUri() -- Exception = |" + e.getMessage() + "|");
		}
	}
	
}
