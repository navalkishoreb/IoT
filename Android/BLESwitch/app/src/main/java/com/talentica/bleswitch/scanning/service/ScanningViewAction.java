package com.talentica.bleswitch.scanning.service;

/**
 * Created by navalb on 17-06-2016.
 */

public enum ScanningViewAction {
	SCAN_RESULT(907),
	SHOW_SEARCH_SCREEN(904),
	ATTEMPTING_TO_CONNECT_PROFILE(905),
	NOT_CONNECTED_TO_ANY_PROFILE(906);

	private final int action;

	ScanningViewAction(int action) {
		this.action = action;
	}

	public int getAction() {
		return action;
	}

	public static ScanningViewAction fromAction(int action) {
		switch (action) {
			case 904:
				return SHOW_SEARCH_SCREEN;
			case 905:
				return ATTEMPTING_TO_CONNECT_PROFILE;
			case 906:
				return NOT_CONNECTED_TO_ANY_PROFILE;
			case 907:
				return SCAN_RESULT;
			default: throw new IllegalArgumentException("No such scanning view action defined");
		}
	}
}
