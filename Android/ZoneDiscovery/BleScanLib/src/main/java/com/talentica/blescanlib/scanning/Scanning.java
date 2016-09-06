package com.talentica.blescanlib.scanning;

import com.talentica.blescanlib.R;
import com.talentica.blescanlib.scanning.service.AbstractScanningActivity;

public final class Scanning extends AbstractScanningActivity  implements SearchView.Interaction, ScanningDevices.Interaction
    {

  @Override public void startScan() {
    setCurrentFragment(R.string.bsl_tag_fragment_scanning_devices);
  }

  @Override public void showSearchScreen() {
    setCurrentFragment(R.string.bsl_tag_fragment_search);
  }
}
