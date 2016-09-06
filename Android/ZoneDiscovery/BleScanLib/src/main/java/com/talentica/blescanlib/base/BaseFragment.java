package com.talentica.blescanlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by NavalB on 01-07-2016.
 */

public abstract class BaseFragment extends Fragment implements BaseView {

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (view == null) {
      throw new BLEScanLibException("root View cannot be NULL");
    }
    findViews(view);
    setViews();
  }

}


