package com.group7.dominion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.group7.dominion.Chat.ViewPagerAdapter;
import com.group7.dominion.CheatFunction.ShakeListener;
import com.group7.dominion.Network.ClientConnector;

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


        shakeListener = new ShakeListener(getSupportFragmentManager());
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(shakeListener.newSensorListener(), sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        System.out.println(getUsername() + " is here");
    }

    public String getUsername () {
        String str = "";
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null){
            str = extras.getString("USERNAME");
        }
        return str;
    }

    protected void onStart () {
        super.onStart();
        
    }
}
