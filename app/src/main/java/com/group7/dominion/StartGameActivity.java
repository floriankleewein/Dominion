package com.group7.dominion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.group7.dominion.CheatFunction.ShakeListener;

public class StartGameActivity extends AppCompatActivity {
    Button btnStart;
    SensorManager sm;
    ShakeListener shakeListener;
    //TODO: rename this
    public static final String EXTRA_MESSAGE = "clientForNextActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_join);
        btnStart = findViewById(R.id.btn_start);


        ListView playerNamesListView = findViewById(R.id.playerNamesListView);

        /*ArrayAdapter<User> arrayAdapter
                = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1 , );*/
    //TODO: adapter für die listView. wie kommt man an die userliste?


        shakeListener = new ShakeListener(getSupportFragmentManager());
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(shakeListener.newSensorListener(), sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ClientConnector clientConnector = ClientConnector.getClientConnector();

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

    }
}
