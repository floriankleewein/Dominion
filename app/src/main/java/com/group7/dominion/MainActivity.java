package com.group7.dominion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.floriankleewein.commonclasses.network.AddPlayerNameErrorMsg;
import com.floriankleewein.commonclasses.network.AddPlayerSizeErrorMsg;
import com.floriankleewein.commonclasses.network.AddPlayerSuccessMsg;
import com.floriankleewein.commonclasses.network.CheckButtonsMsg;
import com.floriankleewein.commonclasses.network.ClientConnector;

public class MainActivity extends AppCompatActivity {

    Button btnCreate;
    Button btnJoin;
    Button btnReset;
    ClientConnector client;
    SharedPreferences sharedPreferences;

    private static final String PLAYER_ADDED_SUCCESSFULLY = "Spieler wurde erfolgreich hinzugefügt!";
    private static final String NAME_ALREADY_IN_USE = "Name wird bereits verwendet. Bitte wähle einen anderen!";
    private static final String PLAYER_MAX_REACHED = "Maximale Spielerzahl bereits erreicht. Du kannst nicht beitreten!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreate = findViewById(R.id.btn_create);
        btnJoin = findViewById(R.id.btn_join);
        btnReset = findViewById(R.id.btn_reset);
        sharedPreferences = getSharedPreferences("USERNAME", Context.MODE_PRIVATE);

    }

    @Override
    protected void onStart() {
        super.onStart();

        client = ClientConnector.getClientConnector();
        Thread thread = new Thread(() -> {
            client.connect();
            client.checkButtons();
        });
        thread.start();

        client.registerCallback(CheckButtonsMsg.class,(msg->{
            CheckButtonsMsg temp = (CheckButtonsMsg)msg;

            runOnUiThread(() -> {
                btnCreate.setEnabled(temp.getCreateValue());
                btnJoin.setEnabled(temp.getJoinValue());
            });
        }));

        btnCreate.setOnClickListener(v -> {

            Thread thread1 = new Thread(() -> client.createGame());

            thread1.start();
        });


        client.registerCallback(AddPlayerSuccessMsg.class, (msg -> runOnUiThread(() -> {
            TextView textView = findViewById(R.id.nameCheckFeedback);
            textView.setText(PLAYER_ADDED_SUCCESSFULLY);
            startActivity(new Intent(MainActivity.this, StartGameActivity.class));
        })));

        client.registerCallback(AddPlayerNameErrorMsg.class, (msg -> runOnUiThread(() -> {
            TextView textView = findViewById(R.id.nameCheckFeedback);
            textView.setText(NAME_ALREADY_IN_USE);
        })));

        client.registerCallback(AddPlayerSizeErrorMsg.class, (msg -> runOnUiThread(() -> {
            TextView textView = findViewById(R.id.nameCheckFeedback);
            textView.setText(PLAYER_MAX_REACHED);
        })));


        btnJoin.setOnClickListener(v -> {
            addUsernametoPreferences();
            Thread thread12 = new Thread(() -> {
                EditText editText = findViewById(R.id.inputName);
                String userName = editText.getText().toString();
                client.addUser(userName);
            });
            thread12.start();
        });

        btnReset.setOnClickListener(v -> {
            Thread thread13 = new Thread(() -> client.resetGame());
            thread13.start();
        });
    }

    public void addUsernametoPreferences () {
        EditText editText = findViewById(R.id.inputName);
        String userName = editText.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("us", userName);
        editor.commit();
    }
}
