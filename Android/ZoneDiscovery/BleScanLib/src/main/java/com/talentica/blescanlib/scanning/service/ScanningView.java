package com.talentica.blescanlib.scanning.service;

import com.talentica.blescanlib.base.BaseView;

/**
 * Created by navalb on 17-06-2016.
 */

public interface ScanningView extends BaseView {

	String EXTRA_DEVICE_DATA = "result_data";

	void setScanningPresenter(ScanningPresenter scanningPresenter);

	void showSearchDevicesScreen();

	void requestDialogFor(PreCondition preCondition);

	void onScanningStartUIUpdate();

	void onScanningStoppedUIUpdate();

	void onAttemptToConnectProfile();

	void onDeviceDiscoveryStarted();

	void onDeviceDiscoveryStopped();
}
