package com.manwin.cookie;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {
	public static final int NOTIFICATION_ID = 42;
	private static final String TAG = "NotificationReceiver";

	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		int count = intent.getIntExtra("count", -1);
		if (count == -1)
			return;

		PendingIntent operation = PendingIntent.getActivity(context, 0,
				new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

		Notification notification;
		if (Build.VERSION.SDK_INT < 11) {
			notification = new Notification();
			notification.setLatestEventInfo(context, "New Scene!",
					"You've got " + count + " new scenes", operation);
			notification.icon = android.R.drawable.stat_notify_more;
			notification.flags = Notification.FLAG_AUTO_CANCEL;
		} else {
			notification = new Notification.Builder(context)
					.setContentTitle("New Scene!")
					.setContentText("You've got " + count + " new scenes")
					.setSmallIcon(android.R.drawable.stat_notify_more)
					.setContentIntent(operation).setAutoCancel(true)
					.getNotification();
		}
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(NOTIFICATION_ID, notification);

		Log.d(TAG, "onReceived");
	}
}
