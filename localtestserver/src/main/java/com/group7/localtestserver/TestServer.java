package com.group7.localtestserver;


import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.floriankleewein.commonclasses.Board.ActionField;
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
import com.floriankleewein.commonclasses.Network.ActivePlayerMessage;
import com.floriankleewein.commonclasses.Network.AddPlayerSuccessMsg;
import com.floriankleewein.commonclasses.Network.AllPlayersInDominionActivityMsg;
import com.floriankleewein.commonclasses.Network.BaseMessage;
import com.floriankleewein.commonclasses.Network.CheckButtonsMsg;
import com.floriankleewein.commonclasses.Network.CreateGameMsg;
import com.floriankleewein.commonclasses.Network.GameUpdateMsg;
import com.floriankleewein.commonclasses.Network.GetGameMsg;
import com.floriankleewein.commonclasses.Network.GetPlayerMsg;
import com.floriankleewein.commonclasses.Network.HasCheatedMessage;
import com.floriankleewein.commonclasses.Network.NetworkInformationMsg;
import com.floriankleewein.commonclasses.Network.ResetMsg;
import com.floriankleewein.commonclasses.Network.ReturnPlayersMsg;
import com.floriankleewein.commonclasses.Network.StartGameMsg;
import com.floriankleewein.commonclasses.Network.SuspectMessage;
import com.floriankleewein.commonclasses.Network.UpdatePlayerNamesMsg;
import com.floriankleewein.commonclasses.User.GamePoints;
import com.floriankleewein.commonclasses.User.User;
import com.floriankleewein.commonclasses.User.UserCards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

public class TestServer {

    private Server server;
    private Game game;
    private Board board;
    private boolean hasGame = false;
    private boolean gamehandlerCalled = false;
    private final String Tag = "TEST-SERVER"; // debugging only
    private GameHandler gamehandler;
    private Map<User, Connection> userClientConnectorMap = new HashMap<>();


    public TestServer() {
        server = new Server(65536, 65536);
    }

    public void startServer() {
        System.out.println(Tag + ", Running Server!");
        //Register classes
        registerClasses();

        //Start Server
        server.start();

        try {
            //server.bind(8080);
            server.bind(53217);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //adds all listeners. cyclic problem still here.
        addListeners();
    }

    public void updateAll(GameUpdateMsg msg) {
        setBoard(msg.getBoard());
        game.setGame(msg.getGame());
        gamehandler.updateGameHandler(msg);
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

    public void sendCheatInformation(String CheaterName) {
        HasCheatedMessage msg = new HasCheatedMessage();
        msg.setName(CheaterName);
        for (Connection con : server.getConnections()) {
            con.sendTCP(msg);
        }
    }

    public void sendSuspectInformation(String SuspectName, String Username) {
        SuspectMessage msg = new SuspectMessage();
        msg.setSuspectedUserName(SuspectName);
        msg.setUserName(Username);
        for (Connection con : server.getConnections()) {
            con.sendTCP(msg);
        }
    }


    public boolean hasGame() {
        return hasGame;
    }

    /**
     * Creates Starter Deck for all players and returns true if game was created successfully.
     *
     * @return
     */
    public boolean setupGame() {
        if (hasGame()) {
            if (gamehandler == null) {
                gamehandler = new GameHandler(getGame());
                gamehandler.prepareGame();
                return true;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Game getGame() {
        return game;
    }

    public void registerClasses(){
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
        registerClass(ActionField.class);
        registerClass(AllPlayersInDominionActivityMsg.class);
        registerClass(HashMap.class);
    }

    public void addListeners(){
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
                    System.out.println("Got the StartGameMsg on Server");
                    if (setupGame()) {
                        msg.setFeedbackUI(0);
                        msg.setGame(getGame());
                        msg.setGameHandler(gamehandler);
                        // Send message to all clients, TODO they need to be in lobby
                        server.sendToAllTCP(msg);
                        ActivePlayerMessage activePlayerMsg = new ActivePlayerMessage();
                        activePlayerMsg.setGame(getGame());
                        Connection activePlayerCon = userClientConnectorMap.get(game.getActivePlayer());
                        activePlayerCon.sendTCP(activePlayerMsg);
                    } else { // fehlerfall
                        msg.setFeedbackUI(1);
                        con.sendTCP(msg);
                    }
                    con.sendTCP(msg);

                } else if (object instanceof GetPlayerMsg) {
                    System.out.println("Got the GetPlayerMsg");
                    ReturnPlayersMsg msg = new ReturnPlayersMsg();
                    con.sendTCP(msg);


                } else if (object instanceof ChatMessage) {
                    //aktuelle Nachricht filtern
                    ChatMessage msg = (ChatMessage) object;

                    //logging der empfangenen Nachricht
                    System.out.println(Tag + " -> received msg from: " + msg.getPlayerName());

                    /*ChatMessage responseMsg = new ChatMessage();
                    responseMsg.setMessage(msg.getMessage());
                    responseMsg.setSentByMe(false); */

                    msg.setSentByMe(false);

                    //sende die Nachricht an die anderen Spieler
                    server.sendToAllTCP(msg);

                } else if (object instanceof HasCheatedMessage) {
                    HasCheatedMessage CheatMsg = (HasCheatedMessage) object;
                    /*game.getCheatService().addCardtoUser(CheatMsg.getName());
                    Not working now because the User has now DeckCards --> Null Pointer
                     */
                    sendCheatInformation(CheatMsg.getName());


                } else if (object instanceof UpdatePlayerNamesMsg) {
                    UpdatePlayerNamesMsg msg = new UpdatePlayerNamesMsg();
                    for (User x : game.getPlayerList()) {
                        msg.getNameList().add(x.getUserName());
                    }
                    server.sendToAllTCP(msg);

                } else if (object instanceof SuspectMessage) {
                    SuspectMessage msg = (SuspectMessage) object;
                    System.out.println("GOT SUSPECT MESSAGE FROM" + msg.getUserName());
                    //game.getCheatService().suspectUser(msg.getSuspectedUserName(),msg.getUserName());

                    sendSuspectInformation(msg.getSuspectedUserName(), msg.getUserName());
                } else if (object instanceof GameUpdateMsg) {
                    GameUpdateMsg gameUpdateMsg = (GameUpdateMsg) object;
                    if (gameUpdateMsg.getGameHandler() != null) {
                        updateAll(gameUpdateMsg);
                        gameUpdateMsg.setGameHandler(gamehandler); // TODO take care on GameupdateMessage
                        server.sendToAllTCP(gameUpdateMsg);
                    }
                } else if (object instanceof CheckButtonsMsg) {
                    CheckButtonsMsg msg = (CheckButtonsMsg) object;
                    if (hasGame == false) {
                        msg.setCreateValue(true);
                        msg.setJoinValue(false);
                    } else if (hasGame == true) {
                        msg.setCreateValue(false);
                        msg.setJoinValue(true);
                    }
                    con.sendTCP(msg);
                } else if (object instanceof AllPlayersInDominionActivityMsg) {
                    AllPlayersInDominionActivityMsg msg = (AllPlayersInDominionActivityMsg) object;
                    server.sendToAllTCP(msg);
                } else if (object instanceof GetGameMsg) {
                    GetGameMsg msg = new GetGameMsg();
                    System.out.println("Got Get GameMsg on Server");
                    msg.setGm(gamehandler);
                    server.sendToAllTCP(msg);
                }
            }
        });
    }
}
/*
se2-demo.aau.at
53200
 */