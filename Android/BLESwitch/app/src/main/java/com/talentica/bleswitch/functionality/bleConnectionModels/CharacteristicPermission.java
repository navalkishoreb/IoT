package com.talentica.bleswitch.functionality.bleConnectionModels;

/**
 * Created by NavalB on 24-05-2016.
 */
public enum CharacteristicPermission {
	/**
	 * Characteristic read permission
	 */
//	public static final int PERMISSION_READ = 0x01;
	/**
	 * Characteristic permission: Allow encrypted read operations
	 */
//	public static final int PERMISSION_READ_ENCRYPTED = 0x02;
	/**
	 * Characteristic permission: Allow reading with man-in-the-middle protection
	 */
//	public static final int PERMISSION_READ_ENCRYPTED_MITM = 0x04;
	/**
	 * Characteristic write permission
	 */
//	public static final int PERMISSION_WRITE = 0x10;
	/**
	 * Characteristic permission: Allow encrypted writes
	 */
//	public static final int PERMISSION_WRITE_ENCRYPTED = 0x20;
	/**
	 * Characteristic permission: Allow encrypted writes with man-in-the-middle
	 * protection
	 */
//	public static final int PERMISSION_WRITE_ENCRYPTED_MITM = 0x40;
	/**
	 * Characteristic permission: Allow signed write operations
	 */
//	public static final int PERMISSION_WRITE_SIGNED = 0x80;
	/**
	 * Characteristic permission: Allow signed write operations with
	 * man-in-the-middle protection
	 */
//	public static final int PERMISSION_WRITE_SIGNED_MITM = 0x100;
	PERMISSION_READ(0x01),
	PERMISSION_READ_ENCRYPTED(0x02),
	PERMISSION_READ_ENCRYPTED_MITM(0x04),

	PERMISSION_WRITE(0x10),
	PERMISSION_WRITE_ENCRYPTED(0x20),
	PERMISSION_WRITE_ENCRYPTED_MITM(0x40),

	PERMISSION_WRITE_SIGNED(0x80),
	PERMISSION_WRITE_SIGNED_MITM(0x100);

	private final int value;

	CharacteristicPermission(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
