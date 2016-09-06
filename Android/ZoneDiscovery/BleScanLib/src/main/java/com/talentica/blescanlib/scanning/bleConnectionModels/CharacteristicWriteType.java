package com.talentica.blescanlib.scanning.bleConnectionModels;

/**
 * Created by NavalB on 24-05-2016.
 */
public enum CharacteristicWriteType {
	/**
	 * Write characteristic, requesting acknoledgement by the remote device
	 */
//	public static final int WRITE_TYPE_DEFAULT = 0x02;
	/**
	 * Wrtite characteristic without requiring a response by the remote device
	 */
//	public static final int WRITE_TYPE_NO_RESPONSE = 0x01;
	/**
	 * Write characteristic including authentication signature
	 */
//	public static final int WRITE_TYPE_SIGNED = 0x04;


	NO_RESPONSE(0x01),
	DEFAULT(0x02),
	SIGNED(0x04);

	private int value;

	CharacteristicWriteType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
