package com.akelio.android.acollab.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.akelio.android.acollab.core.news.dao.NewsContract;
import com.akelio.android.acollab.core.space.dao.SpaceContract;
import com.akelio.android.acollab.core.user.dao.UserContract;

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

		sql = String.format("create table %s (%s int primary key, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s int)", NewsContract.TABLE,
				NewsContract.Column.ID, NewsContract.Column.TENANT_ID, NewsContract.Column.USER_ID, NewsContract.Column.SPACE_ID, NewsContract.Column.SPACE_NAME,
				NewsContract.Column.TITLE, NewsContract.Column.USER_NAME, NewsContract.Column.DATE_CREATED, NewsContract.Column.MODULE_ID, NewsContract.Column.MODULE_TYPE,
				NewsContract.Column.DELETED);

		Log.d(TAG, "create " + NewsContract.TABLE + " table with SQL: " + sql);
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
		db.execSQL("drop table if exists " + NewsContract.TABLE);
		db.execSQL("drop table if exists " + SpaceContract.TABLE);
		onCreate(db);
	}

}
