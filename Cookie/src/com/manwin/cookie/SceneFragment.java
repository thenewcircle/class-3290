package com.manwin.cookie;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SceneFragment extends Fragment {
	private TextView title, desc;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_scene, null, false);
		title = (TextView) view.findViewById(R.id.title);
		desc = (TextView) view.findViewById(R.id.desc);
		return view;
	}

	public void setContent(long id) {
		if (id == -1) {
			title.setText("");
			desc.setText("Loading...");
		}

		Uri uri = ContentUris.withAppendedId(SceneContract.CONTENT_URI, id);
		Cursor cursor = getActivity().getContentResolver().query(uri, null,
				null, null, null);

		if (cursor.moveToFirst()) {
			
			String activityId = "\n\nActivity: "+getActivity().toString();
			
			title.setText(cursor.getString(cursor
					.getColumnIndex(SceneContract.Columns.TITLE)));
			desc.setText(cursor.getString(cursor
					.getColumnIndex(SceneContract.Columns.SITE)) + activityId);
		}
	}
}
