package com.laddu;

public class Checklist {

	protected static final String TAG = Checklist.class.getSimpleName();
	ResponseListner listner;
	
	public Checklist(ResponseListner listner) {
		this.listner = listner;
	}

	public String getChecklistFromEmail(String email) {
		LadduHttpClient c = new LadduHttpClient(
				ChouchouUrls.GET_CHECKLIST_URL_EMAIL + email);
		return c.get();
	}

	public String getChecklistFromId(String id) {
		LadduHttpClient c = new LadduHttpClient(ChouchouUrls.GET_CHECKLIST_URL_ID
				+ id);
		return c.get();
	}

	public String postChecklist(String params) {
		LadduHttpClient c = new LadduHttpClient(ChouchouUrls.POST_CHECKLIST_URL,
				params);
		return c.post();
	}

	public String putChecklist(String params, String identifier) {
		LadduHttpClient c = new LadduHttpClient(ChouchouUrls.PUT_CHECKLIST_URL
				+ identifier, params);
		return c.put();
	}
}
