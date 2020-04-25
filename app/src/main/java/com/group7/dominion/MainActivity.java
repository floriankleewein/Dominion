package com.group7.dominion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.floriankleewein.commonclasses.User.User;
import com.group7.dominion.Board.Board;
import com.group7.dominion.Network.ClientConnector;
import com.group7.localtestserver.TestServer;

public class MainActivity extends AppCompatActivity {

    Button btnCreate, btnCon;
    TestServer testServer;
    private Board board;


    //TODO: change this
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCon = findViewById(R.id.btn_con);

        //Server Start
        testServer = new TestServer();
        testServer.startServer();

        btnCreate = findViewById(R.id.btn_create);
        btnCon = findViewById(R.id.btn_con);

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

                        EditText editText = findViewById(R.id.inputName);
                        String userName = editText.getText().toString();
                        if(testServer.getGame().checkName(userName)){
                            User user = new User(userName);
                            testServer.getGame().addPlayer(user);
                            Log.d("GAME", "Player " + user.getUserName() + " added to Dominion!");

                            temp.connect();
                            startActivity(new Intent(MainActivity.this, startGameActivity.class));

                        }else{

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Stuff that updates the UI
                                    TextView textView = findViewById(R.id.nameCheckFeedback);
                                    textView.setText("Name nicht verfügbar. Bitte wähle einen anderen!");
                                }
                            });



                            Log.d("GAME", "ERROR: Player " + userName + " already exists!");
                        }
                        checkButtons();
                    }
                }).start();
            }
        });

        board = new Board();
        //UserCards userCards = new UserCards(this);

        checkButtons();
    }


    /*public void sendName(View v){
        Intent intent = new Intent(this, CreateOrJoinActivity.class);
        EditText editText = findViewById(R.id.inputName);
        String name = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, name);
        startActivity(intent);
    }*/


    public void checkButtons() {
        if(testServer.hasGame() == false){
            btnCreate.setEnabled(true);
            btnCon.setEnabled(false);
        }else {
            btnCreate.setEnabled(false);
            btnCon.setEnabled(true);
        }
    }

    public Board getBoard() {
        return board;
    }
}
