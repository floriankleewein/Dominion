package com.floriankleewein.dominion;

import android.util.Log;

import com.esotericsoftware.kryonet.Client;

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
//        registerClass(SomeRequest.class);
        client.start();
        Log.d(TAG, "connect: To my server");
        client.connect(5000, SERVER_IP, SERVER_PORT);
        Log.d(TAG, "connect: Successful!");
//        MessageClass ms = new MessageClass();
//        ms.setMessage("Hello Server!");
//        client.sendTCP(ms);
    }
}


