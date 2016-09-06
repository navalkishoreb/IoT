package com.talentica.bleswitch.feasibility;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.talentica.bleswitch.R;

/**
 * This fragment is to display message for functionality not supported by device.
 * <p>
 * This fragment can NOT have any action. This is terminal message.
 */
public class NoSupport extends Fragment {

	private static final String KEY_DISPLAY_MESSAGE = "display_message";

	private String message;
	private TextView messageView;

	public NoSupport() {
		// Required empty public constructor
	}

	static Fragment newInstance(String message) {
		Bundle bundle = new Bundle();
		bundle.putString(KEY_DISPLAY_MESSAGE, message);
		Fragment fragment = new NoSupport();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			message = bundle.getString(KEY_DISPLAY_MESSAGE, getString(R.string.error_unknown));
		} else {
			throw new IllegalArgumentException("No error message was provided.");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_no_support, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (getView() != null) {
			findViews(getView());
		} else {
			throw new NullPointerException("No views found");
		}

		messageView.setText(message);
	}

	private void findViews(View rootView) {
		messageView = (TextView) rootView.findViewById(R.id.no_support_message);
	}


}
