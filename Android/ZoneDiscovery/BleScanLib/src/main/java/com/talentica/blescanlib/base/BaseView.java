package com.talentica.blescanlib.base;

    import android.content.Context;
    import android.support.annotation.NonNull;
    import android.view.View;

/**
 * Created by NavalB on 01-07-2016.
 */

public interface BaseView {
  Context getContext();

  void findViews(@NonNull View rootView);

  void setViews();
}
