package com.group7.dominion;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.group7.dominion.Board.Board;
import com.group7.dominion.Cards.ActionType;
import com.group7.dominion.Cards.Card;
import com.group7.dominion.Network.ClientConnector;
import com.group7.localtestserver.TestServer;

import androidx.appcompat.app.AppCompatActivity;

public class CardActivity extends AppCompatActivity {

    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        LinearLayout linearLayout = findViewById(R.id.rootContainer);

        board = new Board(this);
        board.init();
        Card card = board.getActionField().pickCard(ActionType.BURGGRABEN);
        linearLayout.addView(card.getImageButton());
    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    public Board getBoard() {
        return board;
    }
}
