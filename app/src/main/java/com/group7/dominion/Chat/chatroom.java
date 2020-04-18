package com.group7.dominion.Chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.group7.dominion.R;

public class chatroom extends AppCompatActivity {


    private Button sendMessage;
    private EditText inputMessage;
    private ScrollView scrollMessages;
    private TextView displayMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        InitializeFields();

    }

    private void InitializeFields() {

        sendMessage = (Button) findViewById(R.id.sendButton_chat);
        inputMessage = (EditText) findViewById(R.id.messageInput_Chat);
        scrollMessages = (ScrollView) findViewById(R.id.scroll_chat);
        displayMessage = (TextView) findViewById(R.id.display_chat);

    }
}
