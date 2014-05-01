package com.laddu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LadduDbHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "laddu.db";
	private static final String TABLE_CHECKLIST = "checklist";

	private static final String KEY_ID = "_id";
	private static final String KEY_IDENTIFIER = "identifier";
	private static final String KEY_DATA = "data";

	public LadduDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CHECKLIST_TABLE = "CREATE TABLE " + TABLE_CHECKLIST + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ KEY_IDENTIFIER + " VARCHAR(50) NULL," + KEY_DATA + " TEXT "
				+ ")";
		db.execSQL(CREATE_CHECKLIST_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKLIST);
		onCreate(db);
	}

	public void insertChecklist(String identity, String data) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_IDENTIFIER, identity);
		values.put(KEY_DATA, data);
		db.insert(TABLE_CHECKLIST, null, values);
		db.close();

	}

	public String getAllChecklist(String identity) {
		SQLiteDatabase db = this.getReadableDatabase();
		String data = null;
		Cursor cursor = db.query(TABLE_CHECKLIST, new String[] { KEY_DATA },
				KEY_IDENTIFIER + "=?", new String[] { identity }, null, null,
				null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			data = cursor.getString(0);
			Log.d("Laddu", data);
			cursor.close();
		}
		return data;
	}

	public Cursor getAllChecklist() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_CHECKLIST, null, null, null, null, null,
				null);
		return cursor;
	}

	public void updateAllChecklist(String identity, String data) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_DATA, data);
		db.update(TABLE_CHECKLIST, values, KEY_IDENTIFIER + "=?",
				new String[] { identity });
		db.close();
		// deleteAllChecklist(identity);
		// insertChecklist(identity, data);
	}

	public void deleteAllChecklist(String identity) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CHECKLIST, KEY_IDENTIFIER + "=?",
				new String[] { identity });
		db.close();
	}

	public int getChecklistCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CHECKLIST;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		return cursor.getCount();
	}

}