package com.talentica.bleswitch.functionality.bleConnectionModels;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Build;

import com.talentica.bleswitch.base.Utils;

import java.util.HashMap;

/**
 * Created by NavalB on 24-05-2016.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class GattCharacteristicSpecification {
	private static HashMap<String, Characteristic> characteristicSpecificationMap;

	static {
		characteristicSpecificationMap = new HashMap<>();
		characteristicSpecificationMap.put("2A00", create("2A00", "Device Name", BluetoothGattCharacteristic.FORMAT_SINT8, DataType.STRING, null));
		characteristicSpecificationMap.put("2A01", create("2A01", "Appearance", BluetoothGattCharacteristic.FORMAT_UINT16, DataType.INTEGER, null));
		characteristicSpecificationMap.put("2A02", create("2A02", "Peripheral Privacy Flag", BluetoothGattCharacteristic.FORMAT_UINT8, DataType.BOOLEAN, null));
		characteristicSpecificationMap.put("2A03", create("2A03", "Reconnection Address", BluetoothGattCharacteristic.FORMAT_UINT8, DataType.HEX,null));
		characteristicSpecificationMap.put("2A04", create("2A04", "Peripheral Preferred Connection Parameters", BluetoothGattCharacteristic.FORMAT_UINT8, DataType.HEX,null));
		characteristicSpecificationMap.put("2A05", create("2A05", "Service Changed", BluetoothGattCharacteristic.FORMAT_UINT16, DataType.HEX,null));
		characteristicSpecificationMap.put("2A06", create("2A06", "Alert Level", BluetoothGattCharacteristic.FORMAT_UINT8, DataType.INTEGER, new Object[]{0, 1, 2}));
		characteristicSpecificationMap.put("2A19", create("2A19", "Battery Level", BluetoothGattCharacteristic.FORMAT_UINT8, DataType.INTEGER,null));
		characteristicSpecificationMap.put("2A07", create("2A07", "Tx Power Level", BluetoothGattCharacteristic.FORMAT_SINT8, DataType.INTEGER,null));
	}

	private static Characteristic create(String assignedNumber, String specificationName, int formatType, DataType dataType, Object[] permissibleValues) {
		Characteristic.Builder builder = new Characteristic.Builder();
		builder.setAssignedNumber(assignedNumber).setSpecificationName(specificationName).setFormatType(formatType).setDataType(dataType).setPermissibleValues(permissibleValues);
		return builder.build();
	}

	public static String getSpecificationName(String assignedNumber) {
		Characteristic characteristic = getCharacteristic(assignedNumber);
		if (characteristic != null) {
			return characteristic.getSpecificationName();
		}
		return "<unknown_characteristic_specification>";
	}

	public static Characteristic getCharacteristic(String assignedNumber) {
		if (characteristicSpecificationMap.containsKey(assignedNumber)) {
			return characteristicSpecificationMap.get(assignedNumber);
		}
		return null;
	}

	public static String getCharacteristicValue(BLEGattCharacteristic bleGattCharacteristic) {
		String value = "<no_value>";
		if (bleGattCharacteristic != null) {
			BluetoothGattCharacteristic bluetoothGattCharacteristic = bleGattCharacteristic.getBluetoothGattCharacteristic();
			if (bluetoothGattCharacteristic.getValue() != null) {
				Characteristic characteristic = getCharacteristic(bleGattCharacteristic.getAssignedNumberHexString());
				if (characteristic != null) {
					switch (characteristic.getDataType()) {
						case INTEGER:
							value = String.valueOf(bluetoothGattCharacteristic.getIntValue(characteristic.getFormatType(), characteristic.getOffset()));
							break;
						case FLOAT:
							value = String.valueOf(bluetoothGattCharacteristic.getFloatValue(characteristic.getFormatType(), characteristic.getOffset()));
							break;
						case STRING:
							value = bluetoothGattCharacteristic.getStringValue(characteristic.getOffset());
							break;
						case HEX:
							value = Utils.bytesToHex(bluetoothGattCharacteristic.getValue());
							value = putDashInHex(value);
							break;
						case BOOLEAN:
							switch (bluetoothGattCharacteristic.getIntValue(characteristic.getFormatType(), characteristic.getOffset())) {
								case 0:
									value = "false";
									break;
								case 1:
									value = "true";
									break;
							}
							break;
						default:
							value = Utils.bytesToHex(bluetoothGattCharacteristic.getValue());
							value = putDashInHex(value);
					}
				}
			}
		}
		return value;
	}

	private static String putDashInHex(String value) {
		StringBuilder stringBuilder = new StringBuilder(value);
		switch (stringBuilder.length() % 4) {
			case 3:
				stringBuilder.insert(0, "0");
			case 2:
				stringBuilder.insert(0, "0");
			case 1:
				stringBuilder.insert(0, "0");
		}
		for (int i = stringBuilder.length() - 4; i > 0; i -= 4) {
			stringBuilder.insert(i, "-");
		}
		return stringBuilder.toString();
	}
}
