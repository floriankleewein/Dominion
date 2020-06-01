package com.floriankleewein.commonclasses.Network;

import com.esotericsoftware.minlog.Log;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.floriankleewein.commonclasses.Chat.ChatMessage;
import com.floriankleewein.commonclasses.ClassRegistration;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.GameLogic.GameHandler;
import com.floriankleewein.commonclasses.Network.Messages.GameLogicMsg.BuyCardMsg;
import com.floriankleewein.commonclasses.Network.Messages.GameLogicMsg.PlayCardMsg;
import com.floriankleewein.commonclasses.Network.Messages.NewTurnMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClientConnector {
    private static final String SERVER_IP = "143.205.174.196";
    private static final int SERVER_PORT = 53217;
    private static Client client;
    private GameHandler gameHandler;


    private Game game; //TODO das sollte man evtl nicht mehr hier im Client haben... Einfach Game.getGame verwenden
    Map<Class, Callback<BaseMessage>> callbackMap = new HashMap<>();


    private static ClientConnector clientConnector;

    //overwriting constructor so it cannot be instanced.
    ClientConnector() {
    }

    public static synchronized ClientConnector getClientConnector() {
        if (ClientConnector.clientConnector == null) {
            client = new Client(65536, 65536);
            ClientConnector.clientConnector = new ClientConnector();
        }
        return ClientConnector.clientConnector;
    }

    public Game getGame() {
        return game;
    }

    public void connect() {
        // Register classes
        registerClasses();

        // start client
        client.start();

        //connects aau server
        try {
            client.connect(5000, SERVER_IP, SERVER_PORT);   // Uni server
        } catch (IOException e) {
            Log.error("Connection to server failed!");
        }
        Log.info("Connection-Status: " + client.isConnected());
    }

    public void recreateStartGameActivity() {
        RecreateStartActivityMsg msg = new RecreateStartActivityMsg();
        client.sendTCP(msg);
    }

    public void createGame() {
        Log.info("Connection-Status: " + client.isConnected());
        final CreateGameMsg startMsg = new CreateGameMsg();
        client.sendTCP(startMsg);
        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof CreateGameMsg) {
                    CreateGameMsg recStartMsg = (CreateGameMsg) object;
                    game = recStartMsg.getGame();
                    checkButtons();
                }
            }
        });

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof HasCheatedMessage) {
                    HasCheatedMessage msg = (HasCheatedMessage) object;
                    callbackMap.get(HasCheatedMessage.class).callback(msg);
                }
            }

        });

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof SuspectMessage) {
                    SuspectMessage msg = (SuspectMessage) object;
                    callbackMap.get(SuspectMessage.class).callback(msg);
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

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof AllPlayersInDominionActivityMsg) {
                    AllPlayersInDominionActivityMsg msg = (AllPlayersInDominionActivityMsg) object;
                    callbackMap.get(AllPlayersInDominionActivityMsg.class).callback(msg);
                }
            }
        });
    }

    public Game sendGameUpdate() {
        GetGameMsg msg = new GetGameMsg();
        client.sendTCP(msg);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof GetGameMsg) {
                    Log.info("Got Message");
                    GetGameMsg msg = (GetGameMsg) object;
                    game = msg.getGame();
                    callbackMap.get(GetGameMsg.class).callback(msg);
                }
            }
        });
        return game;
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

    public void endTurn() {
        NewTurnMessage msg = new NewTurnMessage();
        client.sendTCP(msg);
    }

    public void updatePlayerNames() {
        UpdatePlayerNamesMsg msg = new UpdatePlayerNamesMsg();
        client.sendTCP(msg);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof UpdatePlayerNamesMsg) {
                    UpdatePlayerNamesMsg msg = (UpdatePlayerNamesMsg) object;
                    msg.setNameList(msg.getNameList());
                    callbackMap.get(UpdatePlayerNamesMsg.class).callback(msg);
                }
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
                    if (msg.getFeedbackUI() == 0) {
                        Log.info("Got Game Message in ClientConnector");
                        callbackMap.get(StartGameMsg.class).callback(msg);
                        Game.setGame(msg.getGame());
                        gameHandler = msg.getGameHandler();
                    } else {
                        //TODO display error in starting game
                    }
                }
            }
        });

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof NewTurnMessage) {
                    NewTurnMessage msg = (NewTurnMessage) object;
                    Log.info("Got New Turn Message in ClientConnector");
                    callbackMap.get(NewTurnMessage.class).callback(msg);
                }
            }
        });
    }

    /**
     * Send Update message to Server.
     *
     * @param msg
     */
    public void sendUpdate(GameUpdateMsg msg) {
        client.sendTCP(msg);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof GameUpdateMsg) {
                    GameUpdateMsg gameUpdateMsg = (GameUpdateMsg) object;
                    setGameHandler(msg.getGameHandler());
                    callbackMap.get(GameUpdateMsg.class).callback(gameUpdateMsg);
                }
            }
        });
    }

    public void sendPlayCard(PlayCardMsg msg) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                client.sendTCP(msg);
            }
        });
        thread.start();
        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof PlayCardMsg) {
                    PlayCardMsg msg1 = (PlayCardMsg) object;
                    callbackMap.get(PlayCardMsg.class).callback(msg1);
                }
            }
        });
    }

    public void sendbuyCard(BuyCardMsg msg) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                client.sendTCP(msg);
            }
        });
        thread.start();

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof BuyCardMsg) {
                    BuyCardMsg msg1 = (BuyCardMsg) object;
                    callbackMap.get(BuyCardMsg.class).callback(msg1);
                }
            }
        });
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public Client getClient() {
        return client;
    }

    public void registerCallback(Class c, Callback<BaseMessage> callback) {
        this.callbackMap.put(c, callback);
    }

    public boolean isConnected() {
        return client.isConnected();
    }


    public void sendCheatMessage(String name) {
        HasCheatedMessage msg = new HasCheatedMessage();
        msg.setName(name);
        client.sendTCP(msg);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof HasCheatedMessage) {
                    HasCheatedMessage msg = (HasCheatedMessage) object;
                    callbackMap.get(HasCheatedMessage.class).callback(msg);
                }
            }
        });

    }

    public void sendSuspectUser(String SuspectUsername, String Username) {
        SuspectMessage msg = new SuspectMessage();
        msg.setSuspectedUserName(SuspectUsername);
        msg.setUserName(Username);
        client.sendTCP(msg);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof SuspectMessage) {
                    SuspectMessage msg = (SuspectMessage) object;
                    callbackMap.get(SuspectMessage.class).callback(msg);
                }
            }
        });
    }

    public void checkButtons() {
        CheckButtonsMsg msg = new CheckButtonsMsg();
        client.sendTCP(msg);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof CheckButtonsMsg) {
                    CheckButtonsMsg msg = (CheckButtonsMsg) object;
                    callbackMap.get(CheckButtonsMsg.class).callback(msg);
                }
            }
        });
    }

    public void sendChatMessage(ChatMessage msgToOthers) {
        client.sendTCP(msgToOthers);

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof ChatMessage) {
                    ChatMessage msg = (ChatMessage) object;
                    callbackMap.get(ChatMessage.class).callback(msg);
                }
            }
        });
    }

    //FKDoc: this is the message which is broadcasted when startbutton is clicked. everyone lands in the dominion activity then.
    public void allPlayersInDominionActivity() {
        AllPlayersInDominionActivityMsg msg = new AllPlayersInDominionActivityMsg();
        client.sendTCP(msg);
    }

    public void registerClasses() {
        ClassRegistration reg = new ClassRegistration();
        reg.registerAllClassesForClient(client);
    }
}
