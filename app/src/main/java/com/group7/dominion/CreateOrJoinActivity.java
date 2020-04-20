package com.group7.dominion;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.floriankleewein.commonclasses.User.User;
import com.group7.dominion.Network.ClientConnector;
import com.group7.localtestserver.TestServer;

public class CreateOrJoinActivity extends AppCompatActivity {

    Button btnCreate, btnCon;
    TestServer testServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_join);
        btnCreate = findViewById(R.id.btn_create);
        btnCon = findViewById(R.id.btn_con);

        //Server Start
        testServer = new TestServer();
        testServer.startServer();

    }

    @Override
    protected void onStart() {
        super.onStart();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testServer.startGame();
                checkButtons();
            }
        });

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

        Intent intent = getIntent();
        String msg = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = findViewById(R.id.testTextView);
        textView.setText(msg);

        checkButtons();
    }

    public void checkButtons() {
        if(testServer.hasGame() == false){
            btnCreate.setEnabled(true);
            btnCon.setEnabled(false);
        }else {
            btnCreate.setEnabled(false);
            btnCon.setEnabled(true);
        }
    }
}
