package com.talentica.blescanlib.scanning.blemodels;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by navalb on 16-06-2016.
 */

public enum BTConnectionState {
  STATE_DISCONNECTED(BluetoothAdapter.STATE_DISCONNECTED, "The profile is in disconnected state"),
  STATE_CONNECTING(BluetoothAdapter.STATE_CONNECTING, "The profile is in connecting state"),
  STATE_CONNECTED(BluetoothAdapter.STATE_CONNECTED, "The profile is in connected state"),
  STATE_DISCONNECTING(BluetoothAdapter.STATE_DISCONNECTING,
      "The profile is in disconnecting state");

  private final int value;
  private final String message;

  BTConnectionState(int value, String message) {
    this.value = value;
    this.message = message;
  }


/*	public static final int STATE_DISCONNECTED  = 0;
  public static final int STATE_CONNECTING    = 1;
	public static final int STATE_CONNECTED     = 2;
	public static final int STATE_DISCONNECTING = 3;*/

  public int getValue() {
    return value;
  }

  public String getMessage() {
    return message;
  }
}
