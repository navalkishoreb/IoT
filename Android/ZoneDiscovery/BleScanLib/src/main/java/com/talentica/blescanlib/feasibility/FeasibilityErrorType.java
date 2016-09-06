package com.talentica.blescanlib.feasibility;

/**
 * Created by NavalB on 23-05-2016.
 */
enum FeasibilityErrorType {
	IS_DEVICE_BELOW_LOLLIPOP("This application works only in devices with Android Lollipop and above."),
	DOES_NOT_HAVE_BLUETOOTH("There is no bluetooth hardware available in your device."),
	DOES_NOT_HAVE_BLE("This application requires smart bluetooth to function."),
	NO_PROBLEM("All feasibility condition are met.");

	private final String error;

	FeasibilityErrorType(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
}
