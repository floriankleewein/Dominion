package com.group7.dominion;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.floriankleewein.commonclasses.Network.HasCheatedMessage;
import com.esotericsoftware.kryonet.Client;

import com.floriankleewein.commonclasses.Game;

import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.floriankleewein.commonclasses.Network.SuspectMessage;
import com.floriankleewein.commonclasses.Network.UpdatePlayerNamesMsg;
import com.google.android.material.tabs.TabLayout;


import com.group7.dominion.CheatFunction.ShakeListener;

import java.util.ArrayList;


public class GameActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    //private Toolbar toolbar;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(viewPagerAdapter);





    }




    @Override
    protected void onStart() {
        super.onStart();

        // Handle communication with Server, only send updated to server whenever card is played etc.
    }
}
