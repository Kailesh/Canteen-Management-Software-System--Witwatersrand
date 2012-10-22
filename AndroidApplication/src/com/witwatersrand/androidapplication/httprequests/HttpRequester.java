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
 * Abstract class that defines aspects of a HTTP request
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
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
	 * Constructor
	 * @param uri 
	 * 
	 */
	public HttpRequester(String uri) {
		Log.i(LOGGER_TAG, "HttpRequester -- Constructor");
		setUri(uri);
		setTimeout(TIMEOUT_MILLISEC);
	}
	
	/**
	 * @return the time-out
	 */
	public int getTimeout() {
		Log.i(LOGGER_TAG, "HttpRequester -- getTimeout()");
		return TIMEOUT_MILLISEC;
	}

	/**
	 * @param timeoutMiliseconds the time-out to set
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
	 * @return the response message
	 */
	public String getResponseMessage() {
		Log.i(LOGGER_TAG, "HttpRequester -- getResponseMessage()");
		return responseMessage;
	}
	
	
	/**
	 * @return the response not OK string
	 */
	public static String getResponseNotOkMessage() {
		return RESPONSE_NOT_OK;
	}

	/**
	 * @return the exception thrown string
	 */
	public static String getExceptionThrownMessage() {
		return EXCEPTION_THROWN;
	}

	/**
	 * The derived classes are to implement receiveResponse()
	 * @return the response message
	 */
	abstract String receiveResponse();
	
	/**
	 * Set the URI
	 * @param uriAsString the URI string for the HTTP request
	 */
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
