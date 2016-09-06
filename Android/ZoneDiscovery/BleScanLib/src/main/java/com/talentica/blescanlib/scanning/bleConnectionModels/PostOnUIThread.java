package com.talentica.blescanlib.scanning.bleConnectionModels;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by NavalB on 25-05-2016.
 */
public class PostOnUIThread implements GattConnection {
	private final GattConnection originalCallback;
	private final Handler uiHandler;

	PostOnUIThread(GattConnection originalCallback) {
		this.originalCallback = originalCallback;
		uiHandler = new Handler(Looper.getMainLooper());
	}

	@Override
	public void loadServices(final BLEGattService gattService) {
		uiHandler.post(new Runnable() {
			@Override
			public void run() {
				originalCallback.loadServices(gattService);
			}
		});
	}

	@Override
	public void onConnected() {
		uiHandler.post(new Runnable() {
			@Override
			public void run() {
				originalCallback.onConnected();
			}
		});
	}

	@Override
	public void onDisconnected() {
		uiHandler.post(new Runnable() {
			@Override
			public void run() {
				originalCallback.onDisconnected();
			}
		});
	}

	@Override
	public void onSuccessfulCharacteristicRead() {
		uiHandler.post(new Runnable() {
			@Override
			public void run() {
				originalCallback.onSuccessfulCharacteristicRead();
			}
		});
	}

	@Override
	public void onSuccessfulCharacteristicWrite() {
		uiHandler.post(new Runnable() {
			@Override
			public void run() {
				originalCallback.onSuccessfulCharacteristicWrite();
			}
		});
	}

	@Override
	public void onFailureCharacteristicWrite() {
		uiHandler.post(new Runnable() {
			@Override
			public void run() {
				originalCallback.onFailureCharacteristicWrite();
			}
		});
	}

	@Override
	public void onFailureCharacteristicRead() {
		uiHandler.post(new Runnable() {
			@Override
			public void run() {
				originalCallback.onFailureCharacteristicRead();
			}
		});
	}
}
