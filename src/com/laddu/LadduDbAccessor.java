package com.laddu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class LadduDbAccessor extends AsyncTask<Void, Void, String> {

	private ProcessResult handler;
	private Context context;
	private String identity;
	private String params;
	private RequestMethod requestMethod;

	public LadduDbAccessor(Context context, String identity, RequestMethod rm) {
		this.context = context;
		this.identity = identity;
		this.requestMethod = rm;
	}

	public LadduDbAccessor(Context context, String identity, String params,
			RequestMethod rm) {
		this.context = context;
		this.identity = identity;
		// this.params = params;
		addDataToJsonArray(params);
		this.requestMethod = rm;
	}

	public void addDataToJsonArray(String params) {
		try {
			JSONArray ja = new JSONArray();
			JSONObject jo = new JSONObject(params);
			ja.put(jo);
			this.params = ja.toString();
			Log.d("Laddu", "Params: "+this.params);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String doInBackground(Void... pms) {
		LadduDbHelper db = new LadduDbHelper(context);
		if (requestMethod == RequestMethod.GET) {
			Log.d("Laddu", "Get");
			return db.getAllChecklist(identity);
		} else if (requestMethod == RequestMethod.POST) {
			Log.d("Laddu", "Post ");
			db.insertChecklist(identity, params);
			return db.getAllChecklist(identity);
		} else if (requestMethod == RequestMethod.PUT) {
			Log.d("Laddu", "Put");
			db.updateAllChecklist(identity, params);
			return db.getAllChecklist(identity);
		}
		return null;
	}

	public void setProcessResultHandler(ProcessResult handle) {
		this.handler = handle;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (handler != null) {
			handler.onSuccess(result);
		}
	}
}