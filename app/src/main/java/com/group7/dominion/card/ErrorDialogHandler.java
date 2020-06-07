package com.group7.dominion.card;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * LKDoc:   ein einfacher ErrorDialog, welcher aufgerufen wird wenn card = null
 *          das bedeutet wenn keine Karten mehr im Stapel sind
 */
public class ErrorDialogHandler extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Error")
                .setMessage("Keine Karten mehr von diesem Typ im Stapel")
                .setNeutralButton("Schließen", (dialog, which) -> {
                });
        return builder.create();
    }
}
