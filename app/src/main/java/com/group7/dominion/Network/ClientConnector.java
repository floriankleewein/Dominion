package com.group7.dominion.Network;

import android.util.Log;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.floriankleewein.commonclasses.Network.Game_Information;
import com.floriankleewein.commonclasses.Network.Network_Information;

import java.io.IOException;

public class ClientConnector {
    private final String Tag = "CLIENT-CONNECTOR"; // Debugging only
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
        registerClass(MessageClass.class);
        registerClass(Game_Information.class);
        registerClass(Network_Information.class);
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
}


