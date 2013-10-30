package com.manwin.cookie;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

public class SceneListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	private static final String[] FROM = { SceneContract.Columns.TITLE,
			SceneContract.Columns.SITE, SceneContract.Columns.DATE };
	private static final int[] TO = { R.id.title, R.id.site, R.id.date };
	private static final int LOADER_ID = 42;
	private SimpleCursorAdapter adapter;

	private static final ViewBinder VIEW_BINDER = new ViewBinder() {

		@Override
		public boolean setViewValue(View view, Cursor cursor, int index) {
			if (view.getId() != R.id.date)
				return false;
			
			// custom bind
			long timestamp = cursor.getLong(index) * 1000;
			CharSequence relTime = DateUtils.getRelativeTimeSpanString(timestamp);
			((TextView)view).setText(relTime);
			
			return true;
		}

	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setEmptyText("Loading...");

		adapter = new SimpleCursorAdapter(getActivity(), R.layout.row, null,
				FROM, TO, -1);
		adapter.setViewBinder(VIEW_BINDER);

		setListAdapter(adapter);

		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle arg1) {
		if (LOADER_ID != id)
			return null;

		return new CursorLoader(getActivity(), SceneContract.CONTENT_URI, null,
				null, null, SceneContract.SORT_BY);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}
}
