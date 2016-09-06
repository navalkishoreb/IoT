package com.talentica.blescanlib.base;

import android.support.v4.app.Fragment;
import com.talentica.blescanlib.R;
import com.talentica.blescanlib.feasibility.CheckingFeasibility;
import com.talentica.blescanlib.scanning.ScanningDevices;
import com.talentica.blescanlib.scanning.SearchView;

/**
 * Created by NavalB on 14-06-2016.
 */
 final class FragmentFactory extends AbstractFragmentFactory {

	 static Fragment get(int fragmentTag) {
		Fragment fragment;
		if (fragmentTag == R.string.bsl_tag_fragment_search) {
			fragment = SearchView.newInstance();
		} else if (fragmentTag == R.string.bsl_tag_fragment_check_feasibility) {
			fragment = CheckingFeasibility.newInstance();
		} else if (fragmentTag == R.string.bsl_tag_fragment_scanning_devices) {
			fragment = ScanningDevices.newInstance();
		} else {
			throw new BLEScanLibException("There is no response declared for given fragment tag " + fragmentTag);
		}
		return fragment;
	}
}
