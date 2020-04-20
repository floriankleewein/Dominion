package com.group7.dominion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.group7.dominion.Board.Board;
import com.group7.dominion.Cards.ActionType;
import com.group7.dominion.Network.ClientConnector;
import com.group7.localtestserver.TestServer;


public class MainActivity extends AppCompatActivity {

    Button btnCreate, btnCon;
    ImageButton btnSendName;
    TestServer testServer;

    //TODO: change this
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCreate = findViewById(R.id.btn_create);
        btnCon = findViewById(R.id.btn_con);
        btnSendName = findViewById(R.id.btn_sendName);

        //Server Start
        testServer = new TestServer();
        testServer.startServer();
    }

    @Override
    protected void onStart() {
        super.onStart();

        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                testServer.startGame();
            }
        });

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //setContentView(R.layout.activity_main);
                        ClientConnector temp = new ClientConnector();
                        temp.connect();
                    }
                }).start();
            }
        });

        btnSendName.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v){
                sendMessage(findViewById(R.layout.activity_create_or_join));
            }
        });

        //Board board = new Board();
        //board.getActionField().pickCard(ActionType.BURGGRABEN);


    }

    public void sendMessage(View v){
        Intent intent = new Intent(this, CreateOrJoinActivity.class);
        EditText editText = findViewById(R.id.inputName);
        String name = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, name);
        startActivity(intent);
    }
}
