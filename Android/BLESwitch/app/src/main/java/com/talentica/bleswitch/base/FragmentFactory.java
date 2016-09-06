package com.talentica.bleswitch.base;

import android.support.v4.app.Fragment;

import com.talentica.bleswitch.R;
import com.talentica.bleswitch.feasibility.CheckingFeasibility;
import com.talentica.bleswitch.scanning.ScanningDevices;
import com.talentica.bleswitch.scanning.SearchView;

/**
 * Created by NavalB on 14-06-2016.
 */
public abstract class FragmentFactory {

	public static Fragment get(int fragmentTag) {
		Fragment fragment;
		switch (fragmentTag) {
			case R.string.tag_fragment_search:
				fragment = SearchView.newInstance();
				break;
			/*case R.string.tag_fragment_error:
				fragment = SearchView.newInstance();
				break;
			case R.string.tag_fragment_device_listing:
				fragment = SearchView.newInstance();
				break;*/
			case R.string.tag_fragment_check_feasibility:
				fragment = CheckingFeasibility.newInstance();
				break;
			case R.string.tag_fragment_scanning_devices:
				fragment = ScanningDevices.newInstance();
				break;
			default:
				throw new IllegalArgumentException("There is no response declared for given fragment tag " + fragmentTag);
		}
		return fragment;
	}
}
