package com.floriankleewein.dominion;

import android.util.Log;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class KryoServer {
Server server;

    public KryoServer() {
        server = new Server();
    }
    public void startServer() {
        server.start();
        try {
            server.bind(53217, 53217);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof SomeRequest) {
                    SomeRequest request = (SomeRequest) object;
                    Log.d("RESPONSE", "received: " + object.getClass().getName());

                    SomeResponse response = new SomeResponse();
                    response.text = "TEST from Server";
                    connection.sendTCP(response);
                }
            }
        });
    }
}
