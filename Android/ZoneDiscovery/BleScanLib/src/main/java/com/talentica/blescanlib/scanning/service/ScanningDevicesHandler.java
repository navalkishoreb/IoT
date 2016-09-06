package com.talentica.blescanlib.scanning.service;

import android.bluetooth.le.ScanResult;
import android.os.Handler;
import android.os.Message;
import com.talentica.blescanlib.scanning.blemodels.BLEScanResult;

/**
 * Created by navalb on 16-06-2016.
 */

final class ScanningDevicesHandler extends Handler {
  private final ScanningPresenter.Callback callback;

  ScanningDevicesHandler(ScanningPresenter.Callback callback) {
    this.callback = callback;
  }

  @Override public void handleMessage(Message msg) {
    super.handleMessage(msg);
    ScanningAction action = ScanningAction.fromAction(msg.what);
    switch (action) {
      case SCANNING_STOPPED:
        callback.onScanningStopped();
        break;
      case ATTEMPTING_TO_CONNECT_PROFILE:
        callback.onAttemptToConnectProfile();
        break;
      case NOT_CONNECTED_TO_ANY_PROFILE:
        callback.onNoConnection();
        break;
      case SCAN_RESULT:
        BLEScanResult bleScanResult = new BLEScanResult(
            (ScanResult) msg.getData().getParcelable(ScanningView.EXTRA_DEVICE_DATA));
        callback.onDeviceFound(bleScanResult);
        break;
      default:
        throw new IllegalArgumentException("ScanningAction was not implemented " + action.name());
    }
  }
}
