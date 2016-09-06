package com.talentica.blescanlib.scanning.service;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;

/**
 * Created by navalb on 16-06-2016.
 */

 final class PreConditionChecker implements Prerequisites {

	private final Context context;

	 PreConditionChecker(Context context) {
		this.context = context;
	}

	@Override
	public PreCondition check() {
		PreCondition preCondition = PreCondition.NO_PROBLEM;
    if(!isLocationPermissionEnabled()){
      preCondition = PreCondition.LOCATION_PERMISSION_23_AND_ABOVE;
    }else	if (!isBluetoothEnabled()) {
			preCondition = PreCondition.BT_NOT_ENABLED;
		} else if (!isGPSProviderEnabled()) {
			preCondition = PreCondition.GPS_PROVIDER_NOT_ENABLED_API_23_AND_ABOVE;
		}
		return preCondition;
	}

	private boolean isBluetoothEnabled() {
		return getBluetoothAdapter().isEnabled();
	}

	private boolean isGPSProviderEnabled() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
			return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		}
		return true;
	}

	private BluetoothAdapter getBluetoothAdapter() {
		BluetoothAdapter bluetoothAdapter;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
			bluetoothAdapter = manager.getAdapter();
		} else {
			bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		}
		return bluetoothAdapter;
	}

  private boolean isLocationPermissionEnabled(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      int permission = context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
      permission+= context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
      return permission >= 0;
    }
    return true;
  }
}
