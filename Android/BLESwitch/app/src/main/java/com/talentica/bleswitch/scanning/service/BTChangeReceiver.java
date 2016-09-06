package com.talentica.bleswitch.scanning.service;

import com.talentica.bleswitch.scanning.blemodels.BTConnectionState;
import com.talentica.bleswitch.scanning.blemodels.BTState;

/**
 * Created by navalb on 17-06-2016.
 */

public interface BTChangeReceiver {
	void discoveryStarted();

	void discoveryFinished();

	void onConnectionStateChanged(BTConnectionState connectionState, BTConnectionState connectionState1);

	void onStateChanged(BTState state, BTState state1);
}
