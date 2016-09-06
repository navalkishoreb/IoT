package com.talentica.bleswitch.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.talentica.bleswitch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class LoadingTextFragment extends Fragment {

	private TextView textView;
	private ProgressBar progressBar;

	public LoadingTextFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the progressBar for this fragment
		return inflater.inflate(R.layout.fragment_loading_text, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (getView() != null) {
			textView = (TextView) getView().findViewById(R.id.loading_text);
			progressBar = (ProgressBar) getView().findViewById(R.id.progressBar);
		} else {
			throw new NullPointerException("No view is available");
		}
	}

	protected void setLoadingText(String loadingText) {
		if (textView != null) {
			textView.setText(loadingText);
		} else {
			throw new NullPointerException("loading text view is NULL. Set loading text in or after onActivityCreated");
		}
	}

	protected void hideLoading() {
		if (progressBar != null) {
			progressBar.setVisibility(View.INVISIBLE);
		} else {
			throw new NullPointerException("loading progressBar is NULL.");
		}
	}

	protected void showLoading() {
		if (progressBar != null) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			throw new NullPointerException("loading progressBar is NULL.");
		}
	}
}
