package com.akelio.android.acollab.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.akelio.android.acollab.contract.ActivityContract;
import com.akelio.android.acollab.db.DbHelper;
import com.akelio.android.acollab.entity.Activity;

public class ActivityDAO {

	private DbHelper		dbHelper;
	private SQLiteDatabase	db;

	public ActivityDAO(Context context) {
		dbHelper = new DbHelper(context);
	}

	public void createActivity(Activity a) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.clear();
		values.put(ActivityContract.Column.ID, a.getActivityId());
		values.put(ActivityContract.Column.TENANT_ID, a.getTenantId());
		values.put(ActivityContract.Column.SPACE_ID, a.getSpaceId());
		values.put(ActivityContract.Column.SPACE_NAME, a.getSpaceName());
		values.put(ActivityContract.Column.MODULE_ID, a.getModuleId());
		values.put(ActivityContract.Column.MODULE_TYPE, a.getModuleType());
		values.put(ActivityContract.Column.TITLE, a.getTitle());
		values.put(ActivityContract.Column.USER_ID, a.getUserId());
		values.put(ActivityContract.Column.USER_NAME, a.getUserName());
		values.put(ActivityContract.Column.DATE_CREATED, a.getDateCreated());
		db.insertWithOnConflict(ActivityContract.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
	}

	public List<Activity> getActivitys() {
		db = dbHelper.getReadableDatabase();
		List<Activity> list = new ArrayList<Activity>();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(ActivityContract.TABLE);
		Cursor cursor = qb.query(db, null, null, null, null, null, ActivityContract.DEFAULT_SORT);
		if (cursor != null) cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Activity obj = new Activity();
			obj.setActivityId(cursor.getString(cursor.getColumnIndex(ActivityContract.Column.ID)));
			obj.setTenantId(cursor.getString(cursor.getColumnIndex(ActivityContract.Column.TENANT_ID)));
			obj.setSpaceId(cursor.getString(cursor.getColumnIndex(ActivityContract.Column.SPACE_ID)));
			obj.setSpaceName(cursor.getString(cursor.getColumnIndex(ActivityContract.Column.SPACE_NAME)));
			obj.setTitle(cursor.getString(cursor.getColumnIndex(ActivityContract.Column.TITLE)));
			obj.setModuleId(cursor.getString(cursor.getColumnIndex(ActivityContract.Column.MODULE_ID)));
			obj.setModuleType(cursor.getString(cursor.getColumnIndex(ActivityContract.Column.MODULE_TYPE)));
			obj.setUserId(cursor.getString(cursor.getColumnIndex(ActivityContract.Column.USER_ID)));
			obj.setUserName(cursor.getString(cursor.getColumnIndex(ActivityContract.Column.USER_NAME)));
			obj.setDateCreated(cursor.getString(cursor.getColumnIndex(ActivityContract.Column.DATE_CREATED)));
			list.add(obj);
			cursor.moveToNext();
		}
		return list;
	}

	public void deleteActivity(int activityId) {
		db = dbHelper.getWritableDatabase();
		db.delete(ActivityContract.TABLE, "_id = " + activityId, null);
	}

	public void deleteAllActivity() {
		db = dbHelper.getWritableDatabase();
		db.execSQL("delete from " + ActivityContract.TABLE);
	}
	
	public void close() {
		db.close();
	}

}
