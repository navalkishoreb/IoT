package com.talentica.bleswitch.scanning.blemodels;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by navalb on 16-06-2016.
 */

public enum BTConnectionState {
	STATE_DISCONNECTED(BluetoothAdapter.STATE_DISCONNECTED),
	STATE_CONNECTING(BluetoothAdapter.STATE_CONNECTING),
	STATE_CONNECTED(BluetoothAdapter.STATE_CONNECTED),
	STATE_DISCONNECTING(BluetoothAdapter.STATE_DISCONNECTING);

	private final int value;

	BTConnectionState(int value) {
		this.value = value;
	}


/*	public static final int STATE_DISCONNECTED  = 0;
	public static final int STATE_CONNECTING    = 1;
	public static final int STATE_CONNECTED     = 2;
	public static final int STATE_DISCONNECTING = 3;*/

	public int getValue() {
		return value;
	}
}
