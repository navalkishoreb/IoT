package com.talentica.blescanlib.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.talentica.blescanlib.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class LoadingTextFragment extends BaseFragment implements LoadingView {

  private TextView textView;
  private ProgressBar progressBar;

  public LoadingTextFragment() {
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
      throw new BLEScanLibException("Loading text view is null");
    }
    if (progressBar == null) {
      throw new BLEScanLibException("progress bar is NULL");
    }
  }

  @Override public void setLoadingText(String loadingText) {
    if (textView != null) {
      textView.setText(loadingText);
    } else {
      throw new BLEScanLibException(
          "loading text view is NULL. Set loading text in or after onActivityCreated");
    }
  }

  @Override public void hideLoading() {
    if (progressBar != null) {
      progressBar.setVisibility(View.INVISIBLE);
    } else {
      throw new BLEScanLibException("loading progressBar is NULL.");
    }
  }

  @Override public void showLoading() {
    if (progressBar != null) {
      progressBar.setVisibility(View.VISIBLE);
    } else {
      throw new BLEScanLibException("loading progressBar is NULL.");
    }
  }
}
