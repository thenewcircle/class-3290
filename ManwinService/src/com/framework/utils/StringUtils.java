package com.framework.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

import com.manwin.service.BuildConfig;

/**
 * Class that will help you manipulate Strings.
 * 
 * @author Matthieu Coisne
 */
public class StringUtils {
	
	private static String TAG = StringUtils.class.getSimpleName();
	
	private StringUtils(){
	}
	
	public static String convertStreamToString(InputStream is) {
		String ret = "";
		if (is != null) {
			try {
				ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
				byte[] buffer = new byte[1024];
				int count;
				do {
					count = is.read(buffer);
					if (count > 0) {
						out.write(buffer, 0, count);
					}
				} while (count >= 0);
				ret = out.toString();
			} catch (IOException e) {
				if (BuildConfig.DEBUG) {
					Log.e(TAG, "convertStreamToString error: " + e.toString());
				}
				ret = "";
			} finally {
				try {
					is.close();
				} catch (IOException ignored) {
				}
			}
		}
		return ret;
	}
}