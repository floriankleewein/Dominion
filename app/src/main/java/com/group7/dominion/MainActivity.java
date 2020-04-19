package com.group7.dominion;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.group7.dominion.Cheat_Function.ShakeListener;
import com.group7.dominion.Network.ClientConnector;
import com.group7.localtestserver.TestServer;

public class MainActivity extends AppCompatActivity {

    Button btnCon;
    SensorManager sm;
    ShakeListener shakeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCon = findViewById(R.id.btn_con);

        //Start Shake Listener
        shakeListener = new ShakeListener(getSupportFragmentManager());
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(shakeListener.newSensorListener(), sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);


        //Server Start
        TestServer testServer = new TestServer();
        testServer.startServer();


    }

    @Override
    protected void onStart() {
        super.onStart();

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ClientConnector temp = new ClientConnector();
                        temp.connect();
                    }
                }).start();
            }
        });

        //Board board = new Board();
        //board.getActionField().pickCard(ActionType.BURGGRABEN);


    }
}
