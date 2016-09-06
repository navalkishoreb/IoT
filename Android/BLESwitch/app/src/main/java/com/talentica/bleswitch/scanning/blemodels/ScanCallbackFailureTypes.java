package com.talentica.bleswitch.scanning.blemodels;

import android.annotation.TargetApi;
import android.bluetooth.le.ScanCallback;
import android.os.Build;

/**
 * Created by navalb on 17-06-2016.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public enum ScanCallbackFailureTypes {
	SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES(5),
	SCAN_FAILED_FEATURE_UNSUPPORTED(ScanCallback.SCAN_FAILED_FEATURE_UNSUPPORTED),
	SCAN_FAILED_INTERNAL_ERROR(ScanCallback.SCAN_FAILED_INTERNAL_ERROR),
	SCAN_FAILED_APPLICATION_REGISTRATION_FAILED(ScanCallback.SCAN_FAILED_APPLICATION_REGISTRATION_FAILED),
	SCAN_FAILED_ALREADY_STARTED(ScanCallback.SCAN_FAILED_ALREADY_STARTED);

	private final int errorCode;

	ScanCallbackFailureTypes(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public static ScanCallbackFailureTypes fromErrorCode(int errorCode) {
		switch (errorCode) {
			case 5:
				return SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES;
			case ScanCallback.SCAN_FAILED_FEATURE_UNSUPPORTED:
				return SCAN_FAILED_FEATURE_UNSUPPORTED;
			case ScanCallback.SCAN_FAILED_INTERNAL_ERROR:
				return SCAN_FAILED_INTERNAL_ERROR;
			case ScanCallback.SCAN_FAILED_APPLICATION_REGISTRATION_FAILED:
				return SCAN_FAILED_APPLICATION_REGISTRATION_FAILED;
			case ScanCallback.SCAN_FAILED_ALREADY_STARTED:
				return SCAN_FAILED_ALREADY_STARTED;
			default:
				throw new IllegalArgumentException("error code not defined " + errorCode);
		}
	}
}
