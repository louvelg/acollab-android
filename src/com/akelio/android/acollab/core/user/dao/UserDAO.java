package com.akelio.android.acollab.core.user.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.akelio.android.acollab.db.DbHelper;
import com.akelio.android.acollab.entity.User;

public class UserDAO {

	private DbHelper		dbHelper;
	private SQLiteDatabase	db;

	public UserDAO(Context context) {
		dbHelper = new DbHelper(context);
	}

	public void createUser(User u) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.clear();
		values.put(UserContract.Column.ID, u.getUserId());
		values.put(UserContract.Column.FIRST_NAME, u.getFirstName());
		values.put(UserContract.Column.LAST_NAME, u.getLastName());
		values.put(UserContract.Column.PHONE1, u.getPhone1());
		values.put(UserContract.Column.PHONE2, u.getPhone2());
		values.put(UserContract.Column.TENANT_ID, u.getTenantId());
		values.put(UserContract.Column.COMPANY, u.getCompany());
		db.insertWithOnConflict(UserContract.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
	}

	public User getUser(String userId) {
		User user = new User();
		db = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(UserContract.TABLE);
		Cursor cursor = qb.query(db, null, "_id=" + userId, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			user.setUserId(cursor.getString(cursor.getColumnIndex(UserContract.Column.ID)));
			user.setFirstName(cursor.getString(cursor.getColumnIndex(UserContract.Column.FIRST_NAME)));
			user.setLastName(cursor.getString(cursor.getColumnIndex(UserContract.Column.LAST_NAME)));
			user.setTenantId(cursor.getString(cursor.getColumnIndex(UserContract.Column.TENANT_ID)));
			user.setCompany(cursor.getString(cursor.getColumnIndex(UserContract.Column.COMPANY)));
			user.setPhone1(cursor.getString(cursor.getColumnIndex(UserContract.Column.PHONE1)));
			user.setPhone2(cursor.getString(cursor.getColumnIndex(UserContract.Column.PHONE2)));
		}
		return user;
	}

	public List<User> getUsers() {
		db = dbHelper.getReadableDatabase();
		List<User> users = new ArrayList<User>();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(UserContract.TABLE);
		Cursor cursor = qb.query(db, null, null, null, null, null, UserContract.DEFAULT_SORT);
		if (cursor != null) cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User user = new User();
			user.setUserId(cursor.getString(cursor.getColumnIndex(UserContract.Column.ID)));
			user.setFirstName(cursor.getString(cursor.getColumnIndex(UserContract.Column.FIRST_NAME)));
			user.setLastName(cursor.getString(cursor.getColumnIndex(UserContract.Column.LAST_NAME)));
			user.setTenantId(cursor.getString(cursor.getColumnIndex(UserContract.Column.TENANT_ID)));
			user.setCompany(cursor.getString(cursor.getColumnIndex(UserContract.Column.COMPANY)));
			user.setPhone1(cursor.getString(cursor.getColumnIndex(UserContract.Column.PHONE1)));
			user.setPhone2(cursor.getString(cursor.getColumnIndex(UserContract.Column.PHONE2)));
			users.add(user);
			cursor.moveToNext();
		}
		return users;
	}

	public void deleteUser(int userId) {
		db = dbHelper.getWritableDatabase();
		db.delete(UserContract.TABLE, "_id = " + userId, null);
	}

	public void close() {
		db.close();
	}

}
