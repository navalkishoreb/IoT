package com.talentica.bleswitch.feasibility;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.talentica.bleswitch.R;
import com.talentica.bleswitch.base.LoadingTextFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckingFeasibility extends LoadingTextFragment {


	public CheckingFeasibility() {
		// Required empty public constructor
	}

	public static Fragment newInstance() {
		return new CheckingFeasibility();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setLoadingText(getString(R.string.checking_feasibility));
	}
}
