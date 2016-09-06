package com.talentica.blescanlib.scanning.bleConnectionModels;

/**
 * Created by NavalB on 24-05-2016.
 */
public enum CharacteristicProperty {
	/**
	 * Characteristic proprty: Characteristic is broadcastable.
	 */
//	public static final int PROPERTY_BROADCAST = 0x01;
	/**
	 * Characteristic property: Characteristic is readable.
	 */
//	public static final int PROPERTY_READ = 0x02;
	/**
	 * Characteristic property: Characteristic can be written without response.
	 */
//	public static final int PROPERTY_WRITE_NO_RESPONSE = 0x04;
	/**
	 * Characteristic property: Characteristic can be written.
	 */
//	public static final int PROPERTY_WRITE = 0x08;
	/**
	 * Characteristic property: Characteristic supports notification
	 */
//	public static final int PROPERTY_NOTIFY = 0x10;
	/**
	 * Characteristic property: Characteristic supports indication
	 */
//	public static final int PROPERTY_INDICATE = 0x20;
	/**
	 * Characteristic property: Characteristic supports write with signature
	 */
//	public static final int PROPERTY_SIGNED_WRITE = 0x40;
	/**
	 * Characteristic property: Characteristic has extended properties
	 */
//	public static final int PROPERTY_EXTENDED_PROPS = 0x80;
	PROPERTY_BROADCAST(0x01),
	PROPERTY_READ(0x02),
	PROPERTY_WRITE_NO_RESPONSE(0X04),
	PROPERTY_WRITE(0X08),
	PROPERTY_NOTIFY(0X10),
	PROPERTY_INDICATE(0X20),
	PROPERTY_SIGNED_WRITE(0X40),
	PROPERTY_EXTENDED_PROPS(0X80);

	private final int value;

	CharacteristicProperty(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
