package com.talentica.blescanlib.scanning.bleConnectionModels;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.os.Build;

import com.talentica.blescanlib.base.BLEScanLibException;
import com.talentica.blescanlib.base.Debug;
import com.talentica.blescanlib.scanning.BLEFilterCriteria;

import java.util.List;
import java.util.UUID;

/**
 * Created by navalb on 18-06-2016.
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class GattCallback extends BluetoothGattCallback {
	private static final String TAG = GattCallback.class.getSimpleName();
	private final PostOnUIThread postOnUIThread;

	public GattCallback(GattConnection gattConnection) {
		postOnUIThread = new PostOnUIThread(gattConnection);
	}

	@Override
	public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
		super.onConnectionStateChange(gatt, status, newState);
		Debug.i(TAG, "onConnectionStateChange " + "status " + status + " newState " + newState);
		printStatus(status);
		delegateConnectionState(newState);
	}

	private void delegateConnectionState(int newState) {
		switch (newState) {
			case BluetoothProfile.STATE_CONNECTED:
				Debug.d(TAG,"connecting...");
				postOnUIThread.onConnected();
				break;
			case BluetoothProfile.STATE_DISCONNECTED:
				Debug.d(TAG,"disconnecting...");
				postOnUIThread.onDisconnected();
				break;
			default:
				throw new BLEScanLibException("There cannot be any other state "+newState);
		}
	}

	private void printStatus(int status) {
		switch (status) {
			case BluetoothGatt.GATT_SUCCESS:
				Debug.i(TAG, "GATT operation completed successfully");
				break;
			case BluetoothGatt.GATT_CONNECTION_CONGESTED:
				Debug.e(TAG, "A remote device connection is congested.");
				break;
			case BluetoothGatt.GATT_FAILURE:
				Debug.e(TAG, "A GATT operation failed, generic error statement.");
				break;
			case BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION:
				Debug.e(TAG, "Insufficient authentication for a given operation");
				break;
			case BluetoothGatt.GATT_INSUFFICIENT_ENCRYPTION:
				Debug.e(TAG, "Insufficient encryption for a given operation");
				break;
			case BluetoothGatt.GATT_INVALID_ATTRIBUTE_LENGTH:
				Debug.e(TAG, "A write operation exceeds the maximum length of the attribute ");
				break;
			case BluetoothGatt.GATT_INVALID_OFFSET:
				Debug.e(TAG, "A read or write operation was requested with an invalid offset");
				break;
			case BluetoothGatt.GATT_READ_NOT_PERMITTED:
				Debug.e(TAG, "GATT read operation is not permitted");
				break;
			case BluetoothGatt.GATT_REQUEST_NOT_SUPPORTED:
				Debug.e(TAG, "The given request is not supported");
				break;
			case BluetoothGatt.GATT_WRITE_NOT_PERMITTED:
				Debug.e(TAG, "GATT write operation is not permitted");
				break;
			default:
				Debug.e(TAG, "not specified.");
		}
	}

	@Override
	public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
		super.onCharacteristicRead(gatt, characteristic, status);
		Debug.i(TAG, "onCharacteristicRead " + "status " + status);
		printStatus(status);
		if (status == BluetoothGatt.GATT_SUCCESS) {
			postOnUIThread.onSuccessfulCharacteristicRead();
		} else {
			postOnUIThread.onFailureCharacteristicWrite();
		}
	}

	@Override
	public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
		super.onCharacteristicWrite(gatt, characteristic, status);
		Debug.i(TAG, "onCharacteristicWrite " + "status " + status);
		printStatus(status);
		if (status == BluetoothGatt.GATT_SUCCESS) {
			postOnUIThread.onSuccessfulCharacteristicWrite();
		} else {
			postOnUIThread.onFailureCharacteristicWrite();
		}
	}

	@Override
	public void onServicesDiscovered(BluetoothGatt gatt, int status) {
		super.onServicesDiscovered(gatt, status);
		Debug.i(TAG, "onServicesDiscovered " + "status " + status);
		printStatus(status);
		List<BluetoothGattService> serviceList = gatt.getServices();
		BLEGattService bleGattService = null;
		Debug.i(TAG, "---------------");
		for (BluetoothGattService bluetoothGattService : serviceList) {
			if (bluetoothGattService.getUuid().compareTo(UUID.fromString(BLEFilterCriteria.SERVICE_UUID)) == 0) {
				bleGattService = new BLEGattService(bluetoothGattService);
				Debug.i(TAG, bleGattService.toString());
				Debug.i(TAG, "---------------");
				break;
			}
		}
		if (bleGattService == null) {
			throw new IllegalArgumentException("Required service not found");
		}
		postOnUIThread.loadServices(bleGattService);
	}
}
