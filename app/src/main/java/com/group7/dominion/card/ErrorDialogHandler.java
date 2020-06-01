package com.group7.dominion.card;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ErrorDialogHandler extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Error")
                .setMessage("No cards left on stack for this type.")
                .setNeutralButton("Close", (dialog, which) -> {
                });
        return builder.create();
    }
}
