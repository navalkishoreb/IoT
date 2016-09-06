package com.talentica.bleswitch.scanning;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.talentica.bleswitch.R;
import com.talentica.bleswitch.base.Debug;
import com.talentica.bleswitch.base.LoadingTextFragment;
import com.talentica.bleswitch.listing.Device;
import com.talentica.bleswitch.scanning.blemodels.BLEScanResult;
import com.talentica.bleswitch.scanning.service.ScannerServiceConnection;
import com.talentica.bleswitch.scanning.service.ScannerServiceConnector;
import com.talentica.bleswitch.scanning.service.ScanningDevicesHandler;
import com.talentica.bleswitch.scanning.service.ScanningView;

import static android.support.design.widget.Snackbar.Callback.DISMISS_EVENT_ACTION;
import static android.support.design.widget.Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE;
import static android.support.design.widget.Snackbar.Callback.DISMISS_EVENT_MANUAL;
import static android.support.design.widget.Snackbar.Callback.DISMISS_EVENT_SWIPE;
import static android.support.design.widget.Snackbar.Callback.DISMISS_EVENT_TIMEOUT;
import static android.support.design.widget.Snackbar.LENGTH_INDEFINITE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanningDevices extends LoadingTextFragment implements ScanningView {
	private static final String TAG = "Scanner";
	private static final int REQUEST_ENABLE_BLUETOOTH = 902;
	private static final int REQUEST_ENABLE_LOCATION_PROVIDER = 903;

	private Prerequisites prerequisites;
	private Interaction interaction;
	private ScannerServiceConnection scannerServiceConnection;

	private View.OnClickListener btAction = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			startActivityForResult(getBluetoothEnableIntent(), REQUEST_ENABLE_BLUETOOTH);
		}
	};

	private View.OnClickListener gpsAction = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			startActivityForResult(getLocationProviderIntent(), REQUEST_ENABLE_LOCATION_PROVIDER);
		}
	};
	private Snackbar.Callback callback = new Snackbar.Callback() {
		@Override
		public void onDismissed(Snackbar snackbar, int event) {
			super.onDismissed(snackbar, event);
			onSnackBarDismissed(event);
		}
	};

	public ScanningDevices() {
		// Required empty public constructor
	}

	interface Interaction {
		void showSearchScreen();
	}

	public static Fragment newInstance() {
		return new ScanningDevices();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof Interaction) {
			interaction = (Interaction) activity;
		} else {
			throw new IllegalArgumentException("Activity need to implement showSearchScreen interface.");
		}
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prerequisites = new PreConditionChecker(getContext());
		scannerServiceConnection = new ScannerServiceConnector(new ScanningDevicesHandler(this));
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		startScanningUIUpdate();
		takeAction(prerequisites.check());
	}

	@Override
	public void onStop() {
		super.onStop();
		Debug.d(TAG, "onStop");
		unbindService();
	}

	private void takeAction(PreCondition check) {
		if (check != null) {
			switch (check) {
				case BT_NOT_ENABLED:
					requestEnableBluetooth();
					break;
				case GPS_PROVIDER_NOT_ENABLED_API_23_AND_ABOVE:
					launchGPSProvider();
					break;
				case NO_PROBLEM:
					startScanningService();
					break;
				default:
					throw new IllegalArgumentException("No action provided for precondition " + check.name());
			}
		} else {
			throw new NullPointerException("prerequisites check cannot return NULL.");
		}
	}

	private void startScanningService() {
		startScanningUIUpdate();
		scannerServiceConnection.startScanning(getContext());
	}

	private void stopScanningService() {
		stopScanningUIUpdate();
		scannerServiceConnection.stopScanning();
	}
	private void unbindService(){
		scannerServiceConnection.unBindService(getContext());
	}

	private void launchGPSProvider() {
		stopScanningUIUpdate();
		showSnackBar(PreCondition.GPS_PROVIDER_NOT_ENABLED_API_23_AND_ABOVE.getMessageString(), getString(R.string.enable), gpsAction);
	}

	private void requestEnableBluetooth() {
		stopScanningUIUpdate();
		showSnackBar(PreCondition.BT_NOT_ENABLED.getMessageString(), getString(R.string.enable), btAction);
	}

	private void showSnackBar(String messageText, String actionText, View.OnClickListener listener) {
		if (getView() != null) {
			Snackbar snackbar = Snackbar.make(getView(), messageText, LENGTH_INDEFINITE);
			snackbar.setAction(actionText, listener);
			snackbar.setCallback(callback);
			snackbar.show();
		} else {
			throw new NullPointerException("Root View is NULL.");
		}
	}

	private void stopScanningUIUpdate() {
		hideLoading();
		setLoadingText(getString(R.string.scanning_stopped));
	}

	private void startScanningUIUpdate() {
		showLoading();
		setLoadingText(getString(R.string.scanning_devices));
	}

	@Override
	public void updateUIAttemptingConnect() {
		setLoadingText(getString(R.string.attempting_connection));
	}

	private Intent getBluetoothEnableIntent() {
		Intent intent = new Intent();
		intent.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		return intent;
	}

	private Intent getLocationProviderIntent() {
		return new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		takeAction(prerequisites.check());
	}

	private void onSnackBarDismissed(int event) {
		switch (event) {
			case DISMISS_EVENT_SWIPE:
				showSearchScreen();
				break;
			case DISMISS_EVENT_ACTION:
				break;
			case DISMISS_EVENT_CONSECUTIVE:
			case DISMISS_EVENT_TIMEOUT:
			case DISMISS_EVENT_MANUAL:
			default:
				throw new UnsupportedOperationException("Dismissal action on Snackbar is not supported " + event);
		}
	}

	@Override
	public void showSearchScreen() {
		interaction.showSearchScreen();
	}

	@Override
	public void addDevice(BLEScanResult bleScanResult) {
		stopScanningService();
		Device.launch(getContext(),bleScanResult);
	}

}
