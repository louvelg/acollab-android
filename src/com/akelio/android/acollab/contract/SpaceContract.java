package com.akelio.android.acollab.contract;

import android.provider.BaseColumns;

public class SpaceContract {
	public static final String DB_NAME = "user.db";
	public static final int DB_VERSION = 2;
	public static final String TABLE = "space";
	public static final String DEFAULT_SORT = Column.NAME + " ASC";

	public class Column {
		public static final String ID = BaseColumns._ID;
		public static final String TENANT_ID = "tenant_id";
		public static final String NAME = "name";
		public static final String APPLICATIONS = "applications";
	}
}
