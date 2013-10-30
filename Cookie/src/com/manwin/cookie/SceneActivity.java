package com.manwin.cookie;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class SceneActivity extends FragmentActivity {
	private static final String TAG = "SceneActivity";
	private SceneFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			fragment = new SceneFragment();
			getSupportFragmentManager().beginTransaction().add(android.R.id.content,
					fragment, "SceneFragment").commit();
		}
		Log.d(TAG, "onCreated: "+this.toString());
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		long id = getIntent().getLongExtra("id", -1);
		if(fragment!=null) fragment.setContent(id);
		
		Log.d(TAG, "onStarted: "+this.toString());
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStopped: "+this.toString());
	}
}
