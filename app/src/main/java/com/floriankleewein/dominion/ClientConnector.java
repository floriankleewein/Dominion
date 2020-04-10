package com.floriankleewein.dominion;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class ClientConnector {

    private Client client;

    ClientConnector(){
        this.client = new Client();
    }

    public void connect() throws IOException {
        client.start();
        client.connect(5000, "143.205.174.196", 53217, 53217);
    }
}


