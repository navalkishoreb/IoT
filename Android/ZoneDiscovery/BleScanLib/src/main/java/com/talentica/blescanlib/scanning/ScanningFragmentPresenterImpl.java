package com.talentica.blescanlib.scanning;

import com.talentica.blescanlib.scanning.blemodels.BLEScanResult;
import com.talentica.blescanlib.scanning.service.AbstractScanningFragmentPresenter;

/**
 * Created by NavalB on 30-06-2016.
 */

final class ScanningFragmentPresenterImpl extends AbstractScanningFragmentPresenter {
  private final ScanForFirstDevice scanForFirstDevice;

  ScanningFragmentPresenterImpl(ScanForFirstDevice scanForFirstDevice) {
    super(scanForFirstDevice);
    this.scanForFirstDevice = scanForFirstDevice;
  }

  @Override public void onDeviceFound(BLEScanResult device) {
    stopScanningService();
    scanForFirstDevice.launch(device);
  }
}
