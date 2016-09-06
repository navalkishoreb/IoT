package com.talentica.blescanlib.base;

import android.util.Log;

/**
 * Created by NavalB on 23-05-2016.
 */
public abstract class Debug {
  public static String SCANNING_TAG ="Scanning";
  public static String SERVICE_TAG ="Service";
	public static void d(String tag, String message) {
			Log.d(tag, message);
	}

	public static void i(String tag, String message) {
			Log.i(tag, message);
	}

	public static void e(String tag, String message) {
			Log.e(tag, message);
	}
}
