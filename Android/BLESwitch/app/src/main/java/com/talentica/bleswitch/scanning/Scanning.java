package com.talentica.bleswitch.scanning;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;

import com.talentica.bleswitch.R;
import com.talentica.bleswitch.base.BaseActivity;

public class Scanning extends BaseActivity implements SearchView.Interaction, ScanningDevices.Interaction {
	private final static String KEY_CURRENT_FRAGMENT = "current_fragment";
	private int currentFragment;

	public Scanning() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);
		setToolbar();
		getCurrentFragmentResourceId(savedInstanceState);
		setFragment(currentFragment);
	}

	@Override
	public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
		super.onSaveInstanceState(outState, outPersistentState);
		outState.putInt(KEY_CURRENT_FRAGMENT, currentFragment);
	}

	private int getCurrentFragmentResourceId(Bundle savedInstanceState) {
		if (currentFragment == 0) {
			if (savedInstanceState != null) {
				currentFragment = savedInstanceState.getInt(KEY_CURRENT_FRAGMENT);
			} else {
				currentFragment = R.string.tag_fragment_search;
			}
		}
		return currentFragment;
	}

	private void setToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	@Override
	public void startScan() {
		currentFragment = R.string.tag_fragment_scanning_devices;
		setFragment(currentFragment);
	}

	@Override
	public void showSearchScreen() {
		currentFragment = R.string.tag_fragment_search;
		setFragment(currentFragment);
	}
}
