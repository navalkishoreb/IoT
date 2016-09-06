package com.talentica.bleswitch.scanning.blemodels;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by navalb on 16-06-2016.
 */

public enum BTState {
	STATE_OFF(BluetoothAdapter.STATE_OFF),
	STATE_TURNING_ON(BluetoothAdapter.STATE_TURNING_ON),
	STATE_ON(BluetoothAdapter.STATE_ON),
	STATE_TURNING_OFF(BluetoothAdapter.STATE_TURNING_OFF);

/*	public static final int STATE_OFF = 10;
	public static final int STATE_TURNING_ON = 11;
	public static final int STATE_ON = 12;
	public static final int STATE_TURNING_OFF = 13;*/

	private final int value;

	BTState(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static BTState fromValue(int state){
		switch (state){
			case BluetoothAdapter.STATE_OFF: return STATE_OFF;
			case BluetoothAdapter.STATE_ON: return STATE_ON;
			case BluetoothAdapter.STATE_TURNING_OFF: return STATE_TURNING_OFF;
			case BluetoothAdapter.STATE_TURNING_ON: return STATE_TURNING_ON;
			default: throw new IllegalArgumentException("No such value exists");
		}
	}
}
