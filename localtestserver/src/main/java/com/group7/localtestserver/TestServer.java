package com.group7.localtestserver;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.AddPlayerSuccessMsg;
import com.floriankleewein.commonclasses.Network.BaseMessage;
import com.floriankleewein.commonclasses.Network.StartGameMsg;
import com.floriankleewein.commonclasses.Network.GameInformationMsg;
import com.floriankleewein.commonclasses.Network.NetworkInformationMsg;
import com.floriankleewein.commonclasses.User.User;

import java.io.IOException;
import java.util.ArrayList;

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
        //Register classes
        registerClass(BaseMessage.class);
        registerClass(MessageClass.class);
        registerClass(GameInformationMsg.class);
        registerClass(NetworkInformationMsg.class);
        registerClass(Game.class);
        registerClass(StartGameMsg.class);
        registerClass(AddPlayerSuccessMsg.class);
        registerClass(ArrayList.class);
        registerClass(User.class);
        //Start Server
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
                else if(object instanceof StartGameMsg){
                    startGame();
                    StartGameMsg startGameMsg = (StartGameMsg) object;
                    startGameMsg.setGame(getGame());
                    startGameMsg.setHasGame(hasGame());
                    con.sendTCP(startGameMsg);
                }
                else if(object instanceof AddPlayerSuccessMsg){
                    AddPlayerSuccessMsg addPlayerMsg = (AddPlayerSuccessMsg) object;
                    String name = addPlayerMsg.getPlayerName();
                    User player = new User(name);
                    /*if(game.addPlayer(player)) {
                        addPlayerMsg.setUser(player);
                        addPlayerMsg.setPlayerAdded(true);
                    }*/
                    if(game.checkSize()){
                        if(game.checkName(name)){
                            game.addPlayer(player);
                            addPlayerMsg.setFeedbackUI(0);
                            addPlayerMsg.setPlayerAdded(true);
                        }else{
                            addPlayerMsg.setFeedbackUI(1);
                        }
                    }else{
                        addPlayerMsg.setFeedbackUI(2);
                    }
                    con.sendTCP(addPlayerMsg);
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
