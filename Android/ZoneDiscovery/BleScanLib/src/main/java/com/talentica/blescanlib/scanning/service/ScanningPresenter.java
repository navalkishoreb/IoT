package com.talentica.blescanlib.scanning.service;

import com.talentica.blescanlib.scanning.blemodels.BLEScanResult;

/**
 * Created by NavalB on 30-06-2016.
 */

public interface ScanningPresenter {

  void startScanningService();

  void stopScanningService();

  void unbindService();

  void takeAction();

  void preRequisitesRequirementNotFulfilled();

  interface Callback {

    void onDeviceFound(BLEScanResult device);

    void onNoConnection();

    void onAttemptToConnectProfile();

    void onScanningStopped();

    void onDeviceDiscoveryStarted();

    void onDeviceDiscoveryStopped();
  }
}
