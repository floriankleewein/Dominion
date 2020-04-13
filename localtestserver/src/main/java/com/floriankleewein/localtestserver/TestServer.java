package com.floriankleewein.localtestserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class TestServer {
    private Server server;

    public TestServer() {
        server = new Server();
    }

    public void startServer() throws IOException {
        System.out.println("Running Server!");
        server.getKryo().register(MessageClass.class);
        server.start();
        server.bind(8080);
        server.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if( object instanceof MessageClass) {
                    MessageClass recMessage = (MessageClass) object;
                    System.out.println("Received: " + recMessage.getMessage());

                    MessageClass sendMessage = new MessageClass();
                    sendMessage.setMessage("Received Reply: " + recMessage.getMessage() + " from: " +con.getRemoteAddressTCP().getHostString());

                    con.sendTCP(sendMessage);
                }
            }
        });
    }
}
