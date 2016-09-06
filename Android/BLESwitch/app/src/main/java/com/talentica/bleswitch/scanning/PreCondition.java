package com.talentica.bleswitch.scanning;

/**
 * Created by navalb on 16-06-2016.
 */

public enum PreCondition {
	BT_NOT_ENABLED("Bluetooth is required to be ON."),
	LOCATION_PERMISSION_23_AND_ABOVE("Require location to scan BLE devices"),
	GPS_PROVIDER_NOT_ENABLED_API_23_AND_ABOVE("Need GPS provider ON."),
	NO_PROBLEM("Everything is fine");

	private final String messageString;

	PreCondition(String messageString) {
		this.messageString = messageString;
	}

	public String getMessageString() {
		return messageString;
	}
}
