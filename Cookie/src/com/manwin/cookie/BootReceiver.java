package com.manwin.cookie;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
	private static final String TAG = "BootReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {

		PendingIntent operation = PendingIntent.getService(context, 0,
				new Intent(context, RefreshService.class),
				PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		am.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(),
				AlarmManager.INTERVAL_DAY,
				//30000, 
				operation);
		
		Log.d(TAG, "onReceived");
	}

}
