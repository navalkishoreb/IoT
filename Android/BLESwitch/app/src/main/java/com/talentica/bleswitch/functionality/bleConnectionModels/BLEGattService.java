package com.talentica.bleswitch.functionality.bleConnectionModels;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NavalB on 24-05-2016.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BLEGattService {
	private final static String TAG = BLEGattService.class.getSimpleName();
	private final BluetoothGattService bluetoothGattService;
	private final List<BLEGattCharacteristic> bleGattCharacteristics;

	public BLEGattService(BluetoothGattService bluetoothGattService) {
		this.bluetoothGattService = bluetoothGattService;
		bleGattCharacteristics = transformBluetoothCharacteristic();
	}

	private List<BLEGattCharacteristic> transformBluetoothCharacteristic() {
		List<BLEGattCharacteristic> bleGattCharacteristicList = new ArrayList<>();
		for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService.getCharacteristics()) {
			bleGattCharacteristicList.add(new BLEGattCharacteristic(bluetoothGattCharacteristic));
		}
		return bleGattCharacteristicList;
	}

	public String getServiceUUID() {
		return this.bluetoothGattService.getUuid().toString();
	}

	public ServiceType getServiceType() {
		switch (bluetoothGattService.getType()) {
			case BluetoothGattService.SERVICE_TYPE_PRIMARY:
				return ServiceType.PRIMARY;
			case BluetoothGattService.SERVICE_TYPE_SECONDARY:
				return ServiceType.SECONDARY;
			default:
				return null;
		}
	}

	public List<BLEGattCharacteristic> getBleGattCharacteristics() {
		return bleGattCharacteristics;
	}

	private int getAssignedNumberInteger() {
		return (int) (bluetoothGattService.getUuid().getMostSignificantBits() >>> 32);
	}

	public String getAssignedNumberHexString(){
		return Integer.toHexString(getAssignedNumberInteger()).toUpperCase();
	}

	public String getServiceSpecificationName() {
		return GattServiceSpecification.getSpecificationName(getAssignedNumberHexString());
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("Service Specification Name: ").append(getServiceSpecificationName())
				.append("\nService UUID: ").append(getServiceUUID())
				.append("\nAssigned Hex Number: ").append(getAssignedNumberHexString())
				.append("\nService Type: ").append(getServiceType().name())
				.append("\nCharacteristics Count: ").append(getBleGattCharacteristics().size())
				.append("").append(getAllCharacteristicsString());

		return stringBuilder.toString();
	}


	private String getAllCharacteristicsString(){
		StringBuilder stringBuilder = new StringBuilder();
		for(BLEGattCharacteristic characteristic:getBleGattCharacteristics()){
			stringBuilder.append("\n+").append(characteristic.toString());
		}
		return stringBuilder.toString();
	}
}
