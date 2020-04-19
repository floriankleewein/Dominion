package com.group7.dominion.Cheat_Function;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.fragment.app.FragmentManager;


public class ShakeListener {
    private static boolean alreadyCheated = false;
    private float acelVal;
    private float acelLast;
    private float shake;
    private CheatAlert cheat_alert;
    private FragmentManager fragmentManager;

    public ShakeListener(FragmentManager fragmentManager) {
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
        cheat_alert = new CheatAlert();
        this.fragmentManager = fragmentManager;
    }


    public SensorEventListener newSensorListener() {

        final SensorEventListener sensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];


                acelLast = acelVal;
                acelVal = (float) Math.sqrt( (x * x + y * y + z * z));
                float delta = acelVal - acelLast;
                shake = shake * 0.9f + delta;

                if (shake > 4 && !alreadyCheated) {
                    alreadyCheated = true;
                    Log.i("SHAKING!!", "PHONE GET SHAKED!");
                    cheat_alert.show(fragmentManager, "cheat");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        return sensorListener;
    }
}
