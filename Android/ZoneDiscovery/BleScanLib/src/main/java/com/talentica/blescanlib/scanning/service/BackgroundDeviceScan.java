package com.talentica.blescanlib.scanning.service;

/**
 * Created by navalb on 17-06-2016.
 */

interface BackgroundDeviceScan extends BTChangeReceiver{
	int START_SCAN = 901;
	int STOP_SCAN = 902;

	void startScanning();

	void stopScanning();
}
