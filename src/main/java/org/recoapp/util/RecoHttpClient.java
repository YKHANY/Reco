package org.recoapp.util;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class RecoHttpClient {
	private static final String BASE_URL = "http://211.189.19.64:9110/";
	private static AsyncHttpClient client = new AsyncHttpClient();
	private static RecoHttpClient recoHttpClient;
	private PersistentCookieStore store;
	
	public static synchronized RecoHttpClient getInstance()
	{
		if(recoHttpClient==null)
			recoHttpClient = new RecoHttpClient();
		return recoHttpClient;
	}
	
	public void get(String url, AsyncHttpResponseHandler responseHandler) {
		client.addHeader("User-Agent", "android");
		client.get(getAbsoluteUrl(url), responseHandler);
	}
	
	public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.addHeader("User-Agent", "android");
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}
	
	public void post(String url, AsyncHttpResponseHandler responseHandler) {
		client.addHeader("User-Agent", "android");
		client.post(getAbsoluteUrl(url), responseHandler);
	}
	
	public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.addHeader("User-Agent", "android");
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}
	
	public void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.addHeader("User-Agent", "android");
		client.post(context, getAbsoluteUrl(url), params, responseHandler);
	}
	
	public void setCookieStore(PersistentCookieStore store) {
		this.store = store;
		client.setCookieStore(store);
	}
	
	public PersistentCookieStore getStore() {
		return store;
	}

	public static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}
