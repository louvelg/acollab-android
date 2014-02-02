package com.akelio.android.acollab.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.akelio.android.acollab.contract.ActivityContract;
import com.akelio.android.acollab.contract.MainContract;
import com.akelio.android.acollab.contract.SpaceContract;
import com.akelio.android.acollab.contract.UserContract;

public class DbHelper extends SQLiteOpenHelper {

	static final String	TAG	= "dbHelper";

	public DbHelper(Context context) {
		super(context, MainContract.DB_NAME, null, MainContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "dbHelper onCreate");
		String sql = String.format("create table %s (%s int primary key, %s int, %s text, %s text, %s text, %s text, %s text)", UserContract.TABLE, UserContract.Column.ID,
				UserContract.Column.TENANT_ID, UserContract.Column.FIRST_NAME, UserContract.Column.LAST_NAME, UserContract.Column.PHONE1, UserContract.Column.PHONE2, UserContract.Column.COMPANY);

		Log.d(TAG, "create " + UserContract.TABLE + " table with SQL: " + sql);
		db.execSQL(sql);

		sql = String.format("create table %s (%s int primary key, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s int)", ActivityContract.TABLE,
				ActivityContract.Column.ID, ActivityContract.Column.TENANT_ID, ActivityContract.Column.USER_ID, ActivityContract.Column.SPACE_ID, ActivityContract.Column.SPACE_NAME,
				ActivityContract.Column.TITLE, ActivityContract.Column.USER_NAME, ActivityContract.Column.DATE_CREATED, ActivityContract.Column.MODULE_ID, ActivityContract.Column.MODULE_TYPE,
				ActivityContract.Column.DELETED);

		Log.d(TAG, "create " + ActivityContract.TABLE + " table with SQL: " + sql);
		db.execSQL(sql);

		sql = String.format("create table %s (%s int primary key, %s text, %s text, %s text)", SpaceContract.TABLE, SpaceContract.Column.ID, SpaceContract.Column.TENANT_ID,
				SpaceContract.Column.NAME, SpaceContract.Column.APPLICATIONS);

		Log.d(TAG, "create " + SpaceContract.TABLE + " table with SQL: " + sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "dbHelper onUpdate");
		db.execSQL("drop table if exists " + UserContract.TABLE);
		db.execSQL("drop table if exists " + ActivityContract.TABLE);
		db.execSQL("drop table if exists " + SpaceContract.TABLE);
		onCreate(db);
	}

}
