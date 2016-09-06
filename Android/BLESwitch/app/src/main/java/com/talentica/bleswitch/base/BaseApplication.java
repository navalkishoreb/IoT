package com.talentica.bleswitch.base;

import android.app.Application;

/**
 * Created by navalb on 17-06-2016.
 */

public class BaseApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
//		BackgroundDeviceScan.startService(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
//		BackgroundDeviceScan.stopService(this);
	}
}
