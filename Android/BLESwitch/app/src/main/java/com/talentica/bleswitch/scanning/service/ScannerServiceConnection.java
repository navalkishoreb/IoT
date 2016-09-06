package com.talentica.bleswitch.scanning.service;

		import android.content.Context;
		import android.content.ServiceConnection;

/**
 * Created by navalb on 17-06-2016.
 */

public interface ScannerServiceConnection extends ServiceConnection {
	void startScanning(Context context);

	void stopScanning();

	void unBindService(Context context);
}
