package com.laddu;

public class LadduUrls {

	public final static String host = "10.70.15.75:5000";
	public final static String protocol = "http://";

	public final static String CHECKLIST_URL = "/checklist/";
	public final static String GET_CHECKLIST_URL_EMAIL = CHECKLIST_URL
			+ "?id_email=";
	public final static String GET_CHECKLIST_URL_ID = CHECKLIST_URL
			+ "?id_and=";
	public final static String POST_CHECKLIST_URL = CHECKLIST_URL;
	public final static String PUT_CHECKLIST_URL = CHECKLIST_URL
			+ "?identifier=";
}