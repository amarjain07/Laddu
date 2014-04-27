package com.goibibo.chouchou;

public class Checklist {

	protected static final String TAG = Checklist.class.getSimpleName();
	ResponseListner listner;
	
	public Checklist(ResponseListner listner) {
		this.listner = listner;
	}

	public String getChecklistFromEmail(String email) {
		ChouchouClient c = new ChouchouClient(
				ChouchouUrls.GET_CHECKLIST_URL_EMAIL + email);
		return c.get();
	}

	public String getChecklistFromId(String id) {
		ChouchouClient c = new ChouchouClient(ChouchouUrls.GET_CHECKLIST_URL_ID
				+ id);
		return c.get();
	}

	public String postChecklist(String params) {
		ChouchouClient c = new ChouchouClient(ChouchouUrls.POST_CHECKLIST_URL,
				params);
		return c.post();
	}

	public String putChecklist(String params, String identifier) {
		ChouchouClient c = new ChouchouClient(ChouchouUrls.PUT_CHECKLIST_URL
				+ identifier, params);
		return c.put();
	}
}
