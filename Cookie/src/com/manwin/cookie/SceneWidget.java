package com.manwin.cookie;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.format.DateUtils;
import android.widget.RemoteViews;

public class SceneWidget extends AppWidgetProvider {

	@Override
	public void onReceive(Context context, Intent intent) {
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		int[] appWidgetIds = appWidgetManager
				.getAppWidgetIds(new ComponentName(context, SceneWidget.class));
		onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// super.onUpdate(context, appWidgetManager, appWidgetIds);

		// Getting the data
		Cursor cursor = context.getContentResolver().query(
				SceneContract.CONTENT_URI, null, null, null,
				SceneContract.SORT_BY);

		if (!cursor.moveToFirst())
			return;

		String title = cursor.getString(cursor
				.getColumnIndex(SceneContract.Columns.TITLE));
		String desc = cursor.getString(cursor
				.getColumnIndex(SceneContract.Columns.SITE));
		long date = cursor.getLong(cursor
				.getColumnIndex(SceneContract.Columns.DATE));
		CharSequence relTime = DateUtils.getRelativeTimeSpanString(date * 1000);

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 1,
				new Intent(context, MainActivity.class),
				PendingIntent.FLAG_UPDATE_CURRENT);

		for (int appWidgetId : appWidgetIds) {
			// Get the views
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.row);
			views.setTextViewText(R.id.title, title);
			views.setTextViewText(R.id.site, desc);
			views.setTextViewText(R.id.date, relTime);
			views.setOnClickPendingIntent(R.id.row, pendingIntent);

			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}
