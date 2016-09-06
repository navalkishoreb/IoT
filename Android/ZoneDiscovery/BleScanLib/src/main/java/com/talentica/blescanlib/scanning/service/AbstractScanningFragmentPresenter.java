package com.talentica.blescanlib.scanning.service;

import com.talentica.blescanlib.base.Debug;
import com.talentica.blescanlib.scanning.TAG;

/**
 * Created by NavalB on 30-06-2016.
 */

public abstract class AbstractScanningFragmentPresenter
    implements ScanningPresenter, ScanningPresenter.Callback {

  private final Prerequisites prerequisites;
  private final ScannerServiceConnection scannerServiceConnection;
  private final ScanningView scanningView;

  public AbstractScanningFragmentPresenter(ScanningView scanningView) {
    this.scanningView = scanningView;
    this.prerequisites = new PreConditionChecker(scanningView.getContext());
    this.scannerServiceConnection = new ScannerServiceConnector(new ScanningDevicesHandler(this));
  }

  @Override public void startScanningService() {
    Debug.d(TAG.PRESENTER, "startScanningService");
    scannerServiceConnection.startScanning(scanningView.getContext());
  }

  @Override public void stopScanningService() {
    Debug.d(Debug.SCANNING_TAG, "stopScanningService");
    scanningView.onScanningStoppedUIUpdate();
    scannerServiceConnection.stopScanning();
  }

  @Override public void unbindService() {
    scannerServiceConnection.unBindService(scanningView.getContext());
  }

  @Override public void takeAction() {
    Debug.d(TAG.PRESENTER, "taking action");
    scanningView.onScanningStartUIUpdate();
    takeAction(prerequisites.check());
  }

  private void takeAction(PreCondition check) {
    if (check != null) {
      switch (check) {
        case BT_NOT_ENABLED:
          requestEnableBluetooth();
          break;
        case GPS_PROVIDER_NOT_ENABLED_API_23_AND_ABOVE:
          launchGPSProvider();
          break;
        case LOCATION_PERMISSION_23_AND_ABOVE:
          requestLocationPermission();
          break;
        case NO_PROBLEM:
          startScanningService();
          break;
        default:
          throw new IllegalArgumentException("No action provided for precondition " + check.name());
      }
    } else {
      throw new NullPointerException("prerequisites check cannot return NULL.");
    }
  }

  private void requestLocationPermission() {
    performPreConditionAction(PreCondition.LOCATION_PERMISSION_23_AND_ABOVE);
  }

  private void launchGPSProvider() {
    performPreConditionAction(PreCondition.GPS_PROVIDER_NOT_ENABLED_API_23_AND_ABOVE);
  }

  private void requestEnableBluetooth() {
    performPreConditionAction(PreCondition.BT_NOT_ENABLED);
  }

  private void performPreConditionAction(PreCondition preCondition) {
    Debug.d(TAG.PRESENTER, "performPreConditionAction " + preCondition.name());
    scanningView.onScanningStoppedUIUpdate();
    scanningView.requestDialogFor(preCondition);
  }

  private void showSearchScreen() {
    Debug.d(TAG.PRESENTER, "showSearchScreen");
    scanningView.onScanningStoppedUIUpdate();
    scanningView.showSearchDevicesScreen();
  }

  @Override public void preRequisitesRequirementNotFulfilled() {
    Debug.d(TAG.PRESENTER, "preRequisitesRequirementNotFulfilled");
    showSearchScreen();
  }

  @Override public void onAttemptToConnectProfile() {
    Debug.d(TAG.PRESENTER, "onAttemptToConnectProfile");
    scanningView.onAttemptToConnectProfile();
  }

  @Override public void onNoConnection() {
    showSearchScreen();
  }

  @Override public void onScanningStopped() {
    Debug.d(TAG.PRESENTER, "onScanningStopped");
    scanningView.onScanningStoppedUIUpdate();
  }

  @Override public void onDeviceDiscoveryStarted() {
    scanningView.onDeviceDiscoveryStarted();
  }

  @Override public void onDeviceDiscoveryStopped() {
    scanningView.onDeviceDiscoveryStopped();
  }
}
