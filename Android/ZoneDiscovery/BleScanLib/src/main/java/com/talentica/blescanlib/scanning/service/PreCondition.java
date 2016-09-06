package com.talentica.blescanlib.scanning.service;

/**
 * Created by navalb on 16-06-2016.
 */

public enum PreCondition {
	BT_NOT_ENABLED("Bluetooth is required to be ON.", "Enable"),
	LOCATION_PERMISSION_23_AND_ABOVE("Require location permission to scan BLE devices", "Permit"),
	GPS_PROVIDER_NOT_ENABLED_API_23_AND_ABOVE("Need GPS provider ON.", "Switch On"),
	NO_PROBLEM("Everything is fine", "No Action");

	private final String messageString;
  private final String actionString;

	PreCondition(String messageString, String actionString) {
		this.messageString = messageString;
    this.actionString = actionString;
  }

	public String getMessageString() {
		return messageString;
	}

  public String getActionString() {
    return actionString;
  }
}
