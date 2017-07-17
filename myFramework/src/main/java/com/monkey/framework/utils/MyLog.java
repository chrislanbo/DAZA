package com.monkey.framework.utils;

import android.util.Log;

/**
 * 调试用的Log
 * 
 */
public class MyLog {

	private MyLog() {
	}

	public static boolean v = true;
	public static boolean d = true;
	public static boolean i = true;
	public static boolean w = true;
	public static boolean e = true;

	public static void v(String msg) {
		if (msg == null) {
			e(null);
		}
		if (v)
			Log.v("===Verbose===", msg);
	}

	public static void d(String msg) {
		if (msg == null) {
			e(null);
		}
		if (d)
			Log.d("===Debug===", msg);
	}

	public static void i(String msg) {
		if (msg == null) {
			e(null);
		}
		if (i)
			Log.i("===Info===", msg);
	}

	public static void w(String msg) {
		if (msg == null) {
			e(null);
		}
		if (w)
			Log.w("===Warn===", msg);
	}

	public static void e(String msg) {
		if (msg == null)
			msg = "Log message can not be null";
		if (e)
			Log.e("===Error===", msg);
	}
}
