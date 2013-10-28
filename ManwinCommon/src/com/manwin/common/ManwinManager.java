package com.manwin.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

public class ManwinManager {
	private static final Intent INTENT = new Intent("com.manwin.common.IManwinService");
	private Context context;
	private static IManwinService service;
	
	public ManwinManager(Context context) {
		this.context = context;
		context.bindService(INTENT, CONN, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.unbindService(CONN);
	}
	
	private static final ServiceConnection CONN = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			service = IManwinService.Stub.asInterface(binder);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			service = null;
		}
	};
	
	public boolean update(String packageName) {
		if(service==null) return false;
		
		try {
			return service.update(packageName);
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}
}
