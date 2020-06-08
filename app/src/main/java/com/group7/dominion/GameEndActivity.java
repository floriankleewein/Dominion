package com.group7.dominion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.floriankleewein.commonclasses.network.ClientConnector;

/**
 * @Author Maurer Florian
 * Activity shows the winner of the current game.
 */


public class GameEndActivity extends AppCompatActivity {
    private String winner;
    private TextView textViewWinner;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);
        btn = findViewById(R.id.backToMainActivitybtn);
        textViewWinner = findViewById(R.id.winnerView);
        winner = getIntent().getStringExtra("winner");
    }

    @Override
    protected void onStart() {
        super.onStart();
        textViewWinner.setText(winner);
        ClientConnector.getClientConnector().sendsetGameNull();
        Intent i = new Intent(this, MainActivity.class);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
    }
}
