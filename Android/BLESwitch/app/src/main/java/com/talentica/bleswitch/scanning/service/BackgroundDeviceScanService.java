package com.talentica.bleswitch.scanning.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;

import com.talentica.bleswitch.base.Debug;
import com.talentica.bleswitch.scanning.blemodels.BTConnectionState;
import com.talentica.bleswitch.scanning.blemodels.BTState;

public class BackgroundDeviceScanService extends Service implements BackgroundDeviceScan{
	private final static String TAG = "Service";
	private final static String REPLY_TO = "reply_to";


	private BTState btState;

	public static void bind(Context context, ServiceConnection serviceConnection, Messenger replyTo) {
		Intent intent = new Intent(context, BackgroundDeviceScanService.class);
		intent.putExtra(REPLY_TO, replyTo);
		context.bindService(intent, serviceConnection, BIND_AUTO_CREATE);
	}

/*	public static void startService(Context context) {
		Intent intent = new Intent(context, BackgroundDeviceScanService.class);
		context.startService(intent);
	}
	public static void stopService(Context context) {
		Intent intent = new Intent(context, BackgroundDeviceScanService.class);
		context.stopService(intent);
	}*/

	private NotifyScanningView notifyScanningView;
	private Scanner scanner;
	private BroadcastReceiver receiver;
	private Handler uiHandler;
	private Runnable autoStop = new Runnable() {
		@Override
		public void run() {
			stopScanning();
		}
	};

	public BackgroundDeviceScanService() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Debug.d(TAG, "service is created");
		notifyScanningView = new NotifyScanningViewHandler(this);
		scanner = new Scan(getBluetoothAdapter(), notifyScanningView);
		receiver = new BluetoothChangeReceiver(this);
		uiHandler = new Handler(Looper.getMainLooper());
		btState = BTState.fromValue(getBluetoothAdapter().getState());
		registerReceiver(receiver, getIntentFilter());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		handleIntent(intent);
		Debug.d(TAG, "trying to bind service");
		return new Messenger(notifyScanningView.getHandler()).getBinder();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Debug.d(TAG, "unbinding service");
		return false;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHandler.removeCallbacks(autoStop);
		unregisterReceiver(receiver);
		Debug.d(TAG, "service destroyed");
	}

	private BluetoothAdapter getBluetoothAdapter() {
		BluetoothAdapter bluetoothAdapter;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
			bluetoothAdapter = manager.getAdapter();
		} else {
			bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		}
		return bluetoothAdapter;
	}
	private IntentFilter getIntentFilter() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
		intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
//		intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		intentFilter.addAction(BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED);
		return intentFilter;
	}

	private void handleIntent(Intent intent) {
		if (intent != null) {
			Messenger messenger = intent.getParcelableExtra(REPLY_TO);
			notifyScanningView.setReplyTo(messenger);
		} else {
			throw new NullPointerException("Intent received is NULL");
		}
	}

	@Override
	public void startScanning() {
		Debug.i(TAG, "startScanning");
		if(btState != BTState.STATE_ON){
			throw new IllegalStateException("Bluetooth is not On. Cannot scan for devices");
		}
		scanner.start();
//		uiHandler.postDelayed(autoStop, 5000);
	}

	@Override
	public void stopScanning() {
		Debug.i(TAG, "stopScanning");
		if(btState != BTState.STATE_ON){
			throw new IllegalStateException("Bluetooth is not On. Cannot stop scan");
		}
		scanner.stop();
		notifyScanningView.notify(ScanningViewAction.SHOW_SEARCH_SCREEN);
	}

	@Override
	public void onConnectionStateChanged(BTConnectionState currentConnState, BTConnectionState previousConnState) {
		Debug.i(TAG, "currentConnState " + currentConnState.name() + " previousConnState " + previousConnState.name());
		switch (currentConnState) {
			case STATE_CONNECTING:
				notifyScanningView.notify(ScanningViewAction.ATTEMPTING_TO_CONNECT_PROFILE);
				break;
			case STATE_DISCONNECTED:
				notifyScanningView.notify(ScanningViewAction.NOT_CONNECTED_TO_ANY_PROFILE);
				break;
		}
	}

	@Override
	public void onStateChanged(BTState currentState, BTState previousState) {
		Debug.i(TAG, "currentState " + currentState.name() + " previousState " + previousState.name());
		btState = currentState;
		if (currentState == BTState.STATE_OFF || currentState == BTState.STATE_TURNING_OFF) {
			notifyScanningView.notify(ScanningViewAction.SHOW_SEARCH_SCREEN);
		}
	}

	@Override
	public void discoveryFinished() {
		Debug.i(TAG, "Discovery Finished");
	}

	@Override
	public void discoveryStarted() {
		Debug.i(TAG, "Discovery Started");
	}
}
