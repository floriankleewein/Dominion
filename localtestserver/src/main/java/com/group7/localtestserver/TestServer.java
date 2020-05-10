package com.group7.localtestserver;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.floriankleewein.commonclasses.CheatFunction.CheatService;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.GameLogic.GameHandler;
import com.floriankleewein.commonclasses.Network.ActivePlayerMessage;
import com.floriankleewein.commonclasses.Network.AddPlayerSuccessMsg;
import com.floriankleewein.commonclasses.Network.BaseMessage;

import com.floriankleewein.commonclasses.Network.GetPlayerMsg;
import com.floriankleewein.commonclasses.Network.HasCheatedMessage;
import com.floriankleewein.commonclasses.Network.ReturnPlayersMsg;
import com.floriankleewein.commonclasses.Network.ResetMsg;


import com.floriankleewein.commonclasses.Network.UpdatePlayerNamesMsg;

import com.floriankleewein.commonclasses.Network.CreateGameMsg;
import com.floriankleewein.commonclasses.Network.GameInformationMsg;
import com.floriankleewein.commonclasses.Network.NetworkInformationMsg;
import com.floriankleewein.commonclasses.Network.ResetMsg;
import com.floriankleewein.commonclasses.Network.StartGameMsg;
import com.floriankleewein.commonclasses.User.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestServer {

    private Server server;
    private Game game;
    private boolean hasGame = false;
    private final String Tag = "TEST-SERVER"; // debugging only
    private GameHandler gamehandler;
    private Map<User, Connection> userClientConnectorMap = new HashMap<>();


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
        registerClass(CreateGameMsg.class);
        registerClass(AddPlayerSuccessMsg.class);
        registerClass(ArrayList.class);
        registerClass(User.class);
        registerClass(ResetMsg.class);
        registerClass(StartGameMsg.class);
        registerClass(HasCheatedMessage.class);
        registerClass(ActivePlayerMessage.class);
        registerClass(UpdatePlayerNamesMsg.class);


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
                } else if (object instanceof CreateGameMsg) {
                    createGame();
                    CreateGameMsg startGameMsg = (CreateGameMsg) object;
                    startGameMsg.setGame(getGame());
                    startGameMsg.setHasGame(hasGame());
                    con.sendTCP(startGameMsg);
                } else if (object instanceof AddPlayerSuccessMsg) {
                    AddPlayerSuccessMsg addPlayerMsg = (AddPlayerSuccessMsg) object;
                    String name = addPlayerMsg.getPlayerName();
                    User player = new User(name);
                    /*if(game.addPlayer(player)) {
                        addPlayerMsg.setUser(player);
                        addPlayerMsg.setPlayerAdded(true);
                    }*/
                    if (game.checkSize()) {
                        if (game.checkName(name)) {

                            userClientConnectorMap.put(player, con);

                            game.addPlayer(player);
                            addPlayerMsg.setFeedbackUI(0);
                            addPlayerMsg.setPlayerAdded(true);
                            System.out.println("Player added: " + player.getUserName());
                        } else {
                            addPlayerMsg.setFeedbackUI(1);
                        }
                    } else {
                        addPlayerMsg.setFeedbackUI(2);
                    }
                    con.sendTCP(addPlayerMsg);
                } else if (object instanceof ResetMsg) {
                    System.out.println("Received Reset Message.");
                    reset();
                    //ResetMsg msg = (ResetMsg) object;

                } else if (object instanceof StartGameMsg) {
                    StartGameMsg msg = new StartGameMsg();
                    //Check if game started successfully, and notify client
                    if (setupGame()) {
                        msg.setFeedbackUI(0);
                        msg.setGame(getGame());
                        // Send message to all clients, TODO they need to be in lobby
                        for (Connection c : server.getConnections()) {
                            c.sendTCP(msg);
                        }
                        ActivePlayerMessage activePlayerMsg = new ActivePlayerMessage();
                        activePlayerMsg.setGame(getGame());
                        Connection activePlayerCon = userClientConnectorMap.get(game.getActivePlayer());
                        activePlayerCon.sendTCP(activePlayerMsg);
                    } else {
                        msg.setFeedbackUI(1);
                        con.sendTCP(msg);
                    }
                    con.sendTCP(msg);

                } else if (object instanceof GetPlayerMsg) {
                    System.out.println("Got the GetPlayerMsg");
                    ReturnPlayersMsg msg = new ReturnPlayersMsg();
                    con.sendTCP(msg);
                } else if (object instanceof HasCheatedMessage) {
                    sendCheatInformation();

                }else if(object instanceof UpdatePlayerNamesMsg){
                    UpdatePlayerNamesMsg msg = new UpdatePlayerNamesMsg();
                    for(User x: game.getPlayerList()){
                        msg.getNameList().add(x.getUserName());
                    }
                    server.sendToAllTCP(msg);

                }
            }
        });
    }

    public void registerClass(Class regClass) {
        server.getKryo().register(regClass);
    }

    public void createGame() {
        game = Game.getGame();
        hasGame = true;
        System.out.println("GAME, game instanced - started");
    }


    public void reset() {
        game.getPlayerList().clear();
        userClientConnectorMap.clear();
        System.out.println("Playerlist cleared!");
    }

    public void sendCheatInformation() {
        HasCheatedMessage msg = new HasCheatedMessage();
        for (Connection con : server.getConnections()) {
            con.sendTCP(msg);
        }
    }


    public boolean hasGame() {
        return hasGame;
    }

    public boolean setupGame() {
        if (hasGame()) {
            gamehandler = new GameHandler(getGame());
            gamehandler.prepareGame();
            return true;
        } else {
            return false;
        }
    }

    public Game getGame() {
        return game;
    }

}
/*
se2-demo.aau.at
53200
 */