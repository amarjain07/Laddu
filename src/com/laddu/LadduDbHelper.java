package com.laddu;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LadduDbHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "laddu";
	private static final String TABLE_CHECKLIST = "checklist";

	private static final String KEY_ID = "_id";
	private static final String KEY_DATE = "date";
	private static final String KEY_ITEM_NAME = "item_name";
	private static final String KEY_ITEM_TYPE = "item_type";
	private static final String KEY_IS_CHECKED = "is_checked";
	private static final String KEY_DETAILS = "details";

	public LadduDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CHECKLIST + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DATE
				+ " DATETIME default CURRENT TIMESTAMP," + KEY_ITEM_NAME
				+ " VARCHAR(30)," + KEY_ITEM_TYPE + " VARCHAR(50),"
				+ KEY_IS_CHECKED + " BOOLEAN default 0," + KEY_DETAILS
				+ " VARCHAR(50) NULL" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKLIST);
		onCreate(db);
	}

	public void insertChecklist(ChecklistItem checklist) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ITEM_NAME, checklist.getItemName());
		values.put(KEY_ITEM_TYPE, checklist.getItemType());
		values.put(KEY_IS_CHECKED, checklist.getIsChecked());
		db.insert(TABLE_CHECKLIST, null, values);
		db.close();
	}

	public ChecklistItem getChecklist(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CHECKLIST, new String[] { KEY_ID,
				KEY_ITEM_NAME, KEY_IS_CHECKED }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		ChecklistItem checklistItem = new ChecklistItem();
		checklistItem.set_id(cursor.getInt(0));
		checklistItem.setItemName(cursor.getString(1));
		checklistItem.setIsChecked(cursor.getInt(2));
		return checklistItem;
	}

	public List<ChecklistItem> getAllChecklist() {
		List<ChecklistItem> checkList = new ArrayList<ChecklistItem>();
		String selectQuery = "SELECT  * FROM " + TABLE_CHECKLIST;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				ChecklistItem ci = new ChecklistItem();
				ci.set_id(Integer.parseInt(cursor.getString(0)));
				ci.setItemName(cursor.getString(2));
				ci.setItemType(cursor.getString(3));
				ci.setIsChecked(cursor.getInt(4));
				checkList.add(ci);
			} while (cursor.moveToNext());
		}
		return checkList;
	}

	public void updateAllChecklist(List<ChecklistItem> checkList) {
		deleteAllChecklist();
		for (ChecklistItem checklistItem : checkList) {
			insertChecklist(checklistItem);
		}
	}

	public void deleteAllChecklist() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CHECKLIST, null, null);
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