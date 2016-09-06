package com.talentica.blescanlib.scanning.service;

/**
 * Created by navalb on 17-06-2016.
 */

enum ScanningAction {
  SCANNING_STOPPED(804),
  ATTEMPTING_TO_CONNECT_PROFILE(805),
  NOT_CONNECTED_TO_ANY_PROFILE(806),
  SCAN_RESULT(807),
  DEVICE_DISCOVERY_STARTED(808),
  DEVICE_DISCOVERY_STOPPED(809);

  private final int action;

  ScanningAction(int action) {
    this.action = action;
  }

  public int getAction() {
    return action;
  }

  public static ScanningAction fromAction(int action) {
    switch (action) {
      case 804:
        return SCANNING_STOPPED;
      case 805:
        return ATTEMPTING_TO_CONNECT_PROFILE;
      case 806:
        return NOT_CONNECTED_TO_ANY_PROFILE;
      case 807:
        return SCAN_RESULT;
      case 808:
        return DEVICE_DISCOVERY_STARTED;
      case 809:
        return DEVICE_DISCOVERY_STOPPED;
      default:
        throw new IllegalArgumentException("No such scanning view action defined");
    }
  }
}
