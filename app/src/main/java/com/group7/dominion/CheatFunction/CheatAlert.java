package com.group7.dominion.CheatFunction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.ClientConnector;

import java.util.List;
import java.util.Objects;

public class CheatAlert extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {

    private String name;
    private String SuspectedUser;
    private List<String> namesList;
    private int currentSelect;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String[] s = parseLisToString();
        final ArrayAdapter<String> adp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, s);
        final Spinner sp = new Spinner(getContext());
        sp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);
        sp.setOnItemSelectedListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Very Secret Cheat Menu")
                .setMessage("You really want to cheat? Or do you want to suspect someone?")
                .setView(sp)
                .setPositiveButton("Yes, i want to win", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Game.getGame().getPlayerList().size() > 0) {
                            Game.getGame().getCheatService().addCardtoUser(name);
                        }
                        sendMessage();
                        dialog.cancel();

                    }
                })
                .setNegativeButton("No, im a fair Gamer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nothing happens
                        dialog.cancel();
                    }
                }).setNeutralButton("Suspect Selected User ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!SuspectedUser.equals(name)) {
                            deleteSelectedUser(SuspectedUser);
                            sendSuspectMessage(SuspectedUser, name);
                            dialog.cancel();
                        }
                    }
                });


        return builder.create();
    }

    private void sendMessage() {

        Thread thread = new Thread(() -> ClientConnector.getClientConnector().sendCheatMessage(name));

        thread.start();
    }

    public String[] parseLisToString() {
        String[] s = new String[namesList.size()];
        for (int i = 0; i < namesList.size(); i++) {
            s[i] = namesList.get(i);
        }

        return s;
    }

    private void deleteSelectedUser(String name) {
        for (int i = 0; i < namesList.size(); i++) {
            if (namesList.get(i).equals(name)) {
                namesList.remove(i);
            }
        }
    }

    private void sendSuspectMessage(String SuspectedName, String name) {
        Thread thread = new Thread(() -> ClientConnector.getClientConnector().sendSuspectUser(SuspectedName, name));
        thread.start();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SuspectedUser = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNamesList() {
        return namesList;
    }

    public void setNamesList(List<String> namesList) {
        this.namesList = namesList;
    }


}