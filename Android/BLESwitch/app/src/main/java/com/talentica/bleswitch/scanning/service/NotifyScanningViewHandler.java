package com.talentica.bleswitch.scanning.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.talentica.bleswitch.scanning.ScanningDevices;
import com.talentica.bleswitch.scanning.blemodels.BLEScanResult;

/**
 * Created by navalb on 16-06-2016.
 */
public class NotifyScanningViewHandler extends Handler implements NotifyScanningView {
	private final BackgroundDeviceScan backgroundDeviceScan;
	private Messenger scanningView;

	public NotifyScanningViewHandler(BackgroundDeviceScan backgroundDeviceScan) {
		this.backgroundDeviceScan = backgroundDeviceScan;
	}

	@Override
	public void setReplyTo(Messenger messenger) {
		if (messenger != null) {
			this.scanningView = messenger;
		} else {
			throw new NullPointerException("ReplyTo messenger object is NULL");
		}
	}

	@Override
	public Handler getHandler() {
		return this;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		setReplyTo(msg.replyTo);
		switch (msg.what) {
			case BackgroundDeviceScan.START_SCAN:
				backgroundDeviceScan.startScanning();
				break;
			case BackgroundDeviceScan.STOP_SCAN:
				backgroundDeviceScan.stopScanning();
				break;
			default:
				throw new IllegalArgumentException("No such msg was configured.");
		}
	}

	private void notify(Message message) {
		if (scanningView != null) {
			try {
				scanningView.send(message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			throw new NullPointerException("scanning screen messenger is NULL.");
		}
	}

	private Message createMessage(BLEScanResult result) {
		Message message = createMessage(ScanningViewAction.SCAN_RESULT.getAction());
		Bundle bundle = new Bundle();
		bundle.putParcelable(ScanningDevices.EXTRA_SCAN_RESULT, result);
		message.setData(bundle);
		return message;
	}

	private Message createMessage(int action) {
		Message message = Message.obtain();
		message.what = action;
		return message;
	}

	@Override
	public void notify(ScanningViewAction action) {
		notify(createMessage(action.getAction()));
	}

	@Override
	public void notify(BLEScanResult result) {
		notify(createMessage(result));
	}
}
