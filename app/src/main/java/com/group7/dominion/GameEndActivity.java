package com.group7.dominion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.floriankleewein.commonclasses.network.ClientConnector;

/**
 * @Author Maurer Florian
 * Activity shows the winner of the current game.
 */


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
        ClientConnector.getClientConnector().sendsetGameNull();
    }
}
