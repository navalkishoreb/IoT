package com.talentica.blescanlib.scanning.service;

import android.os.Handler;
import android.os.Messenger;

import com.talentica.blescanlib.scanning.blemodels.BLEScanResult;

/**
 * Created by navalb on 17-06-2016.
 */

 interface NotifyScanningView {
	void notify(ScanningAction action);

	void notify(BLEScanResult result);

	void setReplyTo(Messenger messenger);

	Handler getHandler();
}
