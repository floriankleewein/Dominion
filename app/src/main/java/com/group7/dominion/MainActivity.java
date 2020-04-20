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

    ImageButton btnSendName;

    //TODO: change this
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSendName = findViewById(R.id.btn_sendName);
    }

    @Override
    protected void onStart() {
        super.onStart();

        btnSendName.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v){
                sendName(findViewById(R.layout.activity_create_or_join));
            }
        });

        //Board board = new Board();
        //board.getActionField().pickCard(ActionType.BURGGRABEN);
    }

    public void sendName(View v){
        Intent intent = new Intent(this, CreateOrJoinActivity.class);
        EditText editText = findViewById(R.id.inputName);
        String name = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, name);
        startActivity(intent);
    }
}
