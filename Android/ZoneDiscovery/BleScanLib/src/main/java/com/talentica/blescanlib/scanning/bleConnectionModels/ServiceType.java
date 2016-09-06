package com.talentica.blescanlib.scanning.bleConnectionModels;

/**
 * Created by NavalB on 24-05-2016.
 */
public enum ServiceType {
	PRIMARY("Primary Service"),
	SECONDARY("Secondary Service");

	private final String type;

	ServiceType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
