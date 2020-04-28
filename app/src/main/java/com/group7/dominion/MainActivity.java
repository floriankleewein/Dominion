package com.group7.dominion;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.floriankleewein.commonclasses.Board.Board;
import com.floriankleewein.commonclasses.User.User;
import com.group7.dominion.CheatFunction.ShakeListener;
import com.group7.dominion.Network.ClientConnector;
import com.group7.localtestserver.TestServer;


public class MainActivity extends AppCompatActivity {

    Button btnCreate, btnCon;
    TestServer testServer;
    private Board board;
    SensorManager sm;
    ShakeListener shakeListener;

    //TODO: change this
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Server Start
        testServer = new TestServer();
        testServer.startServer();

        btnCreate = findViewById(R.id.btn_create);
        btnCon = findViewById(R.id.btn_con);


        //Start Shake Listener
        shakeListener = new ShakeListener(getSupportFragmentManager());
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(shakeListener.newSensorListener(), sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

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
                        //TODO: get rid of logic here!! Service
                        EditText editText = findViewById(R.id.inputName);
                        String userName = editText.getText().toString();
                        User user = new User(userName);

                        if (testServer.getGame().addPlayer(user)) {

                            Log.d("GAME", "Player " + user.getUserName() + " added to Dominion!");

                            temp.connect();
                            startActivity(new Intent(MainActivity.this, startGameActivity.class));

                        } else {

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
        checkButtons();
        //Board board = new Board();
        //board.getActionField().pickCard(ActionType.BURGGRABEN);
    }

    /*public void sendName(View v){
        Intent intent = new Intent(this, CreateOrJoinActivity.class);
        EditText editText = findViewById(R.id.inputName);
        String name = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, name);
        startActivity(intent);
    }*/


    public void checkButtons() {
        if (testServer.hasGame() == false) {
            btnCreate.setEnabled(true);
            btnCon.setEnabled(false);
        } else {
            btnCreate.setEnabled(false);
            btnCon.setEnabled(true);
        }
    }

    public Board getBoard() {
        return board;
    }
}
