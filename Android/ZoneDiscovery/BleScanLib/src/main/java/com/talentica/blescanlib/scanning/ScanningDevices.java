package com.talentica.blescanlib.scanning;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.talentica.blescanlib.R;
import com.talentica.blescanlib.base.Debug;
import com.talentica.blescanlib.scanning.blemodels.BLEScanResult;
import com.talentica.blescanlib.scanning.service.ScanningPresenter;
import com.talentica.blescanlib.scanning.service.ScanningView;
import java.util.List;

import static com.talentica.blescanlib.scanning.service.BackgroundDeviceScanService.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public final class ScanningDevices extends AbstractScanningFragmentWithLoader
    implements ScanForFirstDevice {

  private Interaction interaction;

   interface Interaction {
    void showSearchScreen();
  }

  public ScanningDevices() {
    // Required empty public constructor
  }

  public static Fragment newInstance() {
    return new ScanningDevices();
  }

  @SuppressWarnings("deprecation") @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof Interaction) {
      interaction = (Interaction) activity;
    } else {
      throw new IllegalArgumentException(
          "Activity need to implement showSearchDevicesScreen interface.");
    }
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    ScanningPresenter scanningPresenter = new ScanningFragmentPresenterImpl(this);
    super.setScanningPresenter(scanningPresenter);
    super.onCreate(savedInstanceState);
  }

  @Override public void onScanningStoppedUIUpdate() {
    Debug.d(Debug.SCANNING_TAG, "onScanningStoppedUIUpdate");
    hideLoading();
    setLoadingText(getString(R.string.bsl_scanning_stopped));
  }

  @Override public void onScanningStartUIUpdate() {
    Debug.d(Debug.SCANNING_TAG,"onScanningStartUIUpdate");
    showLoading();
    setLoadingText(getString(R.string.bsl_scanning_devices));
  }

  @Override public void onAttemptToConnectProfile() {
    Debug.d(Debug.SCANNING_TAG,"onAttemptToConnectProfile");
    showLoading();
    setLoadingText(getString(R.string.bsl_attempting_connection));
  }

  @Override public void onDeviceDiscoveryStarted() {
    setLoadingText(getString(R.string.bsl_device_discovery_started));
  }

  @Override public void onDeviceDiscoveryStopped() {
    setLoadingText(getString(R.string.bsl_device_discovery_stopped));
  }

  @Override public void showSearchDevicesScreen() {
    Debug.d(Debug.SCANNING_TAG,"showSearchDevicesScreen");
    interaction.showSearchScreen();
  }

  @Override public void launch(BLEScanResult bleScanResult) {
    launchActivity(bleScanResult);
  }

  private void launchActivity(BLEScanResult bleScanResult) {
    PackageManager packageManager = super.getContext().getPackageManager();
    Intent intent = new Intent();
    intent.setAction("blescanlib.intent.action.MAIN");
    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setPackage(getContext().getPackageName());
    List<ResolveInfo> result =
        packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

       /* *
        * reason for being unchecked exception: developer has not performed initial contract  to attach launcher activity
        * also developer has to be minimally involved ans should be informed at runtime of required missing component
        */
    if (result.size() == 0) {
      throw new IllegalArgumentException("No component assigned to required intent");
    }
    for (ResolveInfo resolveInfo : result) {
      Debug.d(TAG, resolveInfo.toString());
      Debug.d(TAG, getComponentInfo(resolveInfo).toString());
    }
    intent.putExtra(ScanningView.EXTRA_DEVICE_DATA, bleScanResult);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    getContext().startActivity(intent);
  }

  private ComponentInfo getComponentInfo(ResolveInfo resolveInfo) {
    if (resolveInfo.activityInfo != null) return resolveInfo.activityInfo;
    throw new IllegalStateException("Missing ComponentInfo!");
  }
}
