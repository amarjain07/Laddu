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

import android.os.AsyncTask;
import android.util.Log;

public class LadduHttpClient extends AsyncTask<Void, Void, String> {
	private static String TAG = LadduHttpClient.class.getSimpleName();
	private String baseUrl;
	private String params;
	private RequestMethod requestMethod;
	private ProcessResult handler;

	public enum RequestMethod {
		GET, POST, PUT;
	}

	public LadduHttpClient(String url, RequestMethod rm) {
		this.baseUrl = url;
		this.requestMethod = rm;
	}

	public LadduHttpClient(String url, String params, RequestMethod rm) {
		this.baseUrl = url;
		this.params = params;
		this.requestMethod = rm;
	}
	
	public void setProcessResultHandler(ProcessResult handle){
		this.handler = handle;
	}

	@Override
	protected String doInBackground(Void... arg0) {
		if (requestMethod == RequestMethod.GET) {
			return getHttp();
		} else if (requestMethod == RequestMethod.POST) {
			return postHttp();
		} else if (requestMethod == RequestMethod.PUT) {
			return putHttp();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		handler.onSuccess(result);
	}

	private void addHeaders(HttpRequestBase reqType) {
		reqType.setHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	}

	public String getHttp() {
		try {
			
			Log.d("Laddu", "URL:"+ ChouchouUrls.protocol + ChouchouUrls.host + baseUrl);
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

	public String postHttp() {
		try {
			HttpClient client = new DefaultHttpClient();
			Log.d("Laddu", "URL:"+ ChouchouUrls.protocol + ChouchouUrls.host + baseUrl);
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

	public String putHttp() {
		try {
			HttpClient client = new DefaultHttpClient();
			Log.d("Laddu", "URL:"+ ChouchouUrls.protocol + ChouchouUrls.host + baseUrl);
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
				Log.d("Laddu", "Response:" + resp);
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
