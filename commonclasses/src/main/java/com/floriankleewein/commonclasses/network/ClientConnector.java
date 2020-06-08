package com.floriankleewein.commonclasses.network;

import com.esotericsoftware.minlog.Log;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.floriankleewein.commonclasses.chat.ChatMessage;
import com.floriankleewein.commonclasses.chat.GetChatMessages;
import com.floriankleewein.commonclasses.ClassRegistration;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.gamelogic.GameHandler;
import com.floriankleewein.commonclasses.network.messages.EndGameMsg;
import com.floriankleewein.commonclasses.network.messages.NewTurnMessage;
import com.floriankleewein.commonclasses.network.messages.SetGameNullMsg;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientConnector {
    private static final String SERVER_IP = "143.205.174.196";
    private static final int SERVER_PORT = 53217;
    private static Client client;
    private GameHandler gameHandler;


    private Game game;
    Map<Class, Callback<BaseMessage>> callbackMap = new HashMap<>();

    /**
     * FKDoc: the hidden clientconnector singleton attribute.
     */
    private static ClientConnector clientConnector;

    ClientConnector() {
    }

    /**
     * FKDoc: singleton getter which creates exactly one clientConnector per Player. its the same clientConnector for the whole game.
     * singleton pattern is very handy here.
     *
     * @return
     */
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

    public void connect() throws InterruptedException {
        /**
         * FKDoc: here the classregistration is started.
         */
        Thread thread = new Thread(() -> registerClasses());
        thread.start();
        thread.join();


        /**
         * FKDoc: kryonet client is started here.
         */
        client.start();

        /**
         * FKDoc: at this point the client tries to connect to the server with correct IP and port with a 5 second timeout.
         */
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

    /**
     * FKDoc: sends the server the request to check the playernumber to see if the start button is enabled or not.
     */
    public void checkStartbutton() {
        StartbuttonMsg msg = new StartbuttonMsg();
        client.sendTCP(msg);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof StartbuttonMsg) {
                    StartbuttonMsg msg = (StartbuttonMsg) object;
                    callbackMap.get(StartbuttonMsg.class).callback(msg);
                }
            }
        });
    }

    /**
     * FKDoc: thats the place where the server is told to create the game.
     */
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

    /**
     * FKDoc: the client gets the username and sends it to the server via a message. the listener is added, where the server response
     * is stored in the message. corresponding callback is called and UI updated aswell for user interface.
     *
     * @param playerName
     */
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

        /**
         * FKDoc: the reason this listener is added at exactly this spot is the following: after creating a user, the player is able to
         *        receive the AllPlayersInDominionActivityMsg. Therefore only players that have a user can land in the DominionActivity
         *        with the start button.
         */
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

    /**
     * FKDoc: sends the reset message to the server. there the playerlist will be cleared to avoid  server restarts all the time.
     */
    public void resetGame() {
        ResetMsg msg = new ResetMsg();
        client.sendTCP(msg);
    }

    public void endTurn() {
        NewTurnMessage msg = new NewTurnMessage();
        client.sendTCP(msg);
    }

    /**
     * FKDoc: here the client sends the corresponding message to the server, fetch the playernames.
     * after the response with the names, the listener below triggers the callback which updates the UI and shows
     * the usernames. this happens whenever a new player joined.
     */
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

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof EndGameMsg) {
                    EndGameMsg msg = (EndGameMsg) object;
                    callbackMap.get(EndGameMsg.class).callback(msg);
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
        Thread thread = new Thread(() -> client.sendTCP(msg));
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

    /**
     * LKDoc:   send BuyCardMsg zum Server + fängt die geklickte Karte ab
     * Dies löst den Listener aus - received (generelles Objekt). Instance of BuyCardMsg?
     * Cast to BuyCardMsg = callback msg
     * Dies wird ausgelöst sobald ein Spieler eine Karte kauft
     *
     * @param msg
     */
    public void sendbuyCard(BuyCardMsg msg) {
        Thread thread = new Thread(() -> client.sendTCP(msg));
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


    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public Client getClient() {
        return client;
    }

    public <T> void registerCallback(Class<T> c, Callback<BaseMessage> callback) {
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

    public void getChatMessages() {
        GetChatMessages getChatListMsg = new GetChatMessages();
        client.sendTCP(getChatListMsg);
    }

    public void sendSuspectUser(String suspectUsername, String username) {
        SuspectMessage msg = new SuspectMessage();
        msg.setSuspectedUserName(suspectUsername);
        msg.setUserName(username);
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

    /**
     * FKDoc: user only sends the corresponding message to the server, who then handles the wished logic.
     */
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

    // hiermit wird eine ChatMessage über TCP an die anderen Spieler versendet
    // @param msgToOthers -> message die versendet wird
    public void sendChatMessage(ChatMessage msgToOthers) {
        client.sendTCP(msgToOthers);
    }

    /**
     * FKDoc: server is told, to put all players with a user in the DominionActivity.
     */
    public void allPlayersInDominionActivity() {
        AllPlayersInDominionActivityMsg msg = new AllPlayersInDominionActivityMsg();
        client.sendTCP(msg);
    }

    /**
     * FKDoc: here the ClassRegistration is instanced, and the kryonet client is passed, which then registers all needed classes.
     */
    public void registerClasses() {
        ClassRegistration reg = new ClassRegistration();
        reg.registerAllClassesForClient(client);
    }

    public void sendsetGameNull() {
        Thread thread = new Thread(() -> client.sendTCP(new SetGameNullMsg()));
        thread.start();
    }
}
