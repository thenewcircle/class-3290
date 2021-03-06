package com.manwin.cookie;

import android.net.Uri;
import android.provider.BaseColumns;

public class SceneContract {
	
	public static final String DB_NAME = "scene.db";
	public static final int DB_VERSION = 5;
	public static final String TABLE = "scene";
	
	public static final String AUTHORITIES = "com.manwin.cookie.provider.scene";
	public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITIES+"/"+TABLE);
	public static final int SCENE_DIR = 1;
	public static final int SCENE_ITEM = 2;
	public static final String SORT_BY = Columns.DATE + " DESC";
	
	static final class Columns {
		public static final String ID = BaseColumns._ID;
		public static final String TITLE = "title";
		public static final String SITE = "site";
		public static final String DATE = "date";
	}
}
