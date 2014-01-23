package com.akelio.android.acollab.contract;

import android.provider.BaseColumns;

public class UserContract {
	public static final String DB_NAME = "user.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "user";
	public static final String DEFAULT_SORT = Column.LAST_NAME + " ASC";

	public class Column {
		public static final String ID = BaseColumns._ID;
		public static final String TENANT_ID = "tenant_id";
		public static final String FIRST_NAME = "first_name";
		public static final String LAST_NAME = "last_name";
		public static final String PHONE1 = "phone1";
		public static final String PHONE2 = "phone2";
		public static final String COMPANY = "company";
	}
}
