package com.talentica.blescanlib.scanning.service;

import com.talentica.blescanlib.scanning.blemodels.BTConnectionState;
import com.talentica.blescanlib.scanning.blemodels.BTState;

/**
 * Created by navalb on 17-06-2016.
 */

 interface BTChangeReceiver {
	void discoveryStarted();

	void discoveryFinished();

	void onConnectionStateChanged(BTConnectionState connectionState, BTConnectionState connectionState1);

	void onStateChanged(BTState state, BTState state1);
}
