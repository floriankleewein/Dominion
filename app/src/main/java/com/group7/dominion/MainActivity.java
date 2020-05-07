package com.group7.dominion;

import android.content.Intent;
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
import com.floriankleewein.commonclasses.Network.CreateGameMsg;
import com.floriankleewein.commonclasses.Network.ClientConnector;


public class MainActivity extends AppCompatActivity {

    Button btnCreate, btnJoin, btnReset;
    private Board board;
    ClientConnector client;

    //TODO: rename this
    public static final String EXTRA_MESSAGE = "clientForNextActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreate = findViewById(R.id.btn_create);
        btnJoin = findViewById(R.id.btn_join);
        btnReset = findViewById(R.id.btn_reset);
    }

    @Override
    protected void onStart() {
        super.onStart();

        client = ClientConnector.getClientConnector();
        checkButtons();

        client.registerCallback(CreateGameMsg.class,(msg->{
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
                        client.createGame();
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

                    /*Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                wait(3);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();*/

                    //TODO: FLO: for gamecreation it must be possible to pass the client to the next activity.
                    /*Intent intent = new Intent(MainActivity.this, StartGameActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, client);
                    startActivity(intent);*/
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

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        client.resetGame();
                    }
                });
                thread.start();
            }
        });

        //Board board = new Board();
        //board.getActionField().pickCard(ActionType.BURGGRABEN);
    }

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
