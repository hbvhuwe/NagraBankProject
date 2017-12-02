package com.wiev.androidclient;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class ActionDialog extends DialogFragment {
  public String title;
  public String message;

  @FunctionalInterface
  public interface NoticeDialogListener {
    void onDialogPositiveClick(DialogFragment dialog);
  }

  // Use this instance of the interface to deliver action events
  NoticeDialogListener mListener;

  @Override
  public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    // Build the dialog and set up the button click handlers
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle(title);
    builder.setMessage(message);
    builder.setCancelable(false);
    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        // Send the positive button event back to the host activity
        mListener.onDialogPositiveClick(ActionDialog.this);
      }
    });
    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        //Do nothing
        return;
      }
    });
    return builder.create();
  }
}
