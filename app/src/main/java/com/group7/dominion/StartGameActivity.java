package com.group7.dominion;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.floriankleewein.commonclasses.User.User;
import com.group7.dominion.Network.ClientConnector;
import com.group7.localtestserver.TestServer;

public class StartGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_join);
        ListView playerNamesListView = findViewById(R.id.playerNamesListView);

        /*ArrayAdapter<User> arrayAdapter
                = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1 , );*/
    //TODO: adapter für die listView. wie kommt man an die userliste?
    }

    @Override
    protected void onStart() {
        super.onStart();



        //Intent intent = getIntent();
        //String msg = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //TextView textView = findViewById(R.id.testTextView);
        //textView.setText(msg);
    }
}