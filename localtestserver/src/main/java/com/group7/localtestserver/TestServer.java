package com.group7.localtestserver;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.Game_Information;
import com.floriankleewein.commonclasses.Network.Network_Information;
import com.floriankleewein.commonclasses.Network.Start_Game;

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
        System.out.println(Tag + ", Running Server!");
        registerClass(MessageClass.class);
        registerClass(Game_Information.class);
        registerClass(Network_Information.class);
        registerClass(Start_Game.class);
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
                    System.out.println(Tag + ", Received Message " + recMessage.getMessage());

                    MessageClass sendMessage = new MessageClass();
                    sendMessage.setMessage("Hello Client! " + " from: " + con.getRemoteAddressTCP().getHostString());

                    con.sendTCP(sendMessage);
                }
                else if(object instanceof Start_Game){
                    startGame();
                    Start_Game startGameMsg = (Start_Game) object;
                    startGameMsg.setGame(getGame());
                    startGameMsg.setHasGame(hasGame());
                    con.sendTCP(startGameMsg);
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
        System.out.println("GAME, game instanced - started");
    }

    public boolean hasGame() {
        return hasGame;
    }

    public Game getGame() {
        return game;
    }

}
