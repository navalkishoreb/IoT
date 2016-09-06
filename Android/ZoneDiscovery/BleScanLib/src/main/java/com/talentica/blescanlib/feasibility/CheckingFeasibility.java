package com.talentica.blescanlib.feasibility;

import android.support.v4.app.Fragment;
import com.talentica.blescanlib.R;
import com.talentica.blescanlib.base.LoadingTextFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public final class CheckingFeasibility extends LoadingTextFragment {


	public CheckingFeasibility() {
		// Required empty public constructor
	}

	public static Fragment newInstance() {
		return new CheckingFeasibility();
	}


  @Override public void setViews() {
    setLoadingText(getString(R.string.bsl_checking_feasibility));
  }
}
