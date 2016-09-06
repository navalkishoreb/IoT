package com.talentica.zonediscovery.zoning;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.talentica.blescanlib.base.BaseActivity;
import com.talentica.blescanlib.scanning.blemodels.BLEScanResult;
import com.talentica.zonediscovery.R;

import static com.talentica.blescanlib.scanning.service.ScanningView.EXTRA_DEVICE_DATA;

public final class Zone extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_device);
    Fragment fragment = ZoneComparison.newInstance();
    setFragment(R.string.tag_fragment_zone_comparison, fragment);
  }
}
