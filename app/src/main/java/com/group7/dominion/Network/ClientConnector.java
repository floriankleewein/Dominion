package com.group7.dominion.Network;

import android.util.Log;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.AddPlayerMsg;
import com.floriankleewein.commonclasses.Network.BaseMessage;
import com.floriankleewein.commonclasses.Network.GameInformationMsg;
import com.floriankleewein.commonclasses.Network.NetworkInformationMsg;
import com.floriankleewein.commonclasses.Network.StartGameMsg;

import java.io.IOException;

public class ClientConnector {
    private final String Tag = "CLIENT-CONNECTOR"; // Debugging only
    private static final String SERVER_IP = "143.205.174.196";
    private static final int SERVER_PORT = 53217;
    private Client client;
    private boolean hasGame = false;


    public ClientConnector() {
        this.client = new Client();
    }

    public void registerClass(Class regClass) {
        client.getKryo().register(regClass);
    }

    public void connect() {
        registerClass(BaseMessage.class);
        registerClass(MessageClass.class);
        registerClass(GameInformationMsg.class);
        registerClass(NetworkInformationMsg.class);
        registerClass(Game.class);
        registerClass(StartGameMsg.class);
        registerClass(AddPlayerMsg.class);
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
        StartGameMsg startMsg = new StartGameMsg();
        client.sendTCP(startMsg);
        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof StartGameMsg) {
                    StartGameMsg recStartMsg = (StartGameMsg) object;
                    hasGame = recStartMsg.isHasGame();
                    Log.d(Tag, "Created/Received Game." + hasGame);
                }
            }
        });
    }

    public String[] addUser(String playerName){
        AddPlayerMsg addPlayerMsg = new AddPlayerMsg();
        addPlayerMsg.setPlayerName(playerName);
        client.sendTCP(addPlayerMsg);
        final String[] returnMsg = new String[1];
        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof AddPlayerMsg) {
                    AddPlayerMsg ms = (AddPlayerMsg) object;
                    if(ms.isPlayerAdded()){
                        returnMsg[0] = "Player added successfully";
                    }else{
                        returnMsg[0] = "Player not added(Game full or names already in use)";
                    }
                }
            }

        });
        return returnMsg;
    }

    public boolean hasGame() {
        return hasGame;
    }
}


