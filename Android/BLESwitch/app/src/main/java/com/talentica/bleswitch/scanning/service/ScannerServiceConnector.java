package com.talentica.bleswitch.scanning.service;

import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.talentica.bleswitch.base.Debug;

/**
 * Created by navalb on 16-06-2016.
 */

public class ScannerServiceConnector implements ScannerServiceConnection {
	private static final String TAG = ScannerServiceConnector.class.getSimpleName();
	private Messenger scanningServiceConnector;
	private final Messenger scanningDeviceScreen;

	public ScannerServiceConnector(Handler scanningDeviceHandler) {
		this.scanningDeviceScreen = new Messenger(scanningDeviceHandler);
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		if (service != null) {
			scanningServiceConnector = new Messenger(service);
			startScanning();
			Debug.d(TAG, "service connected");
		} else {
			throw new NullPointerException("Binder object is NULL");
		}
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		scanningServiceConnector = null;
		Debug.d(TAG, "service disConnected");
	}

	private void notify(Message message) {
		if (scanningServiceConnector != null) {
			try {
				scanningServiceConnector.send(message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			throw new NullPointerException("scanner connector is NULL");
		}
	}

	@Override
	public void startScanning(Context context) {
		if (scanningServiceConnector == null) {
			Debug.d(TAG, "binding BackgroundDeviceScan " + this.toString());
			BackgroundDeviceScanService.bind(context, this, scanningDeviceScreen);
			Debug.d(TAG, "all thread stopped");
		} else {
			startScanning();
			Debug.d(TAG, "already connected. cannot bind again. send message instead.");
		}
	}

	@Override
	public void unBindService(Context context) {
		if (scanningServiceConnector != null) {
			context.unbindService(this);
			scanningServiceConnector = null;
		} else {
			Debug.d(TAG, "no connections");
		}
	}

	private Message createMessage(int action) {
		Message message = Message.obtain();
		message.what = action;
		message.replyTo = scanningDeviceScreen;
		return message;
	}

	private void startScanning() {
		notify(createMessage(BackgroundDeviceScan.START_SCAN));
	}

	@Override
	public void stopScanning() {
		notify(createMessage(BackgroundDeviceScan.STOP_SCAN));
	}
}
