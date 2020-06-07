package com.group7.dominion;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.floriankleewein.commonclasses.network.AllPlayersInDominionActivityMsg;
import com.floriankleewein.commonclasses.network.ClientConnector;
import com.floriankleewein.commonclasses.network.StartGameMsg;
import com.floriankleewein.commonclasses.network.StartbuttonMsg;
import com.floriankleewein.commonclasses.network.UpdatePlayerNamesMsg;

import com.group7.dominion.cheatfunction.ShakeListener;

import java.util.ArrayList;

public class StartGameActivity extends AppCompatActivity {

    Button btnStart;
    ClientConnector client;


    Button btnRecreate;
    ShakeListener shakeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_join);
        btnStart = findViewById(R.id.btn_start);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ClientConnector clientConnector = ClientConnector.getClientConnector();


        /**
         * FKDoc: this is the arrayList,where the names will be stored.
         */
        ArrayList<String> names = new ArrayList<>();

        /**
         * FKDoc: this is the listView where the playerNames should be viewed.
         */
        ListView playerNamesListView = findViewById(R.id.playerNamesListView);
        Thread thread = new Thread(clientConnector::updatePlayerNames);

        thread.start();

        /**
         * FKDoc: the listViewAdapter is used as a communication tool between the listView and the data that should be shown.
         */
        ArrayAdapter<String> listViewAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);

        playerNamesListView.setAdapter(listViewAdapter);

        Thread thread2 = new Thread(clientConnector::checkStartbutton);
        thread2.start();

        /**
         * FKDoc: thats the servercallback which is triggered after the clientConnector.getGame() call. The client calls start game,
         *        which handles the further steps.
         */
        clientConnector.registerCallback(UpdatePlayerNamesMsg.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    names.clear();
                    names.addAll(((UpdatePlayerNamesMsg) msg).getNameList());
                    listViewAdapter.notifyDataSetChanged();
                }
            });
        }));

        /**
         * FKDoc: this callback is done after each user receives the corresponding message, which puts everyone in the DominionActivity
         *        at the same time.
         */
        clientConnector.registerCallback(AllPlayersInDominionActivityMsg.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Thread thread1 = new Thread(clientConnector::startGame);
                    thread1.start();
                    Intent intent = new Intent(StartGameActivity.this, DominionActivity.class);
                    startActivity(intent);
                }
            });
        }));

        /**
         * FKDoc: this is the callback which is executed to enable the button to start the game
         */
        clientConnector.registerCallback(StartbuttonMsg.class, (msg -> {
            StartbuttonMsg temp = (StartbuttonMsg) msg;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Button btn = findViewById(R.id.btn_start);
                    btn.setEnabled(temp.isStartValue());
                }
            });
        }));

        /**
         * FKDoc: one player can click the button START, to trigger all further events to place all players in the actual game screen aka
         *        DominionActivity.
         */
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(clientConnector::allPlayersInDominionActivity);
                thread.start();
            }
        });

        clientConnector.registerCallback(StartGameMsg.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("Callback", "Got the Callback");
                }
            });
        }));
    }


    public String getUserName() {
        return getSharedPreferences("USERNAME", Context.MODE_PRIVATE).getString("us", "username");
    }
}
