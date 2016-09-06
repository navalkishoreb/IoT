package com.talentica.bleswitch.functionality;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.talentica.bleswitch.R;
import com.talentica.bleswitch.base.BaseActivity;
import com.talentica.bleswitch.base.Debug;
import com.talentica.bleswitch.functionality.bleConnectionModels.BLEGattCharacteristic;
import com.talentica.bleswitch.functionality.bleConnectionModels.BLEGattService;
import com.talentica.bleswitch.functionality.bleConnectionModels.GattCallback;
import com.talentica.bleswitch.scanning.BLEFilterCriteria;
import com.talentica.bleswitch.scanning.blemodels.BLEScanResult;

public class ColorLed extends BaseActivity implements SeekBar.OnSeekBarChangeListener, GattConnection {

	private static final String EXTRA_DEVICE_DATA = "result_data";
	private static final String TAG = ColorLed.class.getSimpleName();

	public static void launch(Context context, BLEScanResult bleScanResult) {
		Intent intent = new Intent(context, ColorLed.class);
		intent.putExtra(EXTRA_DEVICE_DATA, bleScanResult);
		context.startActivity(intent);
	}

	private BLEScanResult bleScanResult;
	private BluetoothGattCallback callback;
	private BluetoothGatt bluetoothGatt;
	private BLEGattService bleGattService;
	private BluetoothGattCharacteristic bluetoothGattCharacteristic;

	private int red;
	private int green;
	private int blue;

	private TextView redValue;
	private TextView greenValue;
	private TextView blueValue;

	private SeekBar redSeekBar;
	private SeekBar greenSeekBar;
	private SeekBar blueSeekBar;

	private View colorPlate;

	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_led);
		bleScanResult = getIntent().getParcelableExtra(EXTRA_DEVICE_DATA);
		if (bleScanResult == null) {
			throw new NullPointerException("BleScanResult is NULL.");
		}
		callback = new GattCallback(this);
		findViews();
	}

	@Override
	protected void onStart() {
		super.onStart();
		connectGATT();
	}

	private void findViews() {
		redValue = (TextView) findViewById(R.id.color_value_red);
		greenValue = (TextView) findViewById(R.id.color_value_green);
		blueValue = (TextView) findViewById(R.id.color_value_blue);
		redSeekBar = (SeekBar) findViewById(R.id.seek_bar_red);
		greenSeekBar = (SeekBar) findViewById(R.id.seek_bar_green);
		blueSeekBar = (SeekBar) findViewById(R.id.seek_bar_blue);
		colorPlate = findViewById(R.id.color_plate);
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);
		redSeekBar.setOnSeekBarChangeListener(this);
		greenSeekBar.setOnSeekBarChangeListener(this);
		blueSeekBar.setOnSeekBarChangeListener(this);
	}

	private void showLoading() {
		progressBar.setVisibility(View.VISIBLE);
	}

	private void hideLoading() {
		progressBar.setVisibility(View.GONE);
	}

	private void disableContent() {
		redSeekBar.setEnabled(false);
		greenSeekBar.setEnabled(false);
		blueSeekBar.setEnabled(false);
	}

	private void enableContent() {
		redSeekBar.setEnabled(true);
		greenSeekBar.setEnabled(true);
		blueSeekBar.setEnabled(true);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	private void connectGATT() {
		if (bluetoothGatt == null) {
			disableContent();
			showLoading();
			bluetoothGatt = bleScanResult.getBluetoothDevice().connectGatt(this, true, callback);
		} else {
			bluetoothGatt.connect();
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		setColorValue(seekBar.getId(), progress);
		int color = getColor();
		setColorPlate(color);
		if (fromUser) {
			writeColor(color);
		}
	}

	int getColor() {
		Debug.d(TAG, "#" + Integer.toHexString(Color.rgb(red, green, blue)));
		return Color.rgb(red, green, blue);
	}

	private void setColorPlate(int color) {
		colorPlate.setBackgroundColor(color);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	private void writeColor(int color) {
		Debug.d(TAG,"writing color "+color + " #"+Integer.toHexString(color));
//		bluetoothGattCharacteristic.setValue(color, BluetoothGattCharacteristic.FORMAT_SINT32, 0);
		bluetoothGattCharacteristic.setValue(intToByteArray(color));
		Debug.d(TAG,"writing characteristic successful?="+bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic));
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	private boolean readColor() {
		return bluetoothGatt.readCharacteristic(bluetoothGattCharacteristic);
	}

	public static final byte[] intToByteArray(int value) {
		return new byte[] {
				(byte)(value >>> 16),
				(byte)(value >>> 8),
				(byte)value};
	}

	private void setColorValue(int id, int progress) {
		switch (id) {
			case R.id.seek_bar_red:
				this.red = progress;
				redValue.setText(String.valueOf(progress));
				break;
			case R.id.seek_bar_green:
				this.green = progress;
				greenValue.setText(String.valueOf(progress));
				break;
			case R.id.seek_bar_blue:
				this.blue = progress;
				blueValue.setText(String.valueOf(progress));
				break;
			default:
				throw new IllegalArgumentException("No such seek bar implemented");
		}
	}

	private void fetchCharacteristic() {
		bluetoothGattCharacteristic = null;
		for (BLEGattCharacteristic characteristic : bleGattService.getBleGattCharacteristics()) {
			if (characteristic.getCharacteristicUUID().equals(BLEFilterCriteria.CHARACTERISTIC_UUID)) {
				bluetoothGattCharacteristic = characteristic.getBluetoothGattCharacteristic();
				break;
			}
			if (bluetoothGattCharacteristic == null) {
				throw new NullPointerException("Desired characteristic no found");
			}
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
//		writeColor(getColor());
	}

	@Override
	public void loadServices(BLEGattService gattService) {
		bleGattService = gattService;
		fetchCharacteristic();
		if (!readColor()) {
			hideLoading();
			enableContent();
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@Override
	public void onConnected() {
		bluetoothGatt.discoverServices();
	}

	@Override
	public void onDisconnected() {
		disableContent();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@Override
	public void onSuccessfulCharacteristicRead() {
		Integer color = bluetoothGattCharacteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT32, 0);
		if (color == null) {
			color = 0;
		}
		setColor(color);
	}

	private void setColor(int color) {
		red = 0x00ff0000 & color;
		green = 0x0000ff00 & color;
		blue = 0x000000ff & color;
		redSeekBar.setProgress(red);
		greenSeekBar.setProgress(green);
		blueSeekBar.setProgress(blue);
		setColorPlate(getColor());
		hideLoading();
		enableContent();
	}

	@Override
	public void onSuccessfulCharacteristicWrite() {
		Debug.d(TAG,"callback write successfully");

	}

	@Override
	public void onFailureCharacteristicWrite() {
		Debug.e(TAG,"callback write failed");
	}

	@Override
	public void onFailureCharacteristicRead() {
		hideLoading();
		enableContent();
	}
}
