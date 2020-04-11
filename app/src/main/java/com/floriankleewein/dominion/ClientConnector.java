package com.floriankleewein.dominion;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class ClientConnector {

    private Client client;

    public ClientConnector(){
        this.client = new Client();
    }

    public void registerClass(Class regClass) {
        client.getKryo().register(regClass);
    }

    public void connect() throws IOException {
        registerClass(SomeRequest.class);
        registerClass(SomeResponse.class);
        client.start();
        client.connect(5000, "143.205.174.196", 53217, 53217);
    }
}


