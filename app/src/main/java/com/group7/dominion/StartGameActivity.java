package com.group7.dominion;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.floriankleewein.commonclasses.Network.AllPlayersInDominionActivityMsg;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.ClientConnector;

import com.floriankleewein.commonclasses.Network.ClientConnector;

import com.floriankleewein.commonclasses.Network.StartGameMsg;
import com.floriankleewein.commonclasses.Network.UpdatePlayerNamesMsg;

import com.group7.dominion.CheatFunction.ShakeListener;

import java.util.ArrayList;

public class StartGameActivity extends AppCompatActivity {

    Button btnStart, btnshowPlayers;
    ClientConnector client;


    Button btnRecreate;
    ShakeListener shakeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_join);
        btnStart = findViewById(R.id.btn_start);
        btnshowPlayers = findViewById(R.id.ShowPlayer);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ClientConnector clientConnector = ClientConnector.getClientConnector();

        //FKDoc: this is the arrayList,where the names will be stored.
        ArrayList<String> names = new ArrayList<>();

        //FKDoc: this is the listView where the playerNames should be viewed.
        ListView playerNamesListView = findViewById(R.id.playerNamesListView);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                clientConnector.updatePlayerNames();
            }
        });

        thread.start();

        //FKDoc: the listViewAdapter is used as a communication tool between the listView and the data that should be shown.
        ArrayAdapter<String> listViewAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);

        playerNamesListView.setAdapter(listViewAdapter);

        //FKDoc: thats the servercallback which is triggered after the clientConnector.getGame() call.
        clientConnector.registerCallback(UpdatePlayerNamesMsg.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    names.clear();
                    names.addAll(((UpdatePlayerNamesMsg) msg).getNameList());
                    listViewAdapter.notifyDataSetChanged();
                        Thread thread1 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                clientConnector.startGame();
                            }
                        });
                        thread1.start();
                }
            });
        }));

        clientConnector.registerCallback(AllPlayersInDominionActivityMsg.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(StartGameActivity.this, DominionActivity.class);
                    startActivity(intent);
                }
            });
        }));

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        clientConnector.allPlayersInDominionActivity();

                    }
                });

                thread.start();
            }
        });
        ClientConnector.getClientConnector().registerCallback(StartGameMsg.class, msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Got the Callback for StartGameMsg in StartGameAcitivity");
                }
            });
        });
    }


    public String getUserName() {
        return getSharedPreferences("USERNAME", Context.MODE_PRIVATE).getString("us", "username");
    }
}
