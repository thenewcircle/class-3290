package com.manwin.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ManwinService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return new IManwinServiceImpl(this);
	}

}
