package com.manwin.cookie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	private static final String TAG = "DbHelper";

	public DbHelper(Context context) {
		super(context, SceneContract.DB_NAME, null, SceneContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String
				.format("create table %s (%s integer primary key, %s text, %s text, %s integer)",
						SceneContract.TABLE, SceneContract.Columns.ID,
						SceneContract.Columns.TITLE,
						SceneContract.Columns.SITE, SceneContract.Columns.DATE);
		Log.d(TAG, "onCreate: " + sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + SceneContract.TABLE);
		this.onCreate(db);
	}

}
