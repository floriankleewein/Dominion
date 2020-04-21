package com.group7.dominion.Network;

import android.util.Log;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.group7.dominion.Interfaces.GameInfo;
import com.group7.dominion.Interfaces.NetworkInformation;

import java.io.IOException;
import java.net.InetAddress;

public class ClientConnector {
    private final String LogTag = "CLIENT-CONNECTOR";
    private static final String SERVER_IP = "143.205.174.196";
    private static final int SERVER_PORT = 53217;
    private Client client;

    public ClientConnector() {
        this.client = new Client();
    }

    public void registerClass(Class regClass) {
        client.getKryo().register(regClass);
    }

    public void connect() {
        registerClass(NetworkInfo_Imp.class);
        registerClass(GameInfo_Imp.class);
        client.start();

        //connects to localtestserver now
        try {
            client.connect(5000, InetAddress.getLocalHost(), 8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(LogTag, "Connection-Status: " + client.isConnected());

        NetworkInformation msg = new NetworkInfo_Imp();
        msg.setMessage("Hello Server!");
        client.sendTCP(msg);

        GameInfo gameMsg = new GameInfo_Imp();
        gameMsg.setMessage("SPIELZUGINFO EVTL HIER");
        client.sendTCP(gameMsg);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof NetworkInformation) {
                    NetworkInformation rcvMsg = (NetworkInfo_Imp) object;
                    Log.d(LogTag, "Received: " + rcvMsg.getMessage());
                } else if (object instanceof GameInfo_Imp) {
                    GameInfo rcvGameMsg = (GameInfo) object;
                    Log.d(LogTag, "Received: " + rcvGameMsg.getMessage());
                }
            }
        });
    }
}


