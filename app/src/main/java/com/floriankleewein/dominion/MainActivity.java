package com.floriankleewein.dominion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Test Steiner Emanuel Rep Branch
    }


    @Override
    protected void onStart() {
        super.onStart();
        ClientConnector temp = new ClientConnector();
        try {
            Log.d("test1", "testmsg1");
            temp.connect();
            Log.d("test2", "testmsg2");
        } catch (IOException e) {
            Log.d("test3", "testmsg3");
            e.printStackTrace();
        }
        Log.d("test4", "testmsg4");
    }
}
