package com.akelio.android.acollab.contract;

import android.provider.BaseColumns;

public class ActivityContract {
	public static final String DB_NAME = "user.db";
	public static final int DB_VERSION = 2;
	public static final String TABLE = "activity";
	public static final String DEFAULT_SORT = Column.DATE_CREATED + " DESC";

	public class Column {
		public static final String ID = BaseColumns._ID;
		public static final String TENANT_ID = "tenant_id";
		public static final String USER_ID = "user_id";
		public static final String SPACE_ID = "space_id";
		public static final String SPACE_NAME = "space_name";
		public static final String TITLE = "title";
		public static final String USER_NAME = "user_name";
		public static final String DATE_CREATED = "date_created";
		public static final String MODULE_ID = "module_id";
		public static final String MODULE_TYPE = "module_type";
		public static final boolean DELETED = false;
	}
}
