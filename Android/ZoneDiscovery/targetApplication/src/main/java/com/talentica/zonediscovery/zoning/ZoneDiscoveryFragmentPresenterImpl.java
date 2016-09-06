package com.talentica.zonediscovery.zoning;

import android.util.Log;
import com.talentica.blescanlib.scanning.blemodels.BLEScanResult;
import com.talentica.blescanlib.scanning.service.AbstractScanningFragmentPresenter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NavalB on 30-06-2016.
 */

final class ZoneDiscoveryFragmentPresenterImpl extends AbstractScanningFragmentPresenter
    implements ZoneDiscoveryPresenter {

  private final Map<String, BLEScanResult> scanMap;
  private final ZoneDiscoveryView zoneDiscoveryView;
  private final Comparator<BLEScanResult> maxRssi;
  private String currentlyNearTo;
  private final BLEDeviceListAdapter bleDeviceListAdapter;

  ZoneDiscoveryFragmentPresenterImpl(ZoneDiscoveryView zoneDiscoveryView) {
    super(zoneDiscoveryView);
    this.zoneDiscoveryView = zoneDiscoveryView;
    this.scanMap = new HashMap<>();
    this.maxRssi = getRssiComparator();
    this.bleDeviceListAdapter = new BLEDeviceListAdapter(zoneDiscoveryView.getContext());
  }

  private Comparator getRssiComparator() {
    return new Comparator<BLEScanResult>() {
      @Override public int compare(BLEScanResult lhs, BLEScanResult rhs) {
        return lhs.getRssi() - rhs.getRssi();
      }
    };
  }

  @Override public void onDeviceFound(BLEScanResult device) {
    scanMap.put(device.getDeviceAddress(), device);
    bleDeviceListAdapter.addDevice(device);
    Collection<BLEScanResult> collection = scanMap.values();
    BLEScanResult topDevice = Collections.max(collection, maxRssi);
    if (currentlyNearTo == null || !currentlyNearTo.equals(topDevice.getDeviceAddress())) {
      zoneDiscoveryView.updateUI(topDevice);
      currentlyNearTo = topDevice.getDeviceAddress();
      Log.e("Update", currentlyNearTo);
    }
  }

  @Override public BLEDeviceListAdapter getAdapter() {
    return bleDeviceListAdapter;
  }
}
