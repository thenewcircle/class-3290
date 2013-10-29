package com.manwin.common;

import com.manwin.common.IManwinListener;

interface IManwinService {
	boolean update(String packageName, in IManwinListener listener);
}