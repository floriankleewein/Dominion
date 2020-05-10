package com.group7.dominion;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.google.android.material.tabs.TabLayout;
import com.group7.dominion.Chat.ViewPagerAdapter;

public class GameActivity extends AppCompatActivity {

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Handle communication with Server, only send updated to server whenever card is played etc.
        ClientConnector clientConnector = ClientConnector.getClientConnector();
        Game clientGame = clientConnector.getGame();
        clientConnector.startGame(); // Send Server Message to start game logic
        // TODO display playerlist -> Check features
        // TODO create board and display cards
    }
}
