package com.talentica.blescanlib.scanning;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.talentica.blescanlib.R;

/**
 * A placeholder fragment containing a simple view.
 */
public final class SearchView extends Fragment {

	public static Fragment newInstance() {
		return new SearchView();
	}

	private Interaction interaction;

	public SearchView() {
	}

	 interface Interaction {
		void startScan();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof Interaction) {
			interaction = (Interaction) activity;
		} else {
			throw new IllegalArgumentException("Searching functionality/logic is diverted to activity. Implement Interaction in your host activity");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.bsl_fragment_search, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (getView() != null) {
			FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.bsl_fab);
			fab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					interaction.startScan();
				}
			});
		}
	}
}
