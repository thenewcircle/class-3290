package com.manwin.cookie;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView;

public class SceneListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	private static final String TAG = "SceneListFragment";
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
			CharSequence relTime = DateUtils
					.getRelativeTimeSpanString(timestamp);
			((TextView) view).setText(relTime);

			return true;
		}

	};

	private static int offset = 0;
	private static boolean shouldRefresh = true;
	private final OnScrollListener scrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			int lastVisibleInScreen = firstVisibleItem + visibleItemCount;

			if (shouldRefresh && lastVisibleInScreen >= adapter.getCount()) {
				shouldRefresh = false;
				Log.d(TAG, "should update");
				getActivity().startService(
						new Intent(getActivity(), RefreshService.class)
								.putExtra("offset", offset++));
			}
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
		getListView().setOnScrollListener(scrollListener);

		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		SceneFragment fragment = (SceneFragment) getActivity()
				.getSupportFragmentManager().findFragmentById(
						R.id.fragment_scene);

		if (fragment != null && fragment.isVisible()) {
			fragment.setContent(id);
		} else {
			Intent intent = new Intent(getActivity(), SceneActivity.class);
			intent.putExtra("id", id);
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
					| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			getActivity().startActivity(intent);
		}
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
		shouldRefresh = true;
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
		shouldRefresh = true;
	}

}
