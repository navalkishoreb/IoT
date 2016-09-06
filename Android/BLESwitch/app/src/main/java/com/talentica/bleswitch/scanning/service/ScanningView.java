package com.talentica.bleswitch.scanning.service;

import com.talentica.bleswitch.scanning.blemodels.BLEScanResult;

/**
 * Created by navalb on 17-06-2016.
 */

public interface ScanningView {

	String EXTRA_SCAN_RESULT = "scan_result";

	void showSearchScreen();

	void updateUIAttemptingConnect();

	void addDevice(BLEScanResult device);
}
