package com.manwin.cookie;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

public class SceneProvider extends ContentProvider {
	private static final String TAG = "SceneProvider";
	private DbHelper dbHelper;
	private SQLiteDatabase db;
	private final static UriMatcher uriMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		uriMatcher.addURI(SceneContract.AUTHORITIES, SceneContract.TABLE,
				SceneContract.SCENE_DIR);
		uriMatcher.addURI(SceneContract.AUTHORITIES,
				SceneContract.TABLE + "/#", SceneContract.SCENE_ITEM);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case SceneContract.SCENE_DIR:
			return "vnd.android.cursor.dir/scene";
		case SceneContract.SCENE_ITEM:
			return "vnd.android.cursor.item/scene";	
		default:
			return null;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (uriMatcher.match(uri) != SceneContract.SCENE_DIR) {
			throw new IllegalArgumentException("Unsupported uri: " + uri);
		}

		db = dbHelper.getWritableDatabase();
		long rowId = db.insertWithOnConflict(SceneContract.TABLE, null, values,
				SQLiteDatabase.CONFLICT_REPLACE);
		if(rowId==-1) throw new SQLiteException("Failed to insert for uri: "+uri);
		Uri ret = ContentUris.withAppendedId(uri,
				values.getAsInteger(SceneContract.Columns.ID));
		Log.d(TAG, "inserted: " + ret);
		return ret;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}
}
