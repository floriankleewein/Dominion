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

        /**@Author Maurer Florian
         * Creating the Dialog and the Spinner.
         * A Player can cheat in the game 1x. He will get an extra card from his deck. The Player can cheat anytime in the Game
         * so he should decied wisely when he use it. Also the player can suspect an another Player, if he is right, he will get +5 Points.
         * If he is wrong, his Points decrease 5 Points.
         */

        String[] s = parseLisToString();
        final ArrayAdapter<String> adp = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, s);
        final Spinner sp = new Spinner(getContext());
        sp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);
        sp.setOnItemSelectedListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Geheimes Cheat Menü")
                .setMessage("Willst du cheaten oder jemanden verdächtigen?")
                .setView(sp)
                .setPositiveButton("Gib eine Karte", (dialog, which) -> {
                    if (!alreadyCheated) {
                        alreadyCheated = true;
                        sendMessage();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Menü schließen", (dialog, which) -> {
                    dialog.cancel();
                }).setNeutralButton("Ausgewählten Spieler verdächtigen", (dialog, which) -> {
                    if (!this.suspectedUser.equals(this.name)) {
                        deleteSelectedUser(this.suspectedUser);
                        sendSuspectMessage();
                        dialog.cancel();
                    }
                });


        return builder.create();
    }

    /**@Author Maurer Florian
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

    /**@Author Maurer Florian
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