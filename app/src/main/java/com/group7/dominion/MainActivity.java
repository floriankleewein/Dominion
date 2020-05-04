package com.group7.dominion;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.floriankleewein.commonclasses.Board.Board;
import com.floriankleewein.commonclasses.Network.AddPlayerNameErrorMsg;
import com.floriankleewein.commonclasses.Network.AddPlayerSizeErrorMsg;
import com.floriankleewein.commonclasses.Network.AddPlayerSuccessMsg;
import com.floriankleewein.commonclasses.Network.StartGameMsg;
import com.group7.dominion.CheatFunction.ShakeListener;
import com.group7.dominion.Network.ClientConnector;


public class MainActivity extends AppCompatActivity {

    Button btnCreate, btnJoin;
    private Board board;
    SensorManager sm;
    ShakeListener shakeListener;
    ClientConnector client;

    //TODO: change this
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreate = findViewById(R.id.btn_create);
        btnJoin = findViewById(R.id.btn_join);

        //Start Shake Listener
        shakeListener = new ShakeListener(getSupportFragmentManager());
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(shakeListener.newSensorListener(), sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStart() {
        super.onStart();

        client = new ClientConnector();
        checkButtons();

        client.registerCallback(StartGameMsg.class,(msg->{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkButtons();
                    }
                });
        }));


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        client.connect();
                        client.startGame();
                    }
                });

                thread.start();
            }
        });

        client.registerCallback(AddPlayerSuccessMsg.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView textView = findViewById(R.id.nameCheckFeedback);
                    textView.setText("Spieler erfolgreich hinzugefügt!");
                    try {
                        wait(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(MainActivity.this, StartGameActivity.class));
                }
            });
        }));

        client.registerCallback(AddPlayerNameErrorMsg.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView textView = findViewById(R.id.nameCheckFeedback);
                    textView.setText("Name wird bereits verwendet. Bitte wähle einen anderen.");
                }
            });
        }));

        client.registerCallback(AddPlayerSizeErrorMsg.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView textView = findViewById(R.id.nameCheckFeedback);
                    textView.setText("Maximale Spielerzahl bereits erreicht. Du kannst nicht beitreten.");
                }
            });
        }));

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Thread thread = new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        EditText editText = findViewById(R.id.inputName);
                        String userName = editText.getText().toString();
                        client.addUser(userName);
                    }
                });
               thread.start();
            }
        });

        //Board board = new Board();
        //board.getActionField().pickCard(ActionType.BURGGRABEN);
    }

    /*public void sendName(View v){
        Intent intent = new Intent(this, startGameActivity.class);
        EditText editText = findViewById(R.id.inputName);
        String name = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, name);
        startActivity(intent);
    }*/


    public void checkButtons() {
        if (client.hasGame() == false) {
            btnCreate.setEnabled(true);
            btnJoin.setEnabled(false);
        } else {
            btnCreate.setEnabled(false);
            btnJoin.setEnabled(true);
        }
    }

    public Board getBoard() {
        return board;
    }
}
