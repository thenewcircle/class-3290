package com.manwin.service;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;

import android.os.RemoteException;
import android.util.Log;

import com.framework.utils.WebUtils;
import com.manwin.common.IManwinService;

public class IManwinServiceImpl extends IManwinService.Stub {
	private static final String TAG = IManwinServiceImpl.class.getSimpleName();

	@Override
	public boolean update(String packageName) throws RemoteException {
		new Thread() {
			public void run() {
				
				String url = "http://static-mobile.brazzers.com/mobile/android/Brazzers-1.2.apk";
				long time = System.currentTimeMillis();
				try {
					FileOutputStream out = new FileOutputStream("/sdcard/Brazzers-1.2.apk");
					BufferedInputStream in = new BufferedInputStream(WebUtils.doHttpGet(url));
					byte[] buffer = new byte[4096];
					int size=0;
					while( (size=in.read(buffer)) != -1) {
						out.write(buffer);
					}
					in.close();
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				time = (System.currentTimeMillis() - time) / 1000;
				Log.d(TAG, "Done in seconds: "+time);
			}
		}.start();
		
		Log.d(TAG, "update: "+packageName);
		return true;
	}

}
