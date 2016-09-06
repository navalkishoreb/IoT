package com.talentica.blescanlib.feasibility;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.talentica.blescanlib.R;
import com.talentica.blescanlib.base.BaseFragment;

/**
 * This fragment is to display message for functionality not supported by device.
 * <p>
 * This fragment can NOT have any action. This is terminal message.
 */
public final class NoSupport extends BaseFragment {

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

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = getArguments();
    if (bundle != null) {
      message = bundle.getString(KEY_DISPLAY_MESSAGE, getString(R.string.bsl_error_unknown));
    } else {
      throw new IllegalArgumentException("No error message was provided.");
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.bsl_fragment_no_support, container, false);
  }

  @Override public void findViews(@NonNull View rootView) {
    messageView = (TextView) rootView.findViewById(R.id.bsl_no_support_message);
    if (messageView == null) {
      throw new NullPointerException("No message view found. Use id bsl_no_support_message to mark no support text view.");
    }
  }

  @Override public void setViews() {
    messageView.setText(message);
  }
}
