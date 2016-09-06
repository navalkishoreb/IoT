package com.talentica.bleswitch.feasibility;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.talentica.bleswitch.R;
import com.talentica.bleswitch.base.BaseActivity;
import com.talentica.bleswitch.scanning.Scanning;

public class Launcher extends BaseActivity {

	private Feasibility feasibility;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		attachCheckingFeasibilityView();
		feasibility = new FeasibilityChecker(this);
	}

	private void attachCheckingFeasibilityView() {
		setFragment(R.string.tag_fragment_check_feasibility);
	}

	@Override
	protected void onStart() {
		super.onStart();
		takeAction(feasibility.check());
	}

	private void takeAction(FeasibilityErrorType check) {
		if (check != null) {
			switch (check) {
				case NO_PROBLEM:
					launchSearch();
					break;
				default:
					attachNoSupportFragment(check);
					break;
			}
		} else {
			throw new NullPointerException("There is suppose to be proper check response to take action");
		}
	}

	private void attachNoSupportFragment(FeasibilityErrorType check) {
		Fragment fragment = NoSupport.newInstance(check.getError());
		setFragment(R.string.tag_fragment_no_support, fragment);
	}

	private void launchSearch() {
		Intent intent = new Intent(this, Scanning.class);
		startActivity(intent);
		finish();
	}
}
