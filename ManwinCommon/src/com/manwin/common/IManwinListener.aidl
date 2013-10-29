package com.manwin.common;

import com.manwin.common.ChangeLog;

interface IManwinListener {
	void onProgress(int count, int length);
	void onComplete(in ChangeLog changeLog);
	void onFail();
}