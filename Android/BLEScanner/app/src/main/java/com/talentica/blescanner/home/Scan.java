package com.talentica.blescanner.home;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import com.talentica.blescanner.base.Debug;

/**
 * Created by NavalB on 24-05-2016.
 */
public class Scan implements Scanner {
	private final static String TAG = Scan.class.getSimpleName();
	private static final int REQUEST_ENABLE_BLUETOOTH = 902;
	private static final int REQUEST_ENABLE_LOCATION_PROVIDER = 903;

	private final ScanningView scanningView;
	private BluetoothAdapter bluetoothAdapter;
	private BluetoothScanner bluetoothScanner;
	private boolean isScanning;

	public Scan(ScanningView scanningView) {
		this.scanningView = scanningView;
		bluetoothAdapter = getBluetoothAdapter();
		bluetoothAdapter.cancelDiscovery();
		bluetoothScanner = new BLEScanner(this.scanningView.getScannerCallback());
	}

	private BluetoothAdapter getBluetoothAdapter() {
		BluetoothManager bluetoothManager = (BluetoothManager) scanningView.getContext().getSystemService(Context.BLUETOOTH_SERVICE);
		return bluetoothManager.getAdapter();
	}

	@Override
	public boolean isScanning() {
		return isScanning;
	}

	@Override
	public void start() {
		if(!isScanning) {
			isScanning = true;
			scanningView.startScan();
			checkServicesAndStartScanning();
		}else{
			Debug.i(TAG,"Scanning in progress.");
		}
	}

	@Override
	public void stop() {
		if(isScanning) {
			isScanning = false;
			stopBluetoothScanner();
			scanningView.stopScan();
		}else{
			Debug.i(TAG,"Not scanning."+this.toString());
		}
	}

	private void stopBluetoothScanner() {
		Debug.d(TAG,"stopping ble scanning...");
		if(bluetoothAdapter.isEnabled()) {
			if (bluetoothScanner != null) {
				bluetoothScanner.stopScan();
			} else {
				Debug.e(TAG, "bluetooth scanner is NULL");
			}
		}else{
			Debug.e(TAG,"bluetooth is not enabled. Can not stop scanner.");
		}
	}

	@Override
	public void performOnActivityResult(int requestCode, int resultCode) {
		switch (requestCode) {
			case REQUEST_ENABLE_BLUETOOTH:
				handleBluetoothRequest(resultCode);
				break;
			case REQUEST_ENABLE_LOCATION_PROVIDER:
				handleLocationProviderRequest();
				break;
			default:
				Debug.e(TAG, "request not handled.");
		}
	}

	private void handleBluetoothRequest(int resultCode) {
		if (resultCode == Activity.RESULT_OK) {
			Debug.d(TAG, "bluetooth was  enabled...");
//			start();
		} else if (resultCode == Activity.RESULT_CANCELED) {
			Debug.e(TAG, "bluetooth was NOT enabled...");
			stop();
		}
	}

	private void handleLocationProviderRequest() {
		if (checkLocationProvider()) {
			Debug.d(TAG, "location provider was switched on...");
//			start();
		} else {
			Debug.e(TAG, "location provider was NOT switched on...");
			stop();
		}
	}

	private void checkServicesAndStartScanning() {
		boolean flag = enableLocationProvider();
		if (flag) {
			flag = enableBluetooth();
		}
		if (flag) {
			startBluetoothScanner();
		}
	}

	private boolean enableLocationProvider() {
		Debug.d(TAG, "enabling location provider...");
		if (!checkLocationProvider()) {
			Debug.e(TAG, "location provider not switched on.");
			Debug.d(TAG, "launching intent to switch on location provider...");
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			scanningView.startActivityForResult(intent, REQUEST_ENABLE_LOCATION_PROVIDER);
			return false;
		} else {
			Debug.d(TAG, "location provider is already switched on...");
			return true;
		}
	}

	private boolean enableBluetooth() {
		Debug.d(TAG, "enabling bluetooth...");
		if (!bluetoothAdapter.isEnabled()) {
			Debug.e(TAG, "bluetooth not enabled.");
			Debug.d(TAG, "launching intent to switch on bluetooth...");
			scanningView.startActivityForResult(getBluetoothEnableIntent(), REQUEST_ENABLE_BLUETOOTH);
			return false;
		} else {
			Debug.i(TAG, "bluetooth is already enabled.");
			return true;
		}
	}

	private void startBluetoothScanner() {
			Debug.d(TAG, "starting scan...");
			bluetoothScanner.setBLEScanner(getBluetoothLeScanner());
			bluetoothScanner.scan();
	}

	private boolean checkLocationProvider() {
		LocationManager locationManager = (LocationManager) scanningView.getContext().getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	private Intent getBluetoothEnableIntent() {
		Intent intent = new Intent();
		intent.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		return intent;
	}

	private BluetoothLeScanner getBluetoothLeScanner() {
		return bluetoothAdapter.getBluetoothLeScanner();
	}
}
