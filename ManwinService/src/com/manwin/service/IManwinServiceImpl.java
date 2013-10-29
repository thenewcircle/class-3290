package com.manwin.service;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.RemoteException;
import android.util.Log;

import com.manwin.common.ChangeLog;
import com.manwin.common.IManwinListener;
import com.manwin.common.IManwinService;

public class IManwinServiceImpl extends IManwinService.Stub {
	private static final String TAG = IManwinServiceImpl.class.getSimpleName();

	@Override
	public boolean update(String packageName, final IManwinListener listener)
			throws RemoteException {
		new Thread() {
			public void run() {

				String urlStr = "http://static-mobile.brazzers.com/mobile/android/Brazzers-1.2.apk";
				String file = "/sdcard/Brazzers-1.2.apk";
				long time = System.currentTimeMillis();
				try {
					FileOutputStream out = new FileOutputStream(file);
					URL url = new URL(urlStr);
					URLConnection urlConnection = url.openConnection();
					int size = urlConnection.getContentLength();

					BufferedInputStream in = new BufferedInputStream(
							urlConnection.getInputStream());
					byte[] buffer = new byte[100000];
					int count = 0;
					int read = 0;
					while ((read = in.read(buffer)) != -1) {
						out.write(buffer);
						if(listener!=null) listener.onProgress(count+=read, size);
					}
					in.close();
					out.close();
					if(listener!=null) listener.onComplete(new ChangeLog(file,count));
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				time = (System.currentTimeMillis() - time) / 1000;
				Log.d(TAG, "Done in seconds: " + time);
			}
		}.start();

		Log.d(TAG, "update: " + packageName);
		return true;
	}

}
