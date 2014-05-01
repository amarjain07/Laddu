package com.laddu;

import android.content.Context;
import android.util.Log;

public class Checklist {

	protected static final String TAG = Checklist.class.getSimpleName();
	ResponseListner listner;
	Context context;

	public Checklist(Context ctx, ResponseListner listner) {
		this.listner = listner;
		this.context = ctx;
	}

	public void getChecklistFromEmail(final String email) {
		Log.d("Laddu", "GetChecklistFromEmail");
		LadduDbAccessor lda = new LadduDbAccessor(context, email,
				RequestMethod.GET);
		lda.setProcessResultHandler(new ProcessResult() {

			@Override
			public void onSuccess(String result) {
				Log.d("Laddu", ""+result);
				if (result == null || ("").equals(result)) {
					if (Util.isInternetConnected(context)) {
						Log.d("Laddu", "Get email With Internet");
						LadduHttpClient c = new LadduHttpClient(
								LadduUrls.GET_CHECKLIST_URL_EMAIL + email,
								RequestMethod.GET);
						c.setProcessResultHandler(new ProcessResult() {

							@Override
							public void onSuccess(String result) {
								Log.d("Laddu", result);
								listner.onResponse(result);
							}
						});
						c.execute();
					} else {
						Log.d("Laddu", "Get email Without Internet");
						listner.onResponse(result);
					}
				} else {
					listner.onResponse(result);
				}
			}
		});
		lda.execute();
	}

	public void getChecklistFromId(final String id) {
		Log.d("Laddu", "GetChecklistFromId");
		LadduDbAccessor lda = new LadduDbAccessor(context, id,
				RequestMethod.GET);
		lda.setProcessResultHandler(new ProcessResult() {

			@Override
			public void onSuccess(String result) {
				if (result == null || ("").equals(result)) {
					if (Util.isInternetConnected(context)) {
						Log.d("Laddu", "Get id With Internet");
						LadduHttpClient c = new LadduHttpClient(
								LadduUrls.GET_CHECKLIST_URL_ID + id,
								RequestMethod.GET);
						c.setProcessResultHandler(new ProcessResult() {

							@Override
							public void onSuccess(String result) {
								Log.d("Laddu", result);
								listner.onResponse(result);
							}
						});
						c.execute();
					} else {
						Log.d("Laddu", "Get id Without Internet");
						listner.onResponse(result);
					}

				} else {
					listner.onResponse(result);
				}
			}
		});
		lda.execute();
	}

	public void postChecklist(String params, String id) {
		if (Util.isInternetConnected(context)) {
			Log.d("Laddu", "Post With Internet");
			LadduHttpClient c = new LadduHttpClient(
					LadduUrls.POST_CHECKLIST_URL, params, RequestMethod.POST);
			c.setProcessResultHandler(new ProcessResult() {

				@Override
				public void onSuccess(String result) {
					listner.onResponse(result);
				}
			});
			c.execute();
			LadduDbAccessor lda = new LadduDbAccessor(context, id, params,
					RequestMethod.POST);
			lda.execute();
		} else {
			Log.d("Laddu", "Post Without Internet");
			LadduDbAccessor lda = new LadduDbAccessor(context, id, params,
					RequestMethod.POST);
			lda.setProcessResultHandler(new ProcessResult() {

				@Override
				public void onSuccess(String result) {
					listner.onResponse(result);
				}
			});
			lda.execute();
		}
	}

	public void putChecklist(String params, String id, String identifier) {
		if (Util.isInternetConnected(context)) {
			Log.d("Laddu", "Put With Internet");
			LadduHttpClient c = new LadduHttpClient(
					LadduUrls.PUT_CHECKLIST_URL + identifier, params,
					RequestMethod.PUT);
			c.setProcessResultHandler(new ProcessResult() {

				@Override
				public void onSuccess(String result) {
					listner.onResponse(result);
				}
			});
			c.execute();
			LadduDbAccessor lda = new LadduDbAccessor(context, id, params,
					RequestMethod.PUT);
			lda.execute();
		} else {
			Log.d("Laddu", "Put Without Internet");
			LadduDbAccessor lda = new LadduDbAccessor(context, id, params,
					RequestMethod.PUT);
			lda.setProcessResultHandler(new ProcessResult() {

				@Override
				public void onSuccess(String result) {
					listner.onResponse(result);
				}
			});
			lda.execute();
		}
	}
}
