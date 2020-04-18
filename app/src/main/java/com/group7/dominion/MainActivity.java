package com.group7.dominion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.group7.dominion.Chat.chatroom;
import com.group7.dominion.Network.ClientConnector;
import com.group7.localtestserver.TestServer;

public class MainActivity extends AppCompatActivity {

    Button btnCon;
    Button btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCon = findViewById(R.id.btn_con);
        btnChat = findViewById(R.id.chatButton);


        //Server Start
        TestServer testServer = new TestServer();
        testServer.startServer();

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChatroom();
            }
        });

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

    public void openChatroom() {
        Intent intent = new Intent (this, chatroom.class);
        startActivity(intent);
    }

}
