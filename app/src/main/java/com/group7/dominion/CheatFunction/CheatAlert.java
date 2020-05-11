package com.group7.dominion.CheatFunction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.floriankleewein.commonclasses.CheatFunction.CheatService;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.floriankleewein.commonclasses.User.User;
import com.group7.dominion.GameActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CheatAlert extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {

    Game game;
    String name;
    ArrayList <String> names;
    static boolean firstClick = true;

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
                        //TODO: Have to find Playername
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
                });

        return builder.create();
    }

    private void sendMessage() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ClientConnector.getClientConnector().sendCheatMessage(name);
            }
        });

        thread.start();
    }

    public String [] parseLisToString () {
        String [] s = new String [names.size()];
        for (int i = 0; i < names.size() ; i++) {
            s[i] = names.get(i);
        }

        return s;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGame(Game game) {
        this.game = Game.getGame();
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (!firstClick){
            String SuspectedUserame = (String) parent.getItemAtPosition(position);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ClientConnector.getClientConnector().sendSuspectUser(SuspectedUserame,name);
                }
            });
            thread.start();
            Objects.requireNonNull(getDialog()).cancel();
        }
        firstClick = false;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}