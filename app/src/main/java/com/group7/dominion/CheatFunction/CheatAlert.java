package com.group7.dominion.CheatFunction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.floriankleewein.commonclasses.CheatFunction.CheatService;
import com.floriankleewein.commonclasses.Game;

public class CheatAlert extends AppCompatDialogFragment {

    Game game;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("How dare you?")
                .setMessage("You really want to cheat?")
                .setPositiveButton("Yes, i want to win", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Have to find Playername
                        String name = "";
                        game.getCheatService().addCardtoUser(name);
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
}