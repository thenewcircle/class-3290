package com.manwin.cookie;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

public class SceneListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	private static final String[] FROM = { SceneContract.Columns.TITLE };
	private static final int[] TO = { android.R.id.text1 };
	private static final int LOADER_ID = 42;
	private SimpleCursorAdapter adapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setEmptyText("Loading...");

		adapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_1, null, FROM, TO, -1);

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
