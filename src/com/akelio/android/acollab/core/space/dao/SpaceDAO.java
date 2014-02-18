package com.akelio.android.acollab.core.space.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.akelio.android.acollab.db.DbHelper;
import com.akelio.android.acollab.entity.Space;

public class SpaceDAO {

	private DbHelper		dbHelper;
	private SQLiteDatabase	db;

	public SpaceDAO(Context context) {
		dbHelper = new DbHelper(context);
	}

	public void createSpace(Space s) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.clear();
		values.put(SpaceContract.Column.ID, s.getSpaceId());
		values.put(SpaceContract.Column.NAME, s.getName());
		values.put(SpaceContract.Column.TENANT_ID, s.getTenantId());
		values.put(SpaceContract.Column.APPLICATIONS, StringUtils.collectionToCommaDelimitedString(s.getApplications()));
		db.insertWithOnConflict(SpaceContract.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
	}

	public Space getSpace(String spaceId) {
		Space space = new Space();
		db = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(SpaceContract.TABLE);
		Cursor cursor = qb.query(db, null, "_id=" + spaceId, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			space.setSpaceId(cursor.getString(cursor.getColumnIndex(SpaceContract.Column.ID)));
			space.setTenantId(cursor.getString(cursor.getColumnIndex(SpaceContract.Column.TENANT_ID)));
			space.setName(cursor.getString(cursor.getColumnIndex(SpaceContract.Column.NAME)));
			space.setApplications(cursor.getString(cursor.getColumnIndex(SpaceContract.Column.APPLICATIONS)));
		}
		return space;
	}

	public List<Space> getSpaces() {
		db = dbHelper.getReadableDatabase();
		List<Space> spaces = new ArrayList<Space>();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(SpaceContract.TABLE);
		Cursor cursor = qb.query(db, null, null, null, null, null, SpaceContract.DEFAULT_SORT);
		if (cursor != null) cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Space space = new Space();
			space.setSpaceId(cursor.getString(cursor.getColumnIndex(SpaceContract.Column.ID)));
			space.setName(cursor.getString(cursor.getColumnIndex(SpaceContract.Column.NAME)));
			space.setTenantId(cursor.getString(cursor.getColumnIndex(SpaceContract.Column.TENANT_ID)));
			space.setApplications(cursor.getString(cursor.getColumnIndex(SpaceContract.Column.APPLICATIONS)));
			spaces.add(space);
			cursor.moveToNext();
		}
		return spaces;
	}

	public void deleteSpace(int spaceId) {
		db = dbHelper.getWritableDatabase();
		db.delete(SpaceContract.TABLE, "_id = " + spaceId, null);
	}

	public void close() {
		db.close();
	}

}
