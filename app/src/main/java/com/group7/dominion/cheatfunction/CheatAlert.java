package com.group7.dominion.cheatfunction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.floriankleewein.commonclasses.network.ClientConnector;

import java.util.List;


public class CheatAlert extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {

    private String name;
    private String suspectedUser;
    private List<String> namesList;
    private boolean alreadyCheated = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /**
         * Creating the Dialog and the Spinner
         */

        String[] s = parseLisToString();
        final ArrayAdapter<String> adp = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, s);
        final Spinner sp = new Spinner(getContext());
        sp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);
        sp.setOnItemSelectedListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Very Secret Cheat Menu")
                .setMessage("You really want to cheat? Or do you want to suspect someone?")
                .setView(sp)
                .setPositiveButton("Give me an Extra Card", (dialog, which) -> {
                    if (!alreadyCheated) {
                        alreadyCheated = true;
                        sendMessage();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Close Cheat Menu", (dialog, which) -> {
                    dialog.cancel();
                }).setNeutralButton("Suspect Selected User", (dialog, which) -> {
                    if (!this.suspectedUser.equals(this.name)) {
                        deleteSelectedUser(this.suspectedUser);
                        sendSuspectMessage();
                        dialog.cancel();
                    }
                });


        return builder.create();
    }

    /**
     * Methods for parsing the ArrayList to String Array for Spinner, delete User who already got suspected and sending Messages to
     * the Server
     */

    public String[] parseLisToString() {
        String[] s = new String[this.namesList.size()];
        for (int i = 0; i < this.namesList.size(); i++) {
            s[i] = this.namesList.get(i);
        }

        return s;
    }

    private void deleteSelectedUser(String name) {
        for (int i = 0; i < this.namesList.size(); i++) {
            if (this.namesList.get(i).equals(name)) {
                this.namesList.remove(i);
                break;
            }
        }
    }

    private void sendSuspectMessage() {
        Thread thread = new Thread(() -> ClientConnector.getClientConnector().sendSuspectUser(suspectedUser,name));
        thread.start();
    }

    private void sendMessage() {

        Thread thread = new Thread(() -> ClientConnector.getClientConnector().sendCheatMessage(this.name));

        thread.start();
    }

    /**
     * Methods for the Spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.suspectedUser = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setName(String name) {
        this.name = name;
    }
    

    public void setNamesList(List<String> namesList) {
        this.namesList = namesList;
    }


}