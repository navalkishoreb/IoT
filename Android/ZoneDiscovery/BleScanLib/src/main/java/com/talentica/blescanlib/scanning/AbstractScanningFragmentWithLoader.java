package com.talentica.blescanlib.scanning;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.talentica.blescanlib.R;
import com.talentica.blescanlib.scanning.service.AbstractScanningFragment;

/**
 * Created by NavalB on 01-07-2016.
 */

abstract class AbstractScanningFragmentWithLoader extends AbstractScanningFragment
    implements ScanningViewWithLoader {

  private TextView textView;
  private ProgressBar progressBar;

  public AbstractScanningFragmentWithLoader() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the progressBar for this fragment
    return inflater.inflate(R.layout.bsl_fragment_loading_text, container, false);
  }

  @Override public void findViews(@NonNull View rootView) {
    textView = (TextView) rootView.findViewById(R.id.bsl_loading_text);
    progressBar = (ProgressBar) rootView.findViewById(R.id.bsl_progressBar);
    if (textView == null) {
      throw new NullPointerException("Loading text view is null. You must have overridden onCreateView method. Id your loading text view as bsl_loading_text");
    }
    if (progressBar == null) {
      throw new NullPointerException("Progress bar is NULL. You must have overridden onCreateView method. Id your progress bar view as bsl_progressBar");
    }
  }

  @Override public void setViews() {

  }

  @Override public void setLoadingText(String loadingText) {
    if (textView != null) {
      textView.setText(loadingText);
    } else {
      throw new NullPointerException(
          "loading text view is NULL. Set loading text in or after onActivityCreated");
    }
  }

  @Override public void hideLoading() {
    if (progressBar != null) {
      progressBar.setVisibility(View.INVISIBLE);
    } else {
      throw new NullPointerException("loading progressBar is NULL.");
    }
  }

  @Override public void showLoading() {
    if (progressBar != null) {
      progressBar.setVisibility(View.VISIBLE);
    } else {
      throw new NullPointerException("loading progressBar is NULL.");
    }
  }
}
