package com.talentica.bleswitch.scanning.service;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Build;
import android.os.ParcelUuid;

import com.talentica.bleswitch.base.Debug;
import com.talentica.bleswitch.scanning.BLEFilterCriteria;
import com.talentica.bleswitch.scanning.blemodels.BLEScanResult;
import com.talentica.bleswitch.scanning.blemodels.ScanCallbackFailureTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by navalb on 16-06-2016.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Scan extends ScanCallback implements Scanner {
	private static final String TAG = "Service";
	private BluetoothLeScanner bluetoothLeScanner;
	private List<ScanFilter> scanFilter;
	private ScanSettings scanSettings;
	private final NotifyScanningView notifyScanningView;

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	Scan(BluetoothAdapter bluetoothAdapter, NotifyScanningView notifyScanningView) {
		this.bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
		if (bluetoothLeScanner == null) {
			throw new NullPointerException("Bluetooth is STATE_OFF, caused BLEScanner to be NULL");
		}
		scanFilter = new ArrayList<>();
		scanFilter.add(new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString(BLEFilterCriteria.SERVICE_UUID)).build());
		scanSettings = new ScanSettings.Builder().build();
		this.notifyScanningView = notifyScanningView;
	}

	@Override
	public void start() {
		bluetoothLeScanner.startScan(scanFilter, scanSettings, this);
	}

	@Override
	public void stop() {
		bluetoothLeScanner.stopScan(this);
	}

	@Override
	public void onScanFailed(int errorCode) {
		super.onScanFailed(errorCode);
		Debug.d(TAG, "onScanFailed " + ScanCallbackFailureTypes.fromErrorCode(errorCode).name());
	}

	@Override
	public void onScanResult(int callbackType, ScanResult result) {
		super.onScanResult(callbackType, result);
		BLEScanResult bleScanResult = new BLEScanResult(result);
		Debug.d(TAG, "onScanResult " + bleScanResult.toString());
		notifyScanningView.notify(bleScanResult);
	}
}
