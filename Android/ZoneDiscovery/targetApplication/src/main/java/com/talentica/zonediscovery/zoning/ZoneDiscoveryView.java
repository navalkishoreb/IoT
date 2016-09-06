package com.talentica.zonediscovery.zoning;

import com.talentica.blescanlib.scanning.blemodels.BLEScanResult;
import com.talentica.blescanlib.scanning.service.ScanningView;

/**
 * Created by NavalB on 30-06-2016.
 */

interface ZoneDiscoveryView extends ScanningView {
  void updateUI(BLEScanResult device);
  //void updateFirstZone(BLEScanResult device);
  //void updateSecondZone(BLEScanResult device);
}
