package com.group7.dominion.Network;

import android.util.Log;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.group7.localtestserver.MessageClass;

import java.io.IOException;

public class KryoServer {
    Server server;

    public KryoServer() {
        server = new Server();
    }

    public void registerClass(Class regClass) {
        server.getKryo().register(regClass);
    }

    public void startServer() {
        try {
            registerClass(SomeRequest.class);
            registerClass(MessageClass.class);
            server.bind(53217, 53217);
            server.start();
            Log.d("START SERVER SUC", "startServer: Success!");
        } catch (IOException e) {
            Log.d("START SERVER EXC", "startServer: Failed! ");
            e.printStackTrace();
        }
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof SomeRequest) {
                    SomeRequest request = (SomeRequest) object;
                    Log.d("RESPONSE", "received: " + object.getClass().getName());

                    MessageClass response = new MessageClass();
                    response.setMessage("TEST from Server");
                    connection.sendTCP(response);
                }
            }
        });
    }

    public void broadcastToClients(MessageClass response) {
        for (Connection con : server.getConnections()) {
            con.sendTCP("UPDATE AN ALLE");
        }
    }
}
