package com.yhjia.me.util;

import android.util.Log;

import com.yhjia.me.app.IApplication;


public class Logger {
	public static final String TAG = "Logger";

	public static void d(String tag, String msg) {
		if (IApplication.debug) {
			Log.d(tag, msg);
		}
	}
	public static void d(String msg) {
		if (IApplication.debug) {
			Log.d(TAG,msg);
		}
	}
}
