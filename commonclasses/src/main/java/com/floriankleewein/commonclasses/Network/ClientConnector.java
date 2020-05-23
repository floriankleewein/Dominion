package com.floriankleewein.commonclasses.Network;

import com.esotericsoftware.minlog.Log;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.floriankleewein.commonclasses.Board.Board;
import com.floriankleewein.commonclasses.Board.BuyField;
import com.floriankleewein.commonclasses.Cards.Action;
import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.Cards.ActionType;
import com.floriankleewein.commonclasses.Cards.CalculationHelper;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Cards.EstateCard;
import com.floriankleewein.commonclasses.Cards.EstateType;
import com.floriankleewein.commonclasses.Cards.MoneyCard;
import com.floriankleewein.commonclasses.Cards.MoneyType;
import com.floriankleewein.commonclasses.Chat.ChatMessage;
import com.floriankleewein.commonclasses.CheatFunction.CheatService;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.GameLogic.GameHandler;
import com.floriankleewein.commonclasses.GameLogic.PlayerTurn;
import com.floriankleewein.commonclasses.User.GamePoints;
import com.floriankleewein.commonclasses.User.User;
import com.floriankleewein.commonclasses.User.UserCards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ClientConnector {
    private final String Tag = "CLIENT-CONNECTOR"; // Debugging only
    private static final String SERVER_IP = "143.205.174.196";
    private static final int SERVER_PORT = 53217;
    private static Client client;
    //private boolean hasGame = false;
    private GameHandler gameHandler;


    private Game game; //TODO das sollte man evtl nicht mehr hier im Client haben... Einfach Game.getGame verwenden
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
        registerClass(GameUpdateMsg.class);
        registerClass(NetworkInformationMsg.class);
        registerClass(Game.class);
        registerClass(CreateGameMsg.class);
        registerClass(AddPlayerSuccessMsg.class);
        registerClass(ArrayList.class);
        registerClass(User.class);
        registerClass(ResetMsg.class);
        registerClass(StartGameMsg.class);
        registerClass(ChatMessage.class);
        registerClass(HasCheatedMessage.class);
        registerClass(ActivePlayerMessage.class);
        registerClass(UpdatePlayerNamesMsg.class);
        registerClass(SuspectMessage.class);
        registerClass(CheckButtonsMsg.class);
        registerClass(GetGameMsg.class);
        registerClass(UserCards.class);
        registerClass(GamePoints.class);
        registerClass(LinkedList.class);
        registerClass(Card.class);
        registerClass(MoneyCard.class);
        registerClass(ActionCard.class);
        registerClass(GameHandler.class);
        registerClass(PlayerTurn.class);
        registerClass(Action.class);
        registerClass(Board.class);
        registerClass(BuyField.class);
        registerClass(ActionType.class);
        registerClass(CalculationHelper.class);
        registerClass(EstateType.class);
        registerClass(MoneyType.class);
        registerClass(CheatService.class);
        registerClass(EstateCard.class);

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

    public void recreateStartGameActivity() {
        RecreateStartActivityMsg msg = new RecreateStartActivityMsg();
        client.sendTCP(msg);
    }

    public void createGame() {
        System.out.println("Connection-Status: " + client.isConnected());
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
    }

    public Game sendGameUpdate() {
        GetGameMsg msg = new GetGameMsg();
        client.sendTCP(msg);

        client.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof GetGameMsg) {
                    Log.info("Got Message");
                    GetGameMsg msg = (GetGameMsg) object;
                    System.out.println(msg.getGm());
                    game = msg.getGm().getGame();

                    callbackMap.get(GetGameMsg.class).callback(msg);
                }
            }
        });
        return Game.getGame();
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
                        callbackMap.get(StartGameMsg.class).callback(msg);
                        Game.setGame(msg.getGame());
                        gameHandler = msg.getGameHandler();
                    } else {
                        //TODO display error in starting game
                    }
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
                    gameHandler.updateGameHandler(gameUpdateMsg);
                    callbackMap.get(GameUpdateMsg.class).callback(gameUpdateMsg);
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

    //public boolean hasGame() {return hasGame;}

    //public void setHasGame(Boolean bool) {this.hasGame = bool;}

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

}
