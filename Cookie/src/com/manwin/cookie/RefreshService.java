package com.manwin.cookie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

public class RefreshService extends IntentService {
	private static final String TAG = "RefreshService";

	public RefreshService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String api = Private.key;
		
		try {
			URL url = new URL(api);
			URLConnection connection = url.openConnection();
			connection
					.addRequestProperty("user-agent",
							"Dalvik/1.6.0 (Linux; U; Android 4.2.2; GT-I9505 Build/JDQ39)");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			char[] buffer = new char[1024];
			StringBuffer strBuffer = new StringBuffer();
			while (in.read(buffer) != -1) {
				strBuffer.append(buffer);
			}
			Log.d(TAG, "StrBuffer: " + strBuffer);
			in.close();

			JSONObject jsonFeed = new JSONObject(strBuffer.toString());

			JSONArray scenes = jsonFeed.getJSONArray("scenes");
			JSONObject scene;
			int id;
			String title;
			ContentValues values = new ContentValues();
			for (int i = 0; i < scenes.length(); i++) {
				scene = scenes.getJSONObject(i);
				id = scene.getInt("scene_id");
				title = scene.getString("scene_title");
				Log.d(TAG, String.format("%d: %s", id, title));
				
				values.clear();
				values.put(SceneContract.Columns.ID, id);
				values.put(SceneContract.Columns.TITLE, title);
				getContentResolver().insert(SceneContract.CONTENT_URI, values);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
