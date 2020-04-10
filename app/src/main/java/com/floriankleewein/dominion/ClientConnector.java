package com.floriankleewein.dominion;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class ClientConnector {

    private Client client;

    ClientConnector(){
        this.client = new Client();
    }

    public void connect() throws IOException {
        client.start();
        client.connect(5000, "143.205.174.196", 53200, 53200);

        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof SomeResponse) {
                    SomeResponse response = (SomeResponse)object;
                    System.out.println(response.text);
                }
            }
        });
    }
}


