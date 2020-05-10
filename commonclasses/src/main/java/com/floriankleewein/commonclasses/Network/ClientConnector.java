package com.floriankleewein.commonclasses.Network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.User.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientConnector {
    private final String Tag = "CLIENT-CONNECTOR"; // Debugging only
    private static final String SERVER_IP = "143.205.174.196";
    private static final int SERVER_PORT = 53217;
    private static Client client;
    private boolean hasGame = false;


    private Game game;
    private Callback<CreateGameMsg> callback;
    Map<Class, Callback<BaseMessage>> callbackMap = new HashMap<>();

    /*public ClientConnector() {
        this.client = new Client();
    }*/

    private static ClientConnector clientConnector;

    //overwriting constructor so it cannot be instanced.
    ClientConnector() {
    }

    public static synchronized ClientConnector getClientConnector() {
        if (ClientConnector.clientConnector == null) {
            client = new Client();
            ClientConnector.clientConnector = new ClientConnector();
        }
        return ClientConnector.clientConnector;
    }

    public void registerClass(Class regClass) {
        client.getKryo().register(regClass);
    }

    public Game getGame() {
        return game;
    }

    public void connect() {
        // Register classes
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
        registerClass(ActivePlayerMessage.class);

        // start client
        client.start();

        //connects aau server
        try {
            client.connect(5000, SERVER_IP, SERVER_PORT);   // Uni server
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection-Status: " + client.isConnected());

        MessageClass ms = new MessageClass();
        ms.setMessage("Hello Server!");
        client.sendTCP(ms);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof MessageClass) {
                    MessageClass ms = (MessageClass) object;
                    System.out.println("Received response: " + ms.getMessage());
                }
            }
        });
    }

    public void createGame() {
        System.out.println("Connection-Status: " + client.isConnected());
        final CreateGameMsg startMsg = new CreateGameMsg();
        client.sendTCP(startMsg);
        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof CreateGameMsg) {
                    CreateGameMsg recStartMsg = (CreateGameMsg) object;
                    hasGame = recStartMsg.isHasGame();
                    game = recStartMsg.getGame();
                    callbackMap.get(CreateGameMsg.class).callback(recStartMsg);
                    System.out.println("Created/Received Game." + hasGame);
                }
            }
        });
    }

    public void addUser(String playerName) {
        AddPlayerSuccessMsg addPlayerMsg = new AddPlayerSuccessMsg();
        addPlayerMsg.setPlayerName(playerName);
        client.sendTCP(addPlayerMsg);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof AddPlayerSuccessMsg) {
                    AddPlayerSuccessMsg ms = (AddPlayerSuccessMsg) object;
                    if (ms.getFeedbackUI() == 0) {
                        callbackMap.get(AddPlayerSuccessMsg.class).callback(ms);
                    } else if (ms.getFeedbackUI() == 1) {
                        callbackMap.get(AddPlayerNameErrorMsg.class).callback(ms);
                    } else if (ms.getFeedbackUI() == 2) {
                        callbackMap.get(AddPlayerSizeErrorMsg.class).callback(ms);
                    }

                }
            }

        });
    }

    //for now this method only has the use, to reset the game and playerList, so we
    //dont have to restart the server for the same purpose.
    public void resetGame() {
        ResetMsg msg = new ResetMsg();
        client.sendTCP(msg);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                //what happens then?
            }

        });
    }

    public void startGame() {
        StartGameMsg msg = new StartGameMsg();
        client.sendTCP(msg);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof StartGameMsg) {
                    StartGameMsg msg = (StartGameMsg) object;
                    if(msg.getFeedbackUI() == 0) {
                        callbackMap.get(StartGameMsg.class).callback(msg);
                        game.setGame(msg.getGame());
                    } else {
                        //TODO display error in starting game
                    }
                }
            }

        });

    }

    public boolean hasGame() {
        return hasGame;
    }

    public void setHasGame(Boolean bool) {
        this.hasGame = bool;
    }

    public Client getClient() {
        return client;
    }

    public void registerCallback(Class c, Callback<BaseMessage> callback) {
        this.callbackMap.put(c, callback);
    }
}


