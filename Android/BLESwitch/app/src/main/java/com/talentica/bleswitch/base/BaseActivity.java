package com.talentica.bleswitch.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.android.debug.hv.BuildConfig;
import com.android.debug.hv.ViewServer;
import com.talentica.bleswitch.R;

/**
 * Created by NavalB on 14-06-2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragmentManager = getSupportFragmentManager();
		// Set content view, etc.
		if (BuildConfig.DEBUG) {
			ViewServer.get(this).addWindow(this);
//			Trace.beginSection(this.getClass().getSimpleName());
//			Debug.startMethodTracing(getString(R.string.app_name));
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (BuildConfig.DEBUG) {
			ViewServer.get(this).removeWindow(this);
//			Debug.stopMethodTracing();
//			Trace.endSection();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (BuildConfig.DEBUG) {
			ViewServer.get(this).setFocusedWindow(this);
		}
	}

	private Fragment getFragment(int fragmentTag) {
		if(fragmentTag == 0){
			throw new IllegalArgumentException("fragment tag id cannot be zero");
		}
		String tagString = getString(fragmentTag);
		return fragmentManager.findFragmentByTag(tagString);
	}

	protected void setFragment(int fragmentTag) {
		Fragment fragment = getFragment(fragmentTag);
		if (fragment == null) {
			fragment = FragmentFactory.get(fragmentTag);
		}
		setFragment(fragmentTag, fragment);
	}

	protected void setFragment(int fragmentTag, Fragment fragment) {
		if (fragment != null) {
			String tagString = getString(fragmentTag);
			setFragment(tagString, fragment);
		} else {
			throw new IllegalArgumentException("Fragment provided was NULL");
		}
	}

	private void setFragment(String tagString, Fragment fragment) {
		if (fragment != null) {
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.replace(R.id.fragment_container, fragment, tagString);
			transaction.commit();
		}
	}
}
