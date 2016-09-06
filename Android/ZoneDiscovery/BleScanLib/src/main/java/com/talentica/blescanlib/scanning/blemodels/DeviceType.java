package com.talentica.blescanlib.scanning.blemodels;

/**
 * Created by NavalB on 23-05-2016.
 */
 public enum DeviceType {
	UNKNOWN("Unknown"),
	LE_ONLY("Low Energy - LE-only"),
	CLASSIC_BLUETOOTH("Classic - BR/EDR devices"),
	DUAL_MODE("Dual Mode - BR/EDR/LE");

	private final String type;

	DeviceType(String type) {this.type = type;}

	public String getType() {
		return type;
	}
}
