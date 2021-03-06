package com.wiev.androidclient;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Class to show messages(plain or error) with button OK<br>
 * only one method - onCreateDialog, that creates dialog using messageTitle and messageToShow<br>
 * using: create new object of this class, fill fields and call show(getFragmentManager(), "your description here")
 */

public class Message extends DialogFragment {
  public String messageTitle = null;
  public String messageToShow = null;

  @Override
  public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle(messageTitle);
    builder.setCancelable(false);
    builder.setMessage(messageToShow);
    builder.setPositiveButton("OK", null);
    return builder.create();
  }
}
