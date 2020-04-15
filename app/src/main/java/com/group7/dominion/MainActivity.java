package com.group7.dominion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.group7.dominion.Board.Board;
import com.group7.dominion.Cards.ActionType;
import com.group7.dominion.Network.ClientConnector;
import com.group7.localtestserver.TestServer;

public class MainActivity extends AppCompatActivity {

    Button btnCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCon = findViewById(R.id.btn_con);


        //Server Start
        TestServer testServer = new TestServer();
        testServer.startServer();

    }

    @Override
    protected void onStart() {
        super.onStart();
        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ClientConnector temp = new ClientConnector();
                        temp.connect();
                    }
                }).start();
            }
        });

        //Board board = new Board();
        //board.getActionField().pickCard(ActionType.BURGGRABEN);


    }
}
