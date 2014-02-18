package com.akelio.android.acollab.core.news.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.akelio.android.acollab.db.DbHelper;
import com.akelio.android.acollab.entity.News;

public class NewsDAO {

	private DbHelper		dbHelper;
	private SQLiteDatabase	db;

	public NewsDAO(Context context) {
		dbHelper = new DbHelper(context);
	}

	public void createActivity(News a) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.clear();
		values.put(NewsContract.Column.ID, a.getActivityId());
		values.put(NewsContract.Column.TENANT_ID, a.getTenantId());
		values.put(NewsContract.Column.SPACE_ID, a.getSpaceId());
		values.put(NewsContract.Column.SPACE_NAME, a.getSpaceName());
		values.put(NewsContract.Column.MODULE_ID, a.getModuleId());
		values.put(NewsContract.Column.MODULE_TYPE, a.getModuleType());
		values.put(NewsContract.Column.TITLE, a.getTitle());
		values.put(NewsContract.Column.USER_ID, a.getUserId());
		values.put(NewsContract.Column.USER_NAME, a.getUserName());
		values.put(NewsContract.Column.DATE_CREATED, a.getDateCreated());
		db.insertWithOnConflict(NewsContract.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
	}

	public List<News> getActivitys() {
		db = dbHelper.getReadableDatabase();
		List<News> list = new ArrayList<News>();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(NewsContract.TABLE);
		Cursor cursor = qb.query(db, null, null, null, null, null, NewsContract.DEFAULT_SORT);
		if (cursor != null) cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			News obj = new News();
			obj.setActivityId(cursor.getString(cursor.getColumnIndex(NewsContract.Column.ID)));
			obj.setTenantId(cursor.getString(cursor.getColumnIndex(NewsContract.Column.TENANT_ID)));
			obj.setSpaceId(cursor.getString(cursor.getColumnIndex(NewsContract.Column.SPACE_ID)));
			obj.setSpaceName(cursor.getString(cursor.getColumnIndex(NewsContract.Column.SPACE_NAME)));
			obj.setTitle(cursor.getString(cursor.getColumnIndex(NewsContract.Column.TITLE)));
			obj.setModuleId(cursor.getString(cursor.getColumnIndex(NewsContract.Column.MODULE_ID)));
			obj.setModuleType(cursor.getString(cursor.getColumnIndex(NewsContract.Column.MODULE_TYPE)));
			obj.setUserId(cursor.getString(cursor.getColumnIndex(NewsContract.Column.USER_ID)));
			obj.setUserName(cursor.getString(cursor.getColumnIndex(NewsContract.Column.USER_NAME)));
			obj.setDateCreated(cursor.getString(cursor.getColumnIndex(NewsContract.Column.DATE_CREATED)));
			list.add(obj);
			cursor.moveToNext();
		}
		return list;
	}

	public void deleteActivity(int activityId) {
		db = dbHelper.getWritableDatabase();
		db.delete(NewsContract.TABLE, "_id = " + activityId, null);
	}

	public void deleteAllActivity() {
		db = dbHelper.getWritableDatabase();
		db.execSQL("delete from " + NewsContract.TABLE);
	}
	
	public void close() {
		db.close();
	}

}
