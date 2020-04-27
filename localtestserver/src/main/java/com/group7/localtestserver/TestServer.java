package com.group7.localtestserver;

import android.util.Log;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.Game_Information;
import com.floriankleewein.commonclasses.Network.Network_Information;

import java.io.IOException;

public class TestServer {

    private Server server;
    private Game game;
    private boolean hasGame = false;
    private final String Tag = "TEST-SERVER"; // debugging only

    public TestServer() {
        server = new Server();
    }

    public void startServer() {
        Log.d(Tag, "Running Server!");
        registerClass(MessageClass.class);
        registerClass(Game_Information.class);
        registerClass(Network_Information.class);
        server.start();

        try {
            server.bind(53217);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof MessageClass) {
                    MessageClass recMessage = (MessageClass) object;
                    Log.d(Tag, "Received message: " + recMessage.getMessage());

                    MessageClass sendMessage = new MessageClass();
                    sendMessage.setMessage("Hello Client! " + " from: " + con.getRemoteAddressTCP().getHostString());

                    con.sendTCP(sendMessage);
                }
            }
        });
    }

    public void registerClass(Class regClass) {
        server.getKryo().register(regClass);
    }

    public void startGame() {

        game = Game.getGame();
        hasGame = true;
        Log.d("GAME", "game instanced - started");
    }

    public boolean hasGame() {
        return hasGame;
    }

    public Game getGame() {
        return game;
    }

}
