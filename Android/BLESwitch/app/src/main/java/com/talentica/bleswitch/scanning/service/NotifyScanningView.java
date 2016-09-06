package com.talentica.bleswitch.scanning.service;

import android.os.Handler;
import android.os.Messenger;

import com.talentica.bleswitch.scanning.blemodels.BLEScanResult;

/**
 * Created by navalb on 17-06-2016.
 */

public interface NotifyScanningView {
	void notify(ScanningViewAction action);

	void notify(BLEScanResult result);

	void setReplyTo(Messenger messenger);

	Handler getHandler();
}
