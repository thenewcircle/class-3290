package com.manwin.service;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.manwin.common.ChangeLog;
import com.manwin.common.IManwinListener;
import com.manwin.common.IManwinService;

public class IManwinServiceImpl extends IManwinService.Stub {
	private static final String TAG = IManwinServiceImpl.class.getSimpleName();
	private Context context;

	public IManwinServiceImpl(Context context) {
		this.context = context;
	}

	@Override
	public boolean update(final String packageName,
			final IManwinListener listener) throws RemoteException {

		if (!isAuthorized()) {
			throw new SecurityException("Can't use this API!");
		}

		if (TextUtils.equals(packageName, "Brazzers-1.2.apk")) {
			if (context
					.checkCallingOrSelfPermission("com.manwin.service.permission.ACCESS_BRAZZERS") != PackageManager.PERMISSION_GRANTED) {
				throw new SecurityException("To access Brazzers, you need: "
						+ "com.manwin.service.permission.ACCESS_BRAZZERS");
			}
		}

		new Thread() {
			public void run() {

				String urlStr = "http://static-mobile.brazzers.com/mobile/android/"
						+ packageName;
				String file = "/sdcard/" + packageName;
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
						if (listener != null)
							listener.onProgress(count += read, size);
					}
					in.close();
					out.close();
					if (listener != null)
						listener.onComplete(new ChangeLog(file, count));
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

	private boolean isAuthorized() {
		int uid = getCallingUid();
		PackageManager pm = context.getPackageManager();
		String[] packages = pm.getPackagesForUid(uid);
		PackageInfo info;
		for (String packageName : packages) {
			try {
				info = pm.getPackageInfo(packageName,
						PackageManager.GET_SIGNATURES);
				for (Signature signature : info.signatures) {
					Log.d(TAG, String.format("packageName: %s sig: %s",
							packageName, signature.toCharsString()));
				}
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
}
