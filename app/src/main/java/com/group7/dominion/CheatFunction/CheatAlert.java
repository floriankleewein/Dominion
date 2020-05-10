package com.group7.dominion.CheatFunction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.ClientConnector;

public class CheatAlert extends AppCompatDialogFragment {

    Game game;
    String name;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("How dare you?")
                .setMessage("You really want to cheat?")
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

    private void sendMessage () {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ClientConnector.getClientConnector().sendCheatMessage();
            }
        });

        thread.start();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGame(Game game) {
        this.game = Game.getGame();
    }
}