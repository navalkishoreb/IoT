package com.talentica.blescanlib.scanning.bleConnectionModels;

/**
 * Created by NavalB on 24-05-2016.
 */
public interface GattConnection {
	void loadServices(BLEGattService gattServiceList);

	void onConnected();

	void onDisconnected();

	void onSuccessfulCharacteristicRead();

	void onSuccessfulCharacteristicWrite();

	void onFailureCharacteristicWrite();

	void onFailureCharacteristicRead();
}
