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
    private String Username;
    private List<String> names;

    public ShakeListener(FragmentManager fragmentManager, String username, List<String> names) {
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
        cheatAlert = new CheatAlert();
        this.fragmentManager = fragmentManager;
        this.Username = username;
        this.names = names;
    }

    /**@Author Maurer Florian
     * x,y,z are the coordinates from the Phone. If they change, they method will detected it and open the Cheat Alert Fragment
     *
     * @return the Sensor Listener for Dominion Activity
     */

    public SensorEventListener newSensorListener() {
        return new SensorEventListener() {
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
                    cheatAlert.setName(Username);
                    cheatAlert.setNamesList(names);
                    cheatAlert.show(fragmentManager, "cheat");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
}