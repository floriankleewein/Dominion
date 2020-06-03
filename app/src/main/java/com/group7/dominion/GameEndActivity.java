package com.group7.dominion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.user.User;

public class GameEndActivity extends AppCompatActivity {
    private String winner;
    private Game game;
    private TextView textViewWinner;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);
        textViewWinner = findViewById(R.id.winnerView);
        linearLayout = findViewById(R.id.linearLayoutGameEnd);
        winner = getIntent().getStringExtra("winner");
       // game = getIntent().getParcelableExtra("game");
    }

    @Override
    protected void onStart() {
        super.onStart();
        textViewWinner.setText(winner);

    }
}
