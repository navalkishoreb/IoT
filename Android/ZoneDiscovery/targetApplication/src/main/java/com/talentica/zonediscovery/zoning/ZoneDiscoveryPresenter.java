package com.talentica.zonediscovery.zoning;

import com.talentica.blescanlib.scanning.service.ScanningPresenter;

/**
 * Created by NavalB on 01-07-2016.
 */

public interface ZoneDiscoveryPresenter extends ScanningPresenter {

  BLEDeviceListAdapter getAdapter();
}
