package com.talentica.bleswitch.feasibility;

/**
 * Created by NavalB on 14-06-2016.
 */
public interface Feasibility {

	FeasibilityErrorType check();

	interface Listener {
		void onSuccess();

		void onError(FeasibilityErrorType errorType);
	}
}
