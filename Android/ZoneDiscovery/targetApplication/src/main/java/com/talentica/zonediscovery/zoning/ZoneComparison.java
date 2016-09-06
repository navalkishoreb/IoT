package com.talentica.zonediscovery.zoning;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.talentica.blescanlib.scanning.blemodels.BLEScanResult;
import com.talentica.blescanlib.scanning.service.AbstractScanningFragment;
import com.talentica.zonediscovery.R;

/**
 * A simple {@link Fragment} subclass.
 */
public final class ZoneComparison extends AbstractScanningFragment
    implements View.OnClickListener, ZoneDiscoveryView {

  private TextSwitcher zoneNearName;
  private ListView listView;

  private ZoneDiscoveryPresenter zoneDiscoveryPresenter;

  static Fragment newInstance() {
    return new ZoneComparison();
  }

  public ZoneComparison() {
    // Required empty public constructor
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    zoneDiscoveryPresenter = new ZoneDiscoveryFragmentPresenterImpl(this);
    super.setScanningPresenter(zoneDiscoveryPresenter);
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_zone_comparison, container, false);
  }


  @Override public void setViews() {
    zoneNearName.setFactory(new ViewSwitcher.ViewFactory() {
      @Override public View makeView() {
        return new TextView(getContext(),null,0,R.style.ZoneDeviceName);
      }
    });
    zoneNearName.setInAnimation(getContext(), R.anim.fade_in);
    zoneNearName.setOutAnimation(getContext(), R.anim.fade_out);
    listView.setAdapter(zoneDiscoveryPresenter.getAdapter());
  }

  @Override public void findViews(@NonNull View rootView) {
    zoneNearName = (TextSwitcher) rootView.findViewById(R.id.zone_device_name);
    listView = (ListView) rootView.findViewById(R.id.list_ble_devices);
  }

  @Override public void onClick(View v) {
    //ColorLed.launch(this, bleScanResult);
  }

  @Override public void updateUI(BLEScanResult device) {
    zoneNearName.setText(device.getDeviceName());
  }

  @Override public void showSearchDevicesScreen() {

  }

  @Override public void onScanningStartUIUpdate() {

  }

  @Override public void onScanningStoppedUIUpdate() {

  }

  @Override public void onAttemptToConnectProfile() {

  }

  @Override public void onDeviceDiscoveryStarted() {

  }

  @Override public void onDeviceDiscoveryStopped() {

  }
}
