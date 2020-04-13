package com.floriankleewein.dominion;

import android.util.Log;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class ClientConnector {
    private final String TAG = "CLIENT-CONNECTOR";
    private static final String SERVER_IP = "143.205.174.196";
    private static final int SERVER_PORT = 53217;
    private Client client;

    public ClientConnector() {
        this.client = new Client();
    }

    public void registerClass(Class regClass) {
        client.getKryo().register(regClass);
    }

    public void connect() throws IOException {
        registerClass(MessageClass.class);
        client.start();
        client.connect(5000, SERVER_IP, SERVER_PORT);
        Log.d(TAG, "Connection-Status: " + client.isConnected());

        MessageClass ms = new MessageClass();
        ms.setMessage("Hello Server!");

        client.sendTCP(ms);
        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if(object instanceof MessageClass) {
                    MessageClass ms = (MessageClass) object;
                    Log.d(TAG, "received: " + ms.getMessage());
                }
            }
        });
    }
}


