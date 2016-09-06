package com.talentica.bleswitch.scanning.service;

import android.os.Handler;
import android.os.Message;

import com.talentica.bleswitch.scanning.blemodels.BLEScanResult;

/**
 * Created by navalb on 16-06-2016.
 */

public class ScanningDevicesHandler extends Handler {
	private final ScanningView scanningDevices;

	public ScanningDevicesHandler(ScanningView scanningDevices) {
		this.scanningDevices = scanningDevices;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		ScanningViewAction action = ScanningViewAction.fromAction(msg.what);
		switch (action) {
			case SHOW_SEARCH_SCREEN:
				scanningDevices.showSearchScreen();
				break;
			case ATTEMPTING_TO_CONNECT_PROFILE:
				scanningDevices.updateUIAttemptingConnect();
				break;
			case NOT_CONNECTED_TO_ANY_PROFILE:
				scanningDevices.showSearchScreen();
				break;
			case SCAN_RESULT:
				BLEScanResult bleScanResult = msg.getData().getParcelable(ScanningView.EXTRA_SCAN_RESULT);
				scanningDevices.addDevice(bleScanResult);
				break;
			default:
				throw new IllegalArgumentException("ScanningViewAction was not implemented "+action.name());
		}
	}
}
