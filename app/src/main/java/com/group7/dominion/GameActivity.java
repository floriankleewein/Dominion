package com.group7.dominion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

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
}
