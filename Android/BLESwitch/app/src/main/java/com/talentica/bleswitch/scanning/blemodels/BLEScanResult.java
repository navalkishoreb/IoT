package com.talentica.bleswitch.scanning.blemodels;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NavalB on 23-05-2016.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BLEScanResult implements Parcelable {
	public static final Creator<BLEScanResult> CREATOR = new Creator<BLEScanResult>() {
		@Override
		public BLEScanResult createFromParcel(Parcel in) {
			return new BLEScanResult(in);
		}

		@Override
		public BLEScanResult[] newArray(int size) {
			return new BLEScanResult[size];
		}
	};
	private final ScanResult scanResult;

	public BLEScanResult(ScanResult scanResult) {this.scanResult = scanResult;}

	private BLEScanResult(Parcel in) {
		scanResult = in.readParcelable(ScanResult.class.getClassLoader());
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("---------------").append("\nDevice Name: ").append(getDeviceName()).append("\nDevice Address: ").append(getDeviceAddress()).append("\nBond State: ").append(getBondState().getDescription()).append("\nDevice Type: ").append(getDeviceType().getType()).append("\nTx Power Level: ").append(getTxPowerLevel()).append("\nRSSI :").append(getRssi()).append("\n------------");
		return stringBuilder.toString();
	}

	public String getDeviceName() {
		if (scanResult.getDevice().getName() == null) {
			return "<unknown_device>";
		}
		return scanResult.getDevice().getName();
	}

	public String getDeviceAddress() {
		return scanResult.getDevice().getAddress();
	}

	public BondState getBondState() {
		int bondState = scanResult.getDevice().getBondState() - BluetoothDevice.BOND_NONE;
		return BondState.values()[bondState];
	}

	public DeviceType getDeviceType() {
		int deviceType = scanResult.getDevice().getType() - BluetoothDevice.DEVICE_TYPE_UNKNOWN;
		return DeviceType.values()[deviceType];
	}

	public int getTxPowerLevel() {
		if (scanResult.getScanRecord() == null) {
			return -1;
		}
		return scanResult.getScanRecord().getTxPowerLevel();
	}

	public BluetoothDevice getBluetoothDevice() {
		return scanResult.getDevice();
	}

	public int getRssi() {
		return scanResult.getRssi();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(scanResult, flags);
	}
}
