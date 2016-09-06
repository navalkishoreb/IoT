package com.talentica.blescanlib.scanning.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.talentica.blescanlib.scanning.blemodels.BTConnectionState;
import com.talentica.blescanlib.scanning.blemodels.BTState;

/**
 * Created by navalb on 16-06-2016.
 */

 final class BluetoothChangeReceiver extends BroadcastReceiver {
	private final BTChangeReceiver btChangeReceiver;

	BluetoothChangeReceiver(BTChangeReceiver btChangeReceiver) {
		this.btChangeReceiver = btChangeReceiver;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		switch (intent.getAction()) {
			case BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED:
				int currentConnectionState = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, BluetoothAdapter.STATE_DISCONNECTED);
				int previousConnectionState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_CONNECTION_STATE, BluetoothAdapter.STATE_DISCONNECTED);
				String device = intent.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
				device = (device == null) ? "unkown" : device;
				connectionChanged(currentConnectionState, previousConnectionState, device);
				break;
			case BluetoothAdapter.ACTION_STATE_CHANGED:
				int currentState = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, BluetoothAdapter.STATE_OFF);
				int previousState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_CONNECTION_STATE, BluetoothAdapter.STATE_OFF);
				device = intent.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
				device = (device == null) ? "unkown" : device;
				stateChanged(currentState, previousState, device);
				break;
			case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
				btChangeReceiver.discoveryStarted();
				break;
			case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
				btChangeReceiver.discoveryFinished();
				break;
			default:
		}
	}

	private void connectionChanged(int currentConnectionState, int previousConnectionState, String device) {
		btChangeReceiver.onConnectionStateChanged(getConnectionState(currentConnectionState), getConnectionState(previousConnectionState));
	}

	private void stateChanged(int currentState, int previousState, String device) {
		btChangeReceiver.onStateChanged(getState(currentState), getState(previousState));
	}

	private BTConnectionState getConnectionState(int state) {
		switch (state) {
			case BluetoothAdapter.STATE_DISCONNECTED:
				return BTConnectionState.STATE_DISCONNECTED;
			case BluetoothAdapter.STATE_CONNECTING:
				return BTConnectionState.STATE_CONNECTING;
			case BluetoothAdapter.STATE_CONNECTED:
				return BTConnectionState.STATE_CONNECTED;
			case BluetoothAdapter.STATE_DISCONNECTING:
				return BTConnectionState.STATE_DISCONNECTING;
			default:
				throw new IllegalArgumentException("No such connection state is mentioned");
		}
	}

	private BTState getState(int state) {
		switch (state) {
			case BluetoothAdapter.STATE_OFF:
				return BTState.STATE_OFF;
			case BluetoothAdapter.STATE_ON:
				return BTState.STATE_ON;
			case BluetoothAdapter.STATE_TURNING_OFF:
				return BTState.STATE_TURNING_OFF;
			case BluetoothAdapter.STATE_TURNING_ON:
				return BTState.STATE_TURNING_ON;
			default:
				throw new IllegalArgumentException("No such bluetooth state is mentioned");
		}
	}
}
