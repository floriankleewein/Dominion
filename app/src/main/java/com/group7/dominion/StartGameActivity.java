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

import com.floriankleewein.commonclasses.User.User;
import com.group7.dominion.CheatFunction.ShakeListener;
import com.group7.dominion.Network.ClientConnector;
import com.group7.localtestserver.TestServer;

public class StartGameActivity extends AppCompatActivity {

    SensorManager sm;
    ShakeListener shakeListener;

    private Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_join);
        ListView playerNamesListView = findViewById(R.id.playerNamesListView);

        startGame = findViewById(R.id.startGame);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

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
