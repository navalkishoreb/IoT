package com.talentica.blescanlib.scanning.blemodels;

/**
 * Created by NavalB on 23-05-2016.
 * <p/>
 * <p/>
 * public static final int BOND_NONE = 10;
 * <p/>
 * public static final int BOND_BONDING = 11;
 * <p/>
 * public static final int BOND_BONDED = 12;
 */
  public enum BondState {
	BOND_NONE("no shared link key with the remote device"),
	BONDING("bonding (pairing) is in progress"),
	BONDED("A shared link keys exists locally for the remote device");

	private final String description;

	BondState(String description) {this.description = description;}

	public String getDescription() {
		return description;
	}
}
