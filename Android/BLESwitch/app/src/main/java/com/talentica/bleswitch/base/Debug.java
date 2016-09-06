package com.talentica.bleswitch.base;

import android.util.Log;

import com.talentica.bleswitch.BuildConfig;

/**
 * Created by NavalB on 23-05-2016.
 */
public abstract class Debug {
	public static void d(String tag, String message) {
		if (BuildConfig.DEBUG) {
			Log.d(tag, message);
		}
	}

	public static void i(String tag, String message) {
		if (BuildConfig.DEBUG) {
			Log.i(tag, message);
		}
	}

	public static void e(String tag, String message) {
		if (BuildConfig.DEBUG) {
			Log.e(tag, message);
		}
	}
}
