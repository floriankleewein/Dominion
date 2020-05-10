package com.group7.dominion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.esotericsoftware.kryonet.Client;
import com.floriankleewein.commonclasses.Network.AddPlayerNameErrorMsg;
import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.floriankleewein.commonclasses.Network.GetGameMsg;
import com.floriankleewein.commonclasses.User.User;
import com.group7.dominion.CheatFunction.ShakeListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StartGameActivity extends AppCompatActivity {
    Button btnStart, btnRecreate;
    SensorManager sm;
    ShakeListener shakeListener;
    //TODO: rename this
    public static final String EXTRA_MESSAGE = "clientForNextActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_join);
        btnStart = findViewById(R.id.btn_start);
        btnRecreate = findViewById(R.id.btn_recreate);

        ClientConnector clientConnector = ClientConnector.getClientConnector();



        shakeListener = new ShakeListener(getSupportFragmentManager());
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(shakeListener.newSensorListener(), sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ClientConnector clientConnector = ClientConnector.getClientConnector();

        ListView playerNamesListView = findViewById(R.id.playerNamesListView);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                clientConnector.getGame();
            }
        });
        thread.start();

        ArrayList<String> names = new ArrayList<>();

        ArrayAdapter<String> listViewAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);



        clientConnector.registerCallback(GetGameMsg.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    names.addAll(((GetGameMsg)msg).getNameList());
                    playerNamesListView.setAdapter(listViewAdapter);
                }
            });
        }));


        btnRecreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listViewAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });

                thread.start();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

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
    }
}
