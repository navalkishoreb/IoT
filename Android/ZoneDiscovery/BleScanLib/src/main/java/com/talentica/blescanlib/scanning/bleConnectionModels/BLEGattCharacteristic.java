package com.talentica.blescanlib.scanning.bleConnectionModels;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NavalB on 24-05-2016.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BLEGattCharacteristic {
	private final BluetoothGattCharacteristic bluetoothGattCharacteristic;
	private final List<BLEGattDescriptor> bleGattDescriptorList;
	private final List<CharacteristicPermission> characteristicPermissionList;
	private final List<CharacteristicProperty> characteristicPropertyList;
	private final List<CharacteristicWriteType> characteristicWriteTypeList;

	public BLEGattCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
		this.bluetoothGattCharacteristic = bluetoothGattCharacteristic;
		bleGattDescriptorList = transformDescriptorList();
		characteristicPermissionList = transformCharacteristicPermissions();
		characteristicPropertyList = transformCharacteristicProperties();
		characteristicWriteTypeList = transformCharacteristicWriteTypes();

	}
	public BluetoothGattCharacteristic getBluetoothGattCharacteristic(){
		return bluetoothGattCharacteristic;
	}

	private List<BLEGattDescriptor> transformDescriptorList() {
		List<BLEGattDescriptor> bleGattCharacteristicList = new ArrayList<>();
		for (BluetoothGattDescriptor bluetoothGattDescriptor : bluetoothGattCharacteristic.getDescriptors()) {
			bleGattCharacteristicList.add(new BLEGattDescriptor(bluetoothGattDescriptor));
		}
		return bleGattCharacteristicList;
	}

	private List<CharacteristicPermission> transformCharacteristicPermissions() {
		int permissions = bluetoothGattCharacteristic.getPermissions();
		List<CharacteristicPermission> characteristicPermissionList = new ArrayList<>();
		int mask = 0x01;
		int index = 0;
		CharacteristicPermission[] array = CharacteristicPermission.values();
		while (permissions != 0) {
			if ((permissions & mask) == mask) {
				characteristicPermissionList.add(array[index]);
			}
			permissions = permissions >>> 1;
			index++;
		}
		return characteristicPermissionList;
	}

	private List<CharacteristicProperty> transformCharacteristicProperties() {
		int property = bluetoothGattCharacteristic.getProperties();
		List<CharacteristicProperty> characteristicPropertyList = new ArrayList<>();
		int mask = 0x01;
		int index = 0;
		CharacteristicProperty[] array = CharacteristicProperty.values();
		while (property != 0) {
			if ((property & mask) == mask) {
				characteristicPropertyList.add(array[index]);
			}
			property = property >>> 1;
			index++;
		}
		return characteristicPropertyList;
	}

	private List<CharacteristicWriteType> transformCharacteristicWriteTypes() {
		int property = bluetoothGattCharacteristic.getWriteType();
		List<CharacteristicWriteType> characteristicWriteTypeList = new ArrayList<>();
		int mask = 0x01;
		int index = 0;
		CharacteristicWriteType[] array = CharacteristicWriteType.values();
		while (property != 0) {
			if ((property & mask) == mask) {
				characteristicWriteTypeList.add(array[index]);
			}
			property = property >>> 1;
			index++;
		}
		return characteristicWriteTypeList;
	}

	public List<BLEGattDescriptor> getBleGattDescriptorList() {
		return bleGattDescriptorList;
	}

	public String getCharacteristicUUID() {
		return bluetoothGattCharacteristic.getUuid().toString();
	}

	public String getCharacteristicSpecificationName() {
		return GattCharacteristicSpecification.getSpecificationName(getAssignedNumberHexString());
	}
	public Characteristic getCharacteritic(){
		return GattCharacteristicSpecification.getCharacteristic(getAssignedNumberHexString());
	}

	public String getAssignedNumberHexString() {
		return Integer.toHexString(getAssignedNumberInteger()).toUpperCase();
	}

	public int getAssignedNumberInteger() {
		return (int) (bluetoothGattCharacteristic.getUuid().getMostSignificantBits() >>> 32);
	}

	public List<CharacteristicProperty> getCharacteristicProperties() {
		return characteristicPropertyList;
	}

	public List<CharacteristicPermission> getCharacteristicPermissions() {
		return characteristicPermissionList;
	}

	public List<CharacteristicWriteType> getCharacteristicWriteTypes() {
		return characteristicWriteTypeList;
	}

	public String  getValue(){
		return GattCharacteristicSpecification.getCharacteristicValue(this);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\tSpecification Name: ").append(getCharacteristicSpecificationName())
				.append("\n\t\tCharacteristic UUID: ").append(getCharacteristicUUID())
				.append("\n\t\tAssigned Number Hex: ").append(getAssignedNumberHexString())
				.append("\n\t\t").append(getPropertiesString())
				.append("\n\t\t").append(getPermissionsString())
				.append("\n\t\t").append(getWriteTypesString())
				.append("\n\t\tValue: ").append(getValue())
				.append("\n\t\tDescriptor Count: ").append(getBleGattDescriptorList().size())
				.append("").append(getAllDescriptorString());
		return stringBuilder.toString();
	}
	private String getAllDescriptorString(){
		StringBuilder stringBuilder = new StringBuilder();
		for(BLEGattDescriptor descriptor:getBleGattDescriptorList()){
			stringBuilder.append("\n\t\t+").append(descriptor.toString());
		}
		return stringBuilder.toString();
	}

	public String getPropertiesString(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Properties:[ ");
		for(CharacteristicProperty characteristicProperty:getCharacteristicProperties()){
			stringBuilder.append(characteristicProperty.name()).append(",");
		}
		stringBuilder.replace(stringBuilder.length()-1,stringBuilder.length(),"]");
		return stringBuilder.toString();
	}

	public String getPermissionsString(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Permissions:[ ");
		for(CharacteristicPermission characteristicPermission:getCharacteristicPermissions()){
			stringBuilder.append(characteristicPermission.name()).append(",");
		}
		stringBuilder.replace(stringBuilder.length()-1,stringBuilder.length(),"]");
		return stringBuilder.toString();
	}

	public String getWriteTypesString(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("WriteTypes:[ ");
		for(CharacteristicWriteType characteristicWriteType:getCharacteristicWriteTypes()){
			stringBuilder.append(characteristicWriteType.name()).append(",");
		}
		stringBuilder.replace(stringBuilder.length()-1,stringBuilder.length(),"]");
		return stringBuilder.toString();
	}
}
