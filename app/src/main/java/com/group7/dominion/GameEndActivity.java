package com.group7.dominion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameEndActivity extends AppCompatActivity {
    private String winner;
    private TextView textViewWinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);
        textViewWinner = findViewById(R.id.winnerView);
        winner = getIntent().getStringExtra("winner");
    }

    @Override
    protected void onStart() {
        super.onStart();
        textViewWinner.setText(winner);
    }
}
