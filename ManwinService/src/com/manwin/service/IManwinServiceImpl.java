package com.manwin.service;

import android.os.RemoteException;
import android.util.Log;

import com.manwin.common.IManwinService;

public class IManwinServiceImpl extends IManwinService.Stub {
	private static final String TAG = IManwinServiceImpl.class.getSimpleName();

	@Override
	public boolean update(String packageName) throws RemoteException {
		Log.d(TAG, "update: "+packageName);
		return true;
	}

}
