package com.manwin.cookie;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {
	private Fragment sceneListFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			sceneListFragment = new SceneListFragment();
			getSupportFragmentManager()
					.beginTransaction()
					.add(android.R.id.content, sceneListFragment,
							"SceneListFragment").commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			startService(new Intent(this, RefreshService.class));
			return true;
		case R.id.action_purge:
			getContentResolver().delete(SceneContract.CONTENT_URI, null, null);
			return true;
			
		case R.id.action_cancel_updates:
			PendingIntent operation = PendingIntent.getService(this, 0,
					new Intent(this, RefreshService.class),
					PendingIntent.FLAG_UPDATE_CURRENT);
			
			((AlarmManager)getSystemService(ALARM_SERVICE)).cancel(operation);
			return true;
		default:
			return false;
		}
	}
}
