package com.talentica.blescanlib.scanning.service;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import com.talentica.blescanlib.base.BaseFragment;
import com.talentica.blescanlib.base.Debug;
import com.talentica.blescanlib.scanning.TAG;

/**
 * Created by NavalB on 30-06-2016.
 */

public abstract class AbstractScanningFragment extends BaseFragment implements ScanningView {
  private static final int REQUEST_ENABLE_BLUETOOTH = 901;
  private static final int REQUEST_ENABLE_LOCATION_PROVIDER = 903;
  private static final int REQUEST_PERMISSION = 902;

  private boolean hasActivityCreatedCalled;
  private Snackbar snackbar;
  private ScanningPresenter scanningPresenter;

  private View.OnClickListener btAction = new View.OnClickListener() {
    @Override public void onClick(View v) {
      startActivityForResult(getBluetoothEnableIntent(), REQUEST_ENABLE_BLUETOOTH);
    }
  };

  private View.OnClickListener gpsAction = new View.OnClickListener() {
    @Override public void onClick(View v) {
      startActivityForResult(getLocationProviderIntent(), REQUEST_ENABLE_LOCATION_PROVIDER);
    }
  };

  private View.OnClickListener locationPermissionAction = new View.OnClickListener() {
    @Override public void onClick(View v) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        };
        if (getActivity().shouldShowRequestPermissionRationale(
            Manifest.permission.ACCESS_FINE_LOCATION)) {
          Debug.d(Debug.SCANNING_TAG, "should show permission rationale");
        }
        getActivity().requestPermissions(permissions, REQUEST_PERMISSION);
      }
    }
  };

  private Snackbar.Callback callback = new Snackbar.Callback() {
    @Override public void onDismissed(Snackbar snackbar, int event) {
      super.onDismissed(snackbar, event);
      onSnackBarDismissed(event);
    }
  };

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    if (scanningPresenter == null) {
      throw new NullPointerException(
          "Scanning presenter can not be NULL. Set it before calling super.onCreate of your scanningFragment.");
    }
    super.onCreate(savedInstanceState);
  }

  @Override public void setScanningPresenter(ScanningPresenter scanningPresenter) {
    this.scanningPresenter = scanningPresenter;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Debug.i(TAG.SCANNING, "onActivityCreated");
    hasActivityCreatedCalled = true;
    scanningPresenter.takeAction();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Debug.i(TAG.SCANNING, "onActivityResult");
    if (!hasActivityCreatedCalled) {
      scanningPresenter.takeAction();
    } else {
      Debug.i(TAG.SCANNING, "onActivityCreated has been just called before onActivityResult.");
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    Debug.i(TAG.SCANNING, "onDestroyView");
    scanningPresenter.unbindService();
  }

  private Intent getBluetoothEnableIntent() {
    Intent intent = new Intent();
    intent.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    return intent;
  }

  private Intent getLocationProviderIntent() {
    return new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
  }

  private void onSnackBarDismissed(int event) {
    switch (event) {
      case Snackbar.Callback.DISMISS_EVENT_SWIPE:
        Debug.i(TAG.SCANNING, "onSnackBarDismissed DISMISS_EVENT_SWIPE");
        scanningPresenter.preRequisitesRequirementNotFulfilled();
        break;
      case Snackbar.Callback.DISMISS_EVENT_ACTION:
        Debug.i(TAG.SCANNING, "onSnackBarDismissed DISMISS_EVENT_ACTION");
        break;
      case Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE:
        Debug.e(TAG.SCANNING, "onSnackBarDismissed DISMISS_EVENT_CONSECUTIVE");
        throw new UnsupportedOperationException(
            "Dismissal action on SnackBar is not supported " + event);
      case Snackbar.Callback.DISMISS_EVENT_TIMEOUT:
        Debug.e(TAG.SCANNING, "onSnackBarDismissed DISMISS_EVENT_TIMEOUT");
        throw new UnsupportedOperationException(
            "Dismissal action on SnackBar is not supported " + event);
      case Snackbar.Callback.DISMISS_EVENT_MANUAL:
        Debug.i(TAG.SCANNING, "onSnackBarDismissed DISMISS_EVENT_MANUAL");
        break;
      default:
        throw new UnsupportedOperationException(
            "Dismissal action on SnackBar is not supported " + event);
    }
  }

  @Override public void requestDialogFor(PreCondition preCondition) {
    showSnackBar(preCondition.getMessageString(), preCondition.getActionString(),
        getAction(preCondition));
  }

  private void showSnackBar(String messageText, String actionText, View.OnClickListener listener) {
    Debug.d(TAG.SCANNING, "showing snack bar for " + messageText);
    if (getView() != null) {
      snackbar = Snackbar.make(getView(), messageText, Snackbar.LENGTH_INDEFINITE);
      snackbar.setAction(actionText, listener);
      snackbar.setCallback(callback);
      snackbar.show();
    } else {
      throw new NullPointerException("Root View is NULL.");
    }
  }

  @Override public void onResume() {
    super.onResume();
    Debug.i(TAG.SCANNING, "onResume");
    hasActivityCreatedCalled = false;
  }

  @Override public void onPause() {
    super.onPause();
    Debug.i(TAG.SCANNING, "onPause");
    if (snackbar != null && snackbar.isShown()) {
      Debug.d(TAG.SCANNING, "dismissing snackbar");
      snackbar.dismiss();
    }
  }

  @Override public void onStop() {
    super.onStop();
    Debug.i(TAG.SCANNING, "onStop");
  }

  private View.OnClickListener getAction(PreCondition preCondition) {
    switch (preCondition) {
      case BT_NOT_ENABLED:
        return btAction;
      case GPS_PROVIDER_NOT_ENABLED_API_23_AND_ABOVE:
        return gpsAction;
      case LOCATION_PERMISSION_23_AND_ABOVE:
        return locationPermissionAction;
      default:
        throw new IllegalArgumentException("No action has been implemented");
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case REQUEST_PERMISSION:
        boolean permissionGranted = true;
        for (int result : grantResults) {
          if (result == PackageManager.PERMISSION_DENIED) {
            permissionGranted = false;
            break;
          }
        }
        if (permissionGranted) {
          Debug.d(TAG.SCANNING, "onRequestPermissionsResult PERMISSION_GRANTED");
          scanningPresenter.takeAction();
        } else {
          scanningPresenter.preRequisitesRequirementNotFulfilled();
        }
        break;
      default:
        throw new IllegalArgumentException("Request is not implemented");
    }
  }
}
