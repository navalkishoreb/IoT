package com.talentica.bleswitch.scanning.service;

/**
 * Created by navalb on 17-06-2016.
 */

public interface BackgroundDeviceScan extends BTChangeReceiver{
	int START_SCAN = 901;
	int STOP_SCAN = 902;

	void startScanning();

	void stopScanning();
}
