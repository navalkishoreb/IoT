package com.talentica.zonediscovery.zoning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.talentica.blescanlib.base.Debug;
import com.talentica.blescanlib.scanning.blemodels.BLEScanResult;

import com.talentica.zonediscovery.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NavalB on 23-05-2016.
 */
class BLEDeviceListAdapter extends BaseAdapter {
	private static final String TAG = BLEDeviceListAdapter.class.getSimpleName();
	private final Context context;
	private Map<String, BLEScanResult> bleDeviceMap;
	private List<String> bleDeviceKey;

	public BLEDeviceListAdapter(Context context) {
		this.context = context;
	}

	public void addDevice(BLEScanResult scanResult) {
		if (bleDeviceKey == null) {
			createList(scanResult);
		} else {
			if (!bleDeviceMap.containsKey(scanResult.getDeviceAddress())) {
				Debug.d(TAG, "adding new device...");
				bleDeviceKey.add(scanResult.getDeviceAddress());
			}
			Debug.d(TAG, "replacing " + scanResult.getDeviceAddress() + "...");
			bleDeviceMap.put(scanResult.getDeviceAddress(), scanResult);
			notifyDataSetChanged();
		}
	}

	public void clear(){
		if(getCount()>0) {
			bleDeviceKey.clear();
			bleDeviceMap.clear();
			notifyDataSetChanged();
		}

	}

	private void createList(BLEScanResult scanResult) {
		Debug.d(TAG, "creating list...");
		bleDeviceKey = new ArrayList<>();
		bleDeviceKey.add(scanResult.getDeviceAddress());
		bleDeviceMap = new HashMap<>();
		bleDeviceMap.put(scanResult.getDeviceAddress(), scanResult);
		Debug.d(TAG, "adding new device...");
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (bleDeviceKey != null) {
			return bleDeviceKey.size();
		}
		return 0;
	}

	@Override
	public BLEScanResult getItem(int position) {
		return bleDeviceMap.get(bleDeviceKey.get(position));
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_ble_devices, parent, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.changeData(getItem(position));
		return convertView;
	}

	private static class ViewHolder implements View.OnClickListener {
		private TextView deviceName;
		private TextView deviceAddress;
		private TextView bondState;
		private TextView deviceType;
		private TextView txLevel;
		private TextView rssi;
		private Button connectToGatt;
		private BLEScanResult bleScanResult;
		private final View convertView;

		ViewHolder(View convertView) {
			this.convertView = convertView;
			findViews();
		}

		private void findViews() {
			deviceName = (TextView) convertView.findViewById(R.id.item_ble_devices_device_name);
			deviceAddress = (TextView) convertView.findViewById(R.id.item_ble_devices_device_address);
			bondState = (TextView) convertView.findViewById(R.id.item_ble_devices_bond_state);
			deviceType = (TextView) convertView.findViewById(R.id.item_ble_devices_device_type);
			txLevel = (TextView) convertView.findViewById(R.id.item_ble_devices_tx_level);
			rssi = (TextView) convertView.findViewById(R.id.item_ble_devices_rssi);
		}

		void changeData(BLEScanResult bleScanResult) {
			this.bleScanResult = bleScanResult;
			updateView();
		}

		void updateView() {
			deviceName.setText(bleScanResult.getDeviceName());
			deviceAddress.setText(bleScanResult.getDeviceAddress());
			bondState.setText(bleScanResult.getBondState().getDescription());
			deviceType.setText(bleScanResult.getDeviceType().getType());
			txLevel.setText("Tx Power Level "+String.valueOf(bleScanResult.getTxPowerLevel()));
			rssi.setText(String.valueOf(bleScanResult.getRssi()));
		}

		@Override
		public void onClick(View v) {
		}

	}
}
