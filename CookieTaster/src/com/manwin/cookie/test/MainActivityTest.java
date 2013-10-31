package com.manwin.cookie.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.manwin.cookie.MainActivity;
import com.manwin.cookie.SceneListFragment;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity activity;
	private SceneListFragment fragment;
	private ListView list;

	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		activity = (MainActivity) getActivity();
		fragment = (SceneListFragment) activity.getSupportFragmentManager()
				.findFragmentById(com.manwin.cookie.R.id.fragment_scene_list);
		list = fragment.getListView();
	}

	public void testUIExists() {
		assertNotNull(activity);
		assertNotNull(fragment);
		assertNotNull(list);
	}

	public void testUIVisible() {
		View rootView = activity.findViewById(android.R.id.content);

		ViewAsserts.assertOnScreen(rootView, fragment.getView());
		ViewAsserts.assertOnScreen(fragment.getView(), list);
	}
	
	public void testListHasData() {
		int count = list.getCount();
		assertTrue(count>0);
	}

	public void testTrue() {
		assertTrue(true);
	}

	public void testScene() {
		View row = list.getChildAt(3);
		TouchUtils.clickView(this, row);
		sendKeys(KeyEvent.KEYCODE_BACK);
		ViewAsserts.assertOnScreen(fragment.getView(), list);
	}
	
	@UiThreadTest
	public void testListEmpty() {
		activity.setTitle("Cookie Monster!");
		list.setAdapter(null);
		assertTrue( list.getCount() == 0);
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
