package com.group7.dominion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.floriankleewein.commonclasses.Chat.ChatMessage;
import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.group7.dominion.Chat.ChatFragment;
import android.widget.Toast;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.floriankleewein.commonclasses.Network.HasCheatedMessage;
import com.floriankleewein.commonclasses.Network.SuspectMessage;
import com.floriankleewein.commonclasses.Network.UpdatePlayerNamesMsg;
import com.group7.dominion.CheatFunction.ShakeListener;

import java.util.ArrayList;

public class DominionActivity extends AppCompatActivity implements ChatFragment.OnChatMessageArrivedListener {

    private Button chatButton;
    private FrameLayout fragmentContainer;
    private ChatFragment chatFragment;
    private FragmentTransaction trans;
    private ClientConnector clientConnector;

    private SensorManager sm;
    private ShakeListener shakeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dominion);

        chatButton = findViewById(R.id.chat_Button);
        fragmentContainer = findViewById(R.id.chatFragmentContainer);

        this.clientConnector = ClientConnector.getClientConnector();

        chatButton.setOnClickListener(view -> openFragment());

        sendUpdateMessage();
        ArrayList<String> names = new ArrayList<>();
        ClientConnector.getClientConnector().registerCallback(UpdatePlayerNamesMsg.class, (msg -> {
            runOnUiThread(() -> {
                names.clear();
                names.addAll(((UpdatePlayerNamesMsg) msg).getNameList());
            });
        }));

        shakeListener = new ShakeListener(getSupportFragmentManager(), getUsername(), names);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(shakeListener.newSensorListener(), sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Handle communication with Server, only send updated to server whenever card is played etc.

        ClientConnector clientConnector = ClientConnector.getClientConnector();
        Game clientGame = clientConnector.getGame();
        //clientConnector.startGame(); // Send Server Message to start game logic
        // TODO display playerlist -> Check features
        // TODO create board and display cards

        clientConnector.registerCallback(HasCheatedMessage.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String CheaterName = ((HasCheatedMessage) msg).getName();
                    Toast.makeText(getApplicationContext(), CheaterName + " hat eine zusätzliche Karte gezogen...", Toast.LENGTH_SHORT).show();
                }
            });
        }));

        clientConnector.registerCallback(SuspectMessage.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String SuspectedUser = ((SuspectMessage) msg).getSuspectedUserName();
                    String Username = ((SuspectMessage) msg).getUserName();
                    Toast.makeText(getApplicationContext(), Username + " glaubt, dass " + SuspectedUser + " geschummelt hat", Toast.LENGTH_SHORT).show();
                }
            });
        }));

        clientConnector.registerCallback(ChatMessage.class, msg -> {
            runOnUiThread(() -> {
                String chatMessage = ((ChatMessage) msg).getMessage();
                Toast.makeText(getApplicationContext(), "Nachricht: " + chatMessage, Toast.LENGTH_SHORT).show();
            });
        });
    }

    public void sendUpdateMessage() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                ClientConnector.getClientConnector().updatePlayerNames();
            }
        });
        th.start();
    }

    public String getUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("USERNAME", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("us", null);
        return str;
    }



    public void openFragment() {
        if(this.chatFragment == null) {
            this.chatFragment = ChatFragment.newInstance();
        }
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                    R.anim.enter_from_right, R.anim.exit_to_right);

            trans.addToBackStack(null);

        trans.add(R.id.chatFragmentContainer, chatFragment, "CHAT_FRAGMENT").commit();

    }

    @Override
    public void onChatMessageArrived(String msg) {
        Toast.makeText(getApplicationContext(), "Nachricht: " + msg, Toast.LENGTH_SHORT).show();
        //this.trans.hide(chatFragment);
        onBackPressed();
    }
}
