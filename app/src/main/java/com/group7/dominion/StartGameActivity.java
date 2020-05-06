package com.group7.dominion;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.esotericsoftware.kryonet.Client;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.GetPlayersMsg;
import com.floriankleewein.commonclasses.Network.StartGameMsg;
import com.floriankleewein.commonclasses.User.User;
import com.group7.dominion.CheatFunction.ShakeListener;
import com.group7.dominion.Network.ClientConnector;
import com.group7.localtestserver.TestServer;

import java.util.LinkedList;
import java.util.List;

public class StartGameActivity extends AppCompatActivity {
    Button btnStart, btnshowPlayers;
    ClientConnector client;

    //TODO: rename this
    public static final String EXTRA_MESSAGE = "clientForNextActivity";

    private Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_join);
        btnStart = findViewById(R.id.btn_start);
        btnshowPlayers = findViewById(R.id.ShowPlayer);


        ListView playerNamesListView = findViewById(R.id.playerNamesListView);



        /*ArrayAdapter<User> arrayAdapter
                = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1 , );*/
        //TODO: adapter für die listView. wie kommt man an die userliste?


    }

    @Override
    protected void onStart() {
        super.onStart();
        client = new ClientConnector();

        btnshowPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<User> PlayerList = client.getPlayerList();
                        if (PlayerList == null) {
                            System.out.println("List is empty!");
                        }
                    }
                });
                thread.start();
            }
        });

        /*ClientConnector client = (ClientConnector) getIntent().getSerializableExtra(EXTRA_MESSAGE);

        client.registerCallback(StartGameMsg.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(StartGameActivity.this, GameActivity.class);
                    startActivity(intent);
                }
            });
        }));*/

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //TODO: FLO: for gamecreation it must be possible to pass the client to the next activity.
                        //client.startGame();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(StartGameActivity.this, GameActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                });

                thread.start();
            }
        });

        //Intent intent = getIntent();
        //String msg = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //TextView textView = findViewById(R.id.testTextView);
        //textView.setText(msg);
    }


    public void startGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

}
