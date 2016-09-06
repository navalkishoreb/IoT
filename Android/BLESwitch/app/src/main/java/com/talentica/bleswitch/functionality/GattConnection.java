package com.talentica.bleswitch.functionality;

import com.talentica.bleswitch.functionality.bleConnectionModels.BLEGattService;

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
