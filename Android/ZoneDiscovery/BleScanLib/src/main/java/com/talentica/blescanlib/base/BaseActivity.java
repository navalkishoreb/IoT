package com.talentica.blescanlib.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.talentica.blescanlib.R;
import java.util.List;

import static android.R.attr.tag;

/**
 * Created by NavalB on 14-06-2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

  private FragmentManager fragmentManager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    fragmentManager = getSupportFragmentManager();
  }

  private Fragment getFragment(int fragmentTag) {
    if (fragmentTag == 0) {
      throw new BLEScanLibException("fragment tag id cannot be zero");
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
      String tagString = getString(fragmentTag);
      setFragment(tagString, fragment);
  }

  private void setFragment(String tagString, Fragment fragment) {
    if (fragment != null) {
      FragmentTransaction transaction = fragmentManager.beginTransaction();
      transaction.replace(R.id.bsl_fragment_container, fragment, tagString);
      transaction.commitAllowingStateLoss();
      Debug.d(Debug.SCANNING_TAG,"replacing to "+tagString);
    }else {
      throw new BLEScanLibException("Fragment provided is NULL");
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    List<Fragment> fragments = getSupportFragmentManager().getFragments();
    for (Fragment frag : fragments) {
      if (frag instanceof BaseFragment && frag.isVisible()) {
        frag.onRequestPermissionsResult(requestCode, permissions, grantResults);
      }
    }
  }


  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }
}
