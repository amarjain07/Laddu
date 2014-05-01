package com.laddu;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver {

	public boolean isPost = true;
	public String identity;
	public String data;

	@Override
	public void onReceive(final Context context, final Intent intent) {
		boolean status = NetworkUtil.getConnectivityStatusBool(context);
		Log.d("Laddu", "On Receive");
		if (status) {
			LadduDbHelper db = new LadduDbHelper(context);
			Cursor cr = db.getAllChecklist();
			cr.moveToPosition(-1);
			while (cr.moveToNext()) {
				identity = cr.getString(1);
				data = cr.getString(2);
				Log.d("Laddu", identity + "::" + data);
				data = removeDataFromJsonArray();
				LadduHttpClient client = new LadduHttpClient(
						LadduUrls.GET_CHECKLIST_URL_EMAIL + identity,
						RequestMethod.GET);
				client.setProcessResultHandler(new ProcessResult() {

					@Override
					public void onSuccess(String result) {
						try {
							JSONArray jsonArr = new JSONArray(result);
							if (jsonArr.length() > 0) {
								isPost = false;
							} else {
								isPost = true;
							}
						} catch (JSONException e) {
							isPost = true;
							e.printStackTrace();
						}
						if (isPost) {
							LadduHttpClient client = new LadduHttpClient(
									LadduUrls.POST_CHECKLIST_URL, data,
									RequestMethod.POST);
							client.execute();
						} else {
							LadduHttpClient client = new LadduHttpClient(
									LadduUrls.PUT_CHECKLIST_URL + "id_email",
									data, RequestMethod.PUT);
							client.execute();
						}
					}
				});
				client.execute();
			}
		}
	}

	public String removeDataFromJsonArray() {
		String newData = null;
		try {
			JSONArray ja = new JSONArray(data);
			newData = ja.getJSONObject(0).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return newData;
	}
}
