package com.group7.dominion.Network;

import android.util.Log;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.AddPlayerNameErrorMsg;
import com.floriankleewein.commonclasses.Network.AddPlayerSizeErrorMsg;
import com.floriankleewein.commonclasses.Network.AddPlayerSuccessMsg;
import com.floriankleewein.commonclasses.Network.BaseMessage;
import com.floriankleewein.commonclasses.Network.GameInformationMsg;
import com.floriankleewein.commonclasses.Network.NetworkInformationMsg;
import com.floriankleewein.commonclasses.Network.StartGameMsg;
import com.floriankleewein.commonclasses.User.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientConnector {
    private final String Tag = "CLIENT-CONNECTOR"; // Debugging only
    private static final String SERVER_IP = "143.205.174.196";
    private static final int SERVER_PORT = 53217;
    private Client client;
    private boolean hasGame = false;
    private Callback<StartGameMsg> callback;
    Map<Class, Callback<BaseMessage>> callbackMap = new HashMap<>();

    public ClientConnector() {
        this.client = new Client();
    }

    public void registerClass(Class regClass) {
        client.getKryo().register(regClass);
    }

    public void connect() {
        // Register classes
        registerClass(BaseMessage.class);
        registerClass(MessageClass.class);
        registerClass(GameInformationMsg.class);
        registerClass(NetworkInformationMsg.class);
        registerClass(Game.class);
        registerClass(StartGameMsg.class);
        registerClass(AddPlayerSuccessMsg.class);
        registerClass(ArrayList.class);
        registerClass(User.class);
        // start client
        client.start();

        //connects aau server
        try {
            //client.connect(5000, InetAddress.getLocalHost(), 8080); // local server
            client.connect(5000, SERVER_IP, SERVER_PORT);   // Uni server
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(Tag, "Connection-Status: " + client.isConnected());

        MessageClass ms = new MessageClass();
        ms.setMessage("Hello Server!");
        client.sendTCP(ms);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof MessageClass) {
                    MessageClass ms = (MessageClass) object;
                    Log.d(Tag, "Received response: " + ms.getMessage());
                }
            }
        });
    }

    public void startGame() {
        Log.d(Tag, "Connection-Status: " + client.isConnected());
        final StartGameMsg startMsg = new StartGameMsg();
        client.sendTCP(startMsg);
        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof StartGameMsg) {
                    StartGameMsg recStartMsg = (StartGameMsg) object;
                    hasGame = recStartMsg.isHasGame();
                    callbackMap.get(StartGameMsg.class).callback(recStartMsg);
                    Log.d(Tag, "Created/Received Game." + hasGame);
                }
            }
        });
    }

    public void addUser(String playerName){
        AddPlayerSuccessMsg addPlayerMsg = new AddPlayerSuccessMsg();
        addPlayerMsg.setPlayerName(playerName);
        client.sendTCP(addPlayerMsg);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof AddPlayerSuccessMsg) {
                    AddPlayerSuccessMsg ms = (AddPlayerSuccessMsg) object;
                    if(ms.getFeedbackUI() == 0){
                        callbackMap.get(AddPlayerSuccessMsg.class).callback(ms);
                    }else if(ms.getFeedbackUI() == 1){
                        callbackMap.get(AddPlayerNameErrorMsg.class).callback(ms);
                    }else if(ms.getFeedbackUI() == 2){
                        callbackMap.get(AddPlayerSizeErrorMsg.class).callback(ms);
                    }

                }
            }

        });
    }

    public boolean hasGame() {
        return hasGame;
    }

    public Client getClient(){
        return client;
    }

    public void registerCallback(Class c, Callback<BaseMessage> callback) {
        this.callbackMap.put(c, callback);
    }
}


