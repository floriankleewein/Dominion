package com.group7.dominion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.floriankleewein.commonclasses.Network.HasCheatedMessage;
import com.esotericsoftware.kryonet.Client;
import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.google.android.material.tabs.TabLayout;
import com.group7.dominion.Chat.ViewPagerAdapter;
import com.group7.dominion.CheatFunction.ShakeListener;

public class GameActivity extends AppCompatActivity {
    private SensorManager sm;
    private ShakeListener shakeListener;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Dominion");
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(viewPagerAdapter);


        System.out.println(getUsername() + " is here");
        shakeListener = new ShakeListener(getSupportFragmentManager(), getUsername());
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(shakeListener.newSensorListener(), sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);



    }



    public String getUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("USERNAME", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("us", null);
        return str;
    }

    @Override
    protected void onStart(){
        super.onStart();

        ClientConnector clientConnector = ClientConnector.getClientConnector();

    }
}
