package com.floriankleewein.dominion;

import android.util.Log;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class ClientConnector {
    private final String TAG = "CLIENT-CONNECTOR";
    private Client client;

    public ClientConnector(){
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
        client.connect(5000, "[143.205.174.196", 53217, 53217);
        Log.d(TAG, "connect: Successfull!");
        MessageClass ms = new MessageClass();
        ms.setMessage("Hello Server!");
        client.sendTCP(ms);
    }
}


