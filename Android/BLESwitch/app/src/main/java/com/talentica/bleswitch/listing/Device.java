package com.talentica.bleswitch.listing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.talentica.bleswitch.R;
import com.talentica.bleswitch.base.BaseActivity;
import com.talentica.bleswitch.functionality.ColorLed;
import com.talentica.bleswitch.scanning.blemodels.BLEScanResult;

public class Device extends BaseActivity implements View.OnClickListener {

	private static final String EXTRA_DEVICE_DATA = "result_data";

	private BLEScanResult bleScanResult;

	private TextView deviceName;
	private TextView deviceAddress;
	private TextView bondState;
	private TextView deviceType;
	private TextView txLevel;
	private TextView rssi;

	public static void launch(Context context, BLEScanResult bleScanResult) {
		Intent intent = new Intent(context, Device.class);
		intent.putExtra(EXTRA_DEVICE_DATA, bleScanResult);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device);
		bleScanResult = getIntent().getParcelableExtra(EXTRA_DEVICE_DATA);
		if (bleScanResult == null) {
			throw new NullPointerException("BleScanResult is NULL.");
		}
		findViews();
		setViews(bleScanResult);
	}

	private void setViews(BLEScanResult bleScanResult) {
		deviceName.setText(bleScanResult.getDeviceName());
		deviceAddress.setText(bleScanResult.getDeviceAddress());
		bondState.setText(bleScanResult.getBondState().getDescription());
		deviceType.setText(bleScanResult.getDeviceType().getType());
		txLevel.setText("TxLevel " + String.valueOf(bleScanResult.getTxPowerLevel()));
		rssi.setText(String.valueOf(bleScanResult.getRssi()));
	}
	
	private void findViews() {
		deviceName = (TextView) findViewById(R.id.item_ble_devices_device_name);
		deviceAddress = (TextView) findViewById(R.id.item_ble_devices_device_address);
		bondState = (TextView) findViewById(R.id.item_ble_devices_bond_state);
		deviceType = (TextView) findViewById(R.id.item_ble_devices_device_type);
		txLevel = (TextView) findViewById(R.id.item_ble_devices_tx_level);
		rssi = (TextView) findViewById(R.id.item_ble_devices_rssi);
		Button connectToGatt = (Button) findViewById(R.id.item_ble_devices_connect_gatt);
		if (connectToGatt != null) {
			connectToGatt.setOnClickListener(this);
		} else {
			throw new NullPointerException("No Connect Button view available");
		}
	}

	@Override
	public void onClick(View v) {
		ColorLed.launch(this, bleScanResult);
	}
}
