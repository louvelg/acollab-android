package com.akelio.android.acollab.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.akelio.android.acollab.contract.UserContract;

public class DbHelper extends SQLiteOpenHelper {

	static final String TAG = "dbHelper";

	public DbHelper(Context context) {
		super(context, UserContract.DB_NAME, null, UserContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "dbHelper onCreate");
		String sql = String
				.format("create table %s (%s int primary key, %s int, %s text, %s text, %s text, %s text, %s text)",
						UserContract.TABLE, UserContract.Column.ID,
						UserContract.Column.TENANT_ID,
						UserContract.Column.FIRST_NAME,
						UserContract.Column.LAST_NAME,
						UserContract.Column.PHONE1, UserContract.Column.PHONE2,
						UserContract.Column.COMPANY);

		Log.d(TAG, "create user table with SQL: " + sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "dbHelper onUpdate");
		db.execSQL("drop table if exists " + UserContract.TABLE);
		onCreate(db);
	}

}
