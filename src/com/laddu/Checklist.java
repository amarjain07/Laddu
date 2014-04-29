package com.laddu;

import android.util.Log;

import com.laddu.LadduHttpClient.RequestMethod;

public class Checklist {

	protected static final String TAG = Checklist.class.getSimpleName();
	ResponseListner listner;

	public Checklist(ResponseListner listner) {
		Log.d("Laddu", "Checklist");
		Log.v("Laddu", "Checklist");
		this.listner = listner;
	}

	public void getChecklistFromEmail(String email) {
		LadduHttpClient c = new LadduHttpClient(
				ChouchouUrls.GET_CHECKLIST_URL_EMAIL + email, RequestMethod.GET);
		c.setProcessResultHandler(new ProcessResult() {

			@Override
			public void onSuccess(String result) {
				Log.d("Laddu", result);
				listner.onResponse(result);
			}
		});
		c.execute();
	}

	public void getChecklistFromId(String id) {
		LadduHttpClient c = new LadduHttpClient(
				ChouchouUrls.GET_CHECKLIST_URL_ID + id, RequestMethod.GET);
		c.setProcessResultHandler(new ProcessResult() {

			@Override
			public void onSuccess(String result) {
				Log.d("Laddu", result);
				listner.onResponse(result);
			}
		});
		c.execute();
	}

	public void postChecklist(String params) {
		LadduHttpClient c = new LadduHttpClient(
				ChouchouUrls.POST_CHECKLIST_URL, params, RequestMethod.POST);
		c.setProcessResultHandler(new ProcessResult() {

			@Override
			public void onSuccess(String result) {
				listner.onResponse(result);
			}
		});
		c.execute();
	}

	public void putChecklist(String params, String identifier) {
		LadduHttpClient c = new LadduHttpClient(ChouchouUrls.PUT_CHECKLIST_URL
				+ identifier, params, RequestMethod.PUT);
		c.setProcessResultHandler(new ProcessResult() {

			@Override
			public void onSuccess(String result) {
				listner.onResponse(result);
			}
		});
		c.execute();
	}
}
