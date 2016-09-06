package com.talentica.blescanlib.scanning.service;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.talentica.blescanlib.R;
import com.talentica.blescanlib.base.BLEScanLibException;
import com.talentica.blescanlib.base.BaseActivity;
import com.talentica.blescanlib.base.Debug;

/**
 * Created by NavalB on 05-07-2016.
 */

public abstract class AbstractScanningActivity extends BaseActivity implements ScanningActivityView {
  private final static String KEY_CURRENT_FRAGMENT = "current_fragment";
  private int currentFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.bsl_activity_scan);
    setToolbar();
    getCurrentFragmentResourceId(savedInstanceState);
    setFragment(currentFragment);
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(KEY_CURRENT_FRAGMENT, currentFragment);
    Debug.i(Debug.SCANNING_TAG, "current fragment saved " + getString(currentFragment));
  }

  private int getCurrentFragmentResourceId(Bundle savedInstanceState) {
    if (currentFragment == 0) {
      Debug.d(Debug.SCANNING_TAG, "current fragment tag is zero");
      if (savedInstanceState != null) {
        Debug.d(Debug.SCANNING_TAG, "restoring fragment tag from savedInstance");
        currentFragment = savedInstanceState.getInt(KEY_CURRENT_FRAGMENT);
        Debug.i(Debug.SCANNING_TAG, "current fragment fetched " + getString(currentFragment));
      } else {
        Debug.d(Debug.SCANNING_TAG,
            "there was no savedInstance bundle. Using default search screen.");
        currentFragment = R.string.bsl_tag_fragment_search;
      }
    }
    if (currentFragment == 0) {
      throw new BLEScanLibException("how current fragment is zero??");
    }
    return currentFragment;
  }

  private void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.bsl_toolbar);
	  if(toolbar!=null) {
		  setSupportActionBar(toolbar);
	  }else{
		  Debug.e(Debug.SCANNING_TAG,"default bsl toolbar is not available");
	  }
  }

  protected void setCurrentFragment(int currentFragment) {
    this.currentFragment = currentFragment;
    setFragment(currentFragment);
  }
}
