package com.floriankleewein.dominion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.floriankleewein.localtestserver.TestServer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity{

    Button btnCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCon = findViewById(R.id.btn_con);

        //Server Start
        TestServer testServer = new TestServer();
        try {
            testServer.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ClientConnector temp = new ClientConnector();
                        try {
                            temp.connect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
