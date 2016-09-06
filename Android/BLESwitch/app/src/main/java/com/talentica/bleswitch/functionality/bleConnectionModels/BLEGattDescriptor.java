package com.talentica.bleswitch.functionality.bleConnectionModels;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NavalB on 24-05-2016.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BLEGattDescriptor {
	private final BluetoothGattDescriptor bluetoothGattDescriptor;
	private final List<DescriptorPermission> descriptorPermissionList;

	public BLEGattDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor) {
		this.bluetoothGattDescriptor = bluetoothGattDescriptor;
		descriptorPermissionList = transformDescriptorPermissions();
	}

	private List<DescriptorPermission> transformDescriptorPermissions() {
		List<DescriptorPermission> descriptorPermissionList = new ArrayList<>();
		int descriptorPermission = bluetoothGattDescriptor.getPermissions();
		int mask = 0x01;
		int index = 0;
		DescriptorPermission[] array = DescriptorPermission.values();
		while (descriptorPermission != 0) {
			if ((descriptorPermission & mask) == mask) {
				descriptorPermissionList.add(array[index]);
			}
			descriptorPermission = descriptorPermission >>> 1;
			index++;
		}
		return descriptorPermissionList;
	}

	public String getDescriptorUUID() {
		return bluetoothGattDescriptor.getUuid().toString();
	}

	private int getAssignedNumberInteger() {
		return (int) (bluetoothGattDescriptor.getUuid().getMostSignificantBits() >>> 32);
	}

	public String getAssignedNumberHexString() {
		return Integer.toHexString(getAssignedNumberInteger()).toUpperCase();
	}

	public String getDescriptorSpecificationName() {
		return GattDescriptorSpecification.getSpecificationName(getAssignedNumberHexString());
	}

	public List<DescriptorPermission> getDescriptorPermissions() {
		return descriptorPermissionList;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\tSpecification Name: ").append(getDescriptorSpecificationName())
				.append("\n\t\t\tCharacteristic UUID: ").append(getDescriptorUUID())
				.append("\n\t\t\tAssigned Number Hex: ").append(getAssignedNumberHexString())
				.append("\n\t\t\t").append(getPermissionsString());
		return stringBuilder.toString();
	}


	private String getPermissionsString(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Permissions:[ ");
		for(DescriptorPermission characteristicPermission:getDescriptorPermissions()){
			stringBuilder.append(characteristicPermission.name()).append(",");
		}
		stringBuilder.replace(stringBuilder.length()-1,stringBuilder.length(),"]");
		return stringBuilder.toString();
	}
}
