package com.laddu;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

public class LadduHttpClient {
	private static String TAG = LadduHttpClient.class.getSimpleName();
	private String baseUrl;
	private String params;

	public LadduHttpClient(String url) {
		this.baseUrl = url;
	}

	public LadduHttpClient(String url, String params) {
		this.baseUrl = url;
		this.params = params;
	}

	private void addHeaders(HttpRequestBase reqType) {
		reqType.setHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	}

	public String get() {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(ChouchouUrls.protocol + ChouchouUrls.host
					+ baseUrl);
			addHeaders(get);
			HttpResponse response = client.execute(get);
			return sendResponse(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String post() {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(ChouchouUrls.protocol
					+ ChouchouUrls.host + baseUrl);
			addHeaders(post);
			StringEntity se = new StringEntity(params);
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			post.setEntity(se);
			HttpResponse response = client.execute(post);
			return sendResponse(response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String put() {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPut put = new HttpPut(ChouchouUrls.protocol + ChouchouUrls.host
					+ baseUrl);
			addHeaders(put);
			StringEntity entity;

			entity = new StringEntity(params);

			entity.setContentType("application/json;charset=UTF-8");
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8"));
			put.setEntity(entity);
			HttpResponse response = client.execute(put);
			return sendResponse(response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String sendResponse(HttpResponse response) {
		String resp = null;
		try {
			if (response != null) {
				InputStream is = response.getEntity().getContent();
				resp = Utility.getStringFromInputStream(is);
				// int status = response.getStatusLine().getStatusCode();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resp;
	}
}
