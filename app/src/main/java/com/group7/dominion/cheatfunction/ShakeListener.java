package com.group7.dominion.cheatfunction;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;


public class ShakeListener {
    private float acelVal;
    private float acelLast;
    private float shake;
    private CheatAlert cheatAlert;
    private FragmentManager fragmentManager;
    private String username;
    private List<String> names;

    public ShakeListener(FragmentManager fragmentManager, String username, ArrayList<String> names) {
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
        cheatAlert = new CheatAlert();
        this.fragmentManager = fragmentManager;
        this.username = username;
        this.names = names;
    }


    public SensorEventListener newSensorListener() {

        final SensorEventListener sensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];


                acelLast = acelVal;
                acelVal = (float) Math.sqrt((x * x + y * y + z * z));
                float delta = acelVal - acelLast;
                shake = shake * 0.9f + delta;

                if (shake > 4 && !cheatAlert.isAdded()) {
                    cheatAlert.setName(username);
                    cheatAlert.setNamesList(names);
                    cheatAlert.show(fragmentManager, "cheat");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        return sensorListener;
    }
}