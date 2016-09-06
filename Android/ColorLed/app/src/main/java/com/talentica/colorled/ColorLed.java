package com.talentica.colorled;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.talentica.blescanlib.base.BaseActivity;
import com.talentica.blescanlib.base.Debug;
import com.talentica.blescanlib.scanning.BLEFilterCriteria;
import com.talentica.blescanlib.scanning.bleConnectionModels.BLEGattCharacteristic;
import com.talentica.blescanlib.scanning.bleConnectionModels.BLEGattService;
import com.talentica.blescanlib.scanning.bleConnectionModels.GattCallback;
import com.talentica.blescanlib.scanning.bleConnectionModels.GattConnection;
import com.talentica.blescanlib.scanning.blemodels.BLEScanResult;
import com.talentica.blescanlib.scanning.blemodels.BondState;
import com.talentica.blescanlib.scanning.service.ScanningView;

import java.lang.reflect.Method;

public class ColorLed extends BaseActivity implements SeekBar.OnSeekBarChangeListener, GattConnection {

	private static final String TAG = ColorLed.class.getSimpleName();

	private BLEScanResult bleScanResult;
	private BluetoothGattCallback callback;
	private BluetoothGatt bluetoothGatt;
	private BLEGattService bleGattService;
	private BluetoothGattCharacteristic bluetoothGattCharacteristic;
	private BluetoothSocket bluetoothSocket;
	private BluetoothDevice bluetoothDevice;
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
		bleScanResult = getIntent().getParcelableExtra(ScanningView.EXTRA_DEVICE_DATA);
		if (bleScanResult == null) {
			throw new NullPointerException("BleScanResult is NULL.");
		}
		callback = new GattCallback(this);
		findViews();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Debug.d(TAG, "onStart");
		setConfirmationKey();
		createBond();
		registerReceiver(mPairReceiver, getIntentFilter());
	}

	private IntentFilter getIntentFilter() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		intentFilter.addAction(BluetoothDevice.ACTION_UUID);
		return intentFilter;
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
		Debug.d(TAG, "showLoading");
		progressBar.setVisibility(View.VISIBLE);
	}

	private void hideLoading() {
		progressBar.setVisibility(View.GONE);
	}

	private void disableContent() {
		Debug.d(TAG, "disableContent");
		redSeekBar.setEnabled(false);
		greenSeekBar.setEnabled(false);
		blueSeekBar.setEnabled(false);
		colorPlate.setEnabled(false);
	}

	private void enableContent() {
		Debug.d(TAG, "enableContent");
		redSeekBar.setEnabled(true);
		greenSeekBar.setEnabled(true);
		blueSeekBar.setEnabled(true);
		colorPlate.setEnabled(true);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	private void connectGATT() {
		Debug.d(TAG, "connectGATT");
		if (bluetoothGatt == null) {
			disableContent();
			showLoading();
			Debug.d(TAG, "creating gatt object");
			bluetoothGatt = bleScanResult.getBluetoothDevice().connectGatt(this, false, callback);
		} else {
			Debug.d(TAG, "gatt object already present");
			bluetoothGatt.connect();
		}
	}


	private void setConfirmationKey(){
		boolean flag = bleScanResult.getBluetoothDevice().setPairingConfirmation(true);
		Debug.d(TAG, "setPairingConfirmation?="+flag);
		if(!flag){
//			unpairDevice(bleScanResult.getBluetoothDevice());
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		Debug.d(TAG,"onStop");
		unpairDevice(bleScanResult.getBluetoothDevice());
		closeBluetoothGatt();
		unregisterReceiver(mPairReceiver);
	}
	private void closeBluetoothGatt(){
		if (bluetoothGatt != null) {
			Debug.d(TAG, "disconnecting gatt");
			bluetoothGatt.disconnect();
			bluetoothGatt.close();
			bluetoothGatt = null;
		}else{
			Log.e(TAG,"bluetoothGatt is NULL");
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
		Debug.d(TAG, "writing color " + color + " #" + Integer.toHexString(color));
//		bluetoothGattCharacteristic.setValue(color, BluetoothGattCharacteristic.FORMAT_SINT32, 0);
		bluetoothGattCharacteristic.setValue(intToByteArray(color));
		Debug.d(TAG, "writing characteristic successful?=" + bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic));
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	private void readColor() {
		Debug.d(TAG,"reading color?="+bluetoothGatt.readCharacteristic(bluetoothGattCharacteristic));
	}

	public static final byte[] intToByteArray(int value) {
		return new byte[]{(byte) (value >>> 16), (byte) (value >>> 8), (byte) value};
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
		Debug.d(TAG,"fetchCharacteristic");
//		bleGattService.getBleGattCharacteristics().get(0).getBleGattDescriptorList().get(0).
		for (BLEGattCharacteristic characteristic : bleGattService.getBleGattCharacteristics()) {
			if (characteristic.getCharacteristicUUID().equals(BLEFilterCriteria.CHARACTERISTIC_UUID)) {
				bluetoothGattCharacteristic = characteristic.getBluetoothGattCharacteristic();
//				BluetoothGattCharacteristic temp = characteristic.getBluetoothGattCharacteristic();
//				int properties = BluetoothGattCharacteristic.PROPERTY_SIGNED_WRITE|BluetoothGattCharacteristic.PROPERTY_READ|BluetoothGattCharacteristic.PROPERTY_NOTIFY;
//				int permissions = BluetoothGattCharacteristic.PERMISSION_WRITE_SIGNED | BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED;
//				bluetoothGattCharacteristic= new BluetoothGattCharacteristic(temp.getUuid(),properties,permissions);
//				Debug.d(TAG,"setting write type for characteristics");
//				bluetoothGattCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_SIGNED);
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
		Debug.d(TAG, "loadServices");
		fetchCharacteristic();
		readColor();
//		if (!readColor()) {
//			hideLoading();
//			enableContent();
//		}else{
//			Debug.d(TAG,"not able to read color");
//		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@Override
	public void onConnected() {
		Debug.d(TAG,"gatt connection connected");
		bluetoothGatt.discoverServices();
	}

	private void createBond() {
		Debug.d(TAG, "bonding device");
		disableContent();
		showLoading();
//		Debug.d(TAG, "device creating bond?=" + bleScanResult.getBluetoothDevice().createBond());
		if (bleScanResult.getBondState()== BondState.BOND_NONE) {
			Debug.d(TAG, "device creating bond?=" + bleScanResult.getBluetoothDevice().createBond());
		} else {
			Debug.d(TAG, bleScanResult.getBondState().getDescription());
			afterBonding();
		}
	}

	private void unpairDevice(BluetoothDevice device) {
		Debug.d(TAG,"trying to unpair device using reflection");
		try {
			Method m = device.getClass().getMethod("removeBond", (Class[]) null);
			m.invoke(device, (Object[]) null);
		} catch (Exception e) { Log.e(TAG, e.getMessage()); }
	}
	private void afterBonding(){
		closeBluetoothGatt();
		Debug.d(TAG,"re-connecting gatt");
		connectGATT();
	}

	@Override
	public void onDisconnected() {
		Debug.d(TAG,"gatt connection disconnected");
		hideLoading();
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
		Debug.d(TAG, "callback write successfully");
	}

	@Override
	public void onFailureCharacteristicWrite() {
		Debug.e(TAG, "callback write failed");
	}

	@Override
	public void onFailureCharacteristicRead() {
		hideLoading();
		enableContent();
	}

	private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Debug.d(TAG, "called for " + action);
			if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
				final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
				Debug.d(TAG, "current_state " + state);
				final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);
				Debug.d(TAG, "previous_state " + prevState);
				if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
					Debug.d(TAG, "Paired");
					afterBonding();
				} else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {
					Debug.d(TAG, "Unpaired");
					disableContent();
					hideLoading();
				}else if(state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDING){
					Debug.d(TAG, "Bonding Broke!!");
					disableContent();
					hideLoading();
				}else if(state == BluetoothDevice.BOND_BONDING && prevState == BluetoothDevice.BOND_NONE){
					Debug.d(TAG, "Starting to Bond..");
					disableContent();
					hideLoading();
				}else{
					Debug.e(TAG, "yeah Bond.. James Bond!!");
				}
			}else if(BluetoothDevice.ACTION_UUID.equals(action)){
				bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

			}
		}
	};
}
