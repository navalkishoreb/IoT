package com.talentica.blescanlib.feasibility;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import static com.talentica.blescanlib.feasibility.FeasibilityErrorType.DOES_NOT_HAVE_BLE;
import static com.talentica.blescanlib.feasibility.FeasibilityErrorType.DOES_NOT_HAVE_BLUETOOTH;

/**
 * Created by NavalB on 14-06-2016.
 */
final class FeasibilityChecker implements Feasibility {

	private final Context context;

	FeasibilityChecker(Context context) {
		this.context = context;
	}

	@Override
	public FeasibilityErrorType check() {
		FeasibilityErrorType errorType = FeasibilityErrorType.NO_PROBLEM;
		if (isBelowLollipop()) {
			errorType = FeasibilityErrorType.IS_DEVICE_BELOW_LOLLIPOP;
		} else if (doesNotHaveBluetooth()) {
			errorType = DOES_NOT_HAVE_BLUETOOTH;
		} else if (doesNotHaveSmartBluetooth()) {
			errorType = DOES_NOT_HAVE_BLE;
		}
		return errorType;
	}

	private boolean doesNotHaveSmartBluetooth() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			PackageManager packageManager = context.getPackageManager();
			return !packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
		}
		return false;
	}

	private boolean doesNotHaveBluetooth() {
		return !(hasBluetoothFeature() && hasBluetoothAdapter());
	}

	private boolean isBelowLollipop() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
	}

	private boolean hasBluetoothAdapter() {
		BluetoothAdapter bluetoothAdapter;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
			bluetoothAdapter = manager.getAdapter();
		} else {
			bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		}
		return bluetoothAdapter != null;
	}

	private boolean hasBluetoothFeature() {
		PackageManager packageManager = context.getPackageManager();
		return packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH);
	}
}
