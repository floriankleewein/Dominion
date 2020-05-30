package com.group7.localtestserver;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.floriankleewein.commonclasses.Board.Board;
import com.floriankleewein.commonclasses.Chat.ChatMessage;
import com.floriankleewein.commonclasses.ClassRegistration;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.GameLogic.GameHandler;
import com.floriankleewein.commonclasses.Network.ActivePlayerMessage;
import com.floriankleewein.commonclasses.Network.AddPlayerSuccessMsg;
import com.floriankleewein.commonclasses.Network.AllPlayersInDominionActivityMsg;
import com.floriankleewein.commonclasses.Network.CheckButtonsMsg;
import com.floriankleewein.commonclasses.Network.CreateGameMsg;
import com.floriankleewein.commonclasses.Network.GameUpdateMsg;
import com.floriankleewein.commonclasses.Network.GetGameMsg;
import com.floriankleewein.commonclasses.Network.GetPlayerMsg;
import com.floriankleewein.commonclasses.Network.HasCheatedMessage;
import com.floriankleewein.commonclasses.Network.Messages.GameLogicMsg.BuyCardMsg;
import com.floriankleewein.commonclasses.Network.Messages.GameLogicMsg.PlayCardMsg;
import com.floriankleewein.commonclasses.Network.Messages.NewTurnMessage;
import com.floriankleewein.commonclasses.Network.Messages.NotEnoughRessourcesMsg;
import com.floriankleewein.commonclasses.Network.ResetMsg;
import com.floriankleewein.commonclasses.Network.ReturnPlayersMsg;
import com.floriankleewein.commonclasses.Network.StartGameMsg;
import com.floriankleewein.commonclasses.Network.SuspectMessage;
import com.floriankleewein.commonclasses.Network.UpdatePlayerNamesMsg;
import com.floriankleewein.commonclasses.User.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestServer {

    private Server server;
    private Game game;
    private Board board;
    private boolean hasGame = false;
    private boolean gamehandlerCalled = false;
    private final String Tag = "TEST-SERVER"; // debugging only
    private GameHandler gamehandler;
    private Map<User, Connection> userClientConnectorMap = new HashMap<>(); // TODO fix bad variable name


    public TestServer() {
        server = new Server(65536, 65536);
    }

    public void startServer() {
        System.out.println(Tag + ", Running Server!");
        //FKDoc: Register classes
        registerClasses();

        //FKDoc: Start Server
        server.start();

        try {
            //server.bind(8080);
            server.bind(53217);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //FKDoc: adds all listeners. cyclic problem still here.
        addListeners();
    }

    public void updateAll(GameUpdateMsg msg) {
        setBoard(msg.getBoard());
        game.setGame(msg.getGame());
        gamehandler.updateGameHandler(msg);
    }

    public void createGame() {
        game = Game.getGame();
        hasGame = true;
        System.out.println("GAME, game instanced - started");
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

    /**
     * Can be used to send ErrorMessages to the active User in the game.
     *
     * @param errorNumber
     */
    public void sendErrorMessage(int errorNumber) {
        NotEnoughRessourcesMsg msg = new NotEnoughRessourcesMsg(errorNumber); // 1 = notenoughAp, 2 = notEnough BP, 3 = not Enough Money, else just failure
        Connection con = userClientConnectorMap.get(gamehandler.getActiveUser());
        con.sendTCP(msg);
    }


    public boolean hasGame() {
        return hasGame;
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

    public void registerClasses() {

        ClassRegistration reg = new ClassRegistration();
        reg.registerAllClassesForServer(server);
    }

    public void addListeners() {
        server.addListener(new Listener() {
            public void received(Connection con, Object object) {
                if (object instanceof CreateGameMsg) {
                    createGameMsgFunctionality(object, con);

                } else if (object instanceof AddPlayerSuccessMsg) {
                    addPlayerSuccessMsgFunctionality(object, con);

                } else if (object instanceof ResetMsg) {
                    resetMsgFunctionality();

                } else if (object instanceof StartGameMsg) {
                    startGameMsgFunctionality(con);

                } else if (object instanceof GetPlayerMsg) {
                    getPlayerMsgFunctionality(con);

                } else if (object instanceof ChatMessage) {
                    chatMessageFunctionality(object, con);

                } else if (object instanceof HasCheatedMessage) {
                    hasCheatedMessageFunctionality(object);

                } else if (object instanceof UpdatePlayerNamesMsg) {
                    updatePlayerNamesMsgFunctionality();

                } else if (object instanceof SuspectMessage) {
                    suspectMessageFunctionality(object);

                } else if (object instanceof GameUpdateMsg) {
                    gameUpdateMsgFunctionality(object);

                } else if (object instanceof CheckButtonsMsg) {
                    checkButtonsMsgFunctionality(object, con);

                } else if (object instanceof AllPlayersInDominionActivityMsg) {
                    allPlayersInDominionActivityMsgFunctionality(object);

                } else if (object instanceof GetGameMsg) {
                    getGameMsgFunctionality();
                } else if (object instanceof NewTurnMessage) {
                    newTurnMsgFunctionality();
                } else if (object instanceof PlayCardMsg) {
                    playCardmsgFunctionality(object);
                } else if (object instanceof BuyCardMsg) {
                    buyCardmsgFunctionality(object);
                }
            }
        });
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

    public void createGameMsgFunctionality(Object object, Connection con) {
        createGame();
        CreateGameMsg startGameMsg = (CreateGameMsg) object;
        startGameMsg.setGame(getGame());
        startGameMsg.setHasGame(hasGame());
        con.sendTCP(startGameMsg);
    }

    public void addPlayerSuccessMsgFunctionality(Object object, Connection con) {
        AddPlayerSuccessMsg addPlayerMsg = (AddPlayerSuccessMsg) object;
        String name = addPlayerMsg.getPlayerName();
        User player = new User(name);

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
    }

    public void resetMsgFunctionality() {
        game.getPlayerList().clear();
        userClientConnectorMap.clear();
        System.out.println("Playerlist cleared!");
    }

    public void startGameMsgFunctionality(Connection con) {
        StartGameMsg msg = new StartGameMsg();
        //Check if game started successfully, and notify client
        System.out.println("Got the StartGameMsg on Server");

        if (setupGame()) {
            msg.setFeedbackUI(0);
            msg.setGame(gamehandler.getGame());
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

    }

    public void getPlayerMsgFunctionality(Connection con) {
        System.out.println("Got the GetPlayerMsg");
        ReturnPlayersMsg msg = new ReturnPlayersMsg();
        con.sendTCP(msg);
    }

    public void chatMessageFunctionality(Object object, Connection con) {
        ChatMessage msg = (ChatMessage) object;


        String message = msg.getMessage();

        System.out.println("Receive msg from client:" + message);

        ChatMessage responseMsg = new ChatMessage();
        responseMsg.setMessage(msg.getMessage());
        responseMsg.setSentByMe(false);


        for (Connection c : server.getConnections()) {
            if (c != con) {
                server.sendToTCP(c.getID(), responseMsg);
            }
        }
    }

    public void hasCheatedMessageFunctionality(Object object) {
        HasCheatedMessage CheatMsg = (HasCheatedMessage) object;
        //game.getCheatService().addCardtoUser(CheatMsg.getName());
        // Not working now because the User has now DeckCards --> Null Pointer

        sendCheatInformation(CheatMsg.getName());
    }

    public void updatePlayerNamesMsgFunctionality() {
        UpdatePlayerNamesMsg msg = new UpdatePlayerNamesMsg();
        for (User x : game.getPlayerList()) {
            msg.getNameList().add(x.getUserName());
        }
        server.sendToAllTCP(msg);
    }

    public void suspectMessageFunctionality(Object object) {
        SuspectMessage msg = (SuspectMessage) object;
        System.out.println("GOT SUSPECT MESSAGE FROM" + msg.getUserName());
        //game.getCheatService().suspectUser(msg.getSuspectedUserName(),msg.getUserName());

        sendSuspectInformation(msg.getSuspectedUserName(), msg.getUserName());
    }

    public void gameUpdateMsgFunctionality(Object object) {
        GameUpdateMsg gameUpdateMsg = (GameUpdateMsg) object;
        if (gameUpdateMsg.getGameHandler() != null) {
            updateAll(gameUpdateMsg);
            gameUpdateMsg.setGameHandler(gamehandler); // TODO take care on GameupdateMessage
            server.sendToAllTCP(gameUpdateMsg);
        }
    }

    public void checkButtonsMsgFunctionality(Object object, Connection con) {
        CheckButtonsMsg msg = (CheckButtonsMsg) object;
        if (hasGame == false) {
            msg.setCreateValue(true);
            msg.setJoinValue(false);
        } else if (hasGame) {
            msg.setCreateValue(false);
            msg.setJoinValue(true);
        }
        con.sendTCP(msg);
    }

    public void allPlayersInDominionActivityMsgFunctionality(Object object) {
        AllPlayersInDominionActivityMsg msg = (AllPlayersInDominionActivityMsg) object;
        server.sendToAllTCP(msg);
    }

    public void getGameMsgFunctionality() {
        GetGameMsg msg = new GetGameMsg();
        System.out.println("Got Get GameMsg on Server");
        msg.setGame(gamehandler.getGame());
        msg.setPlayStatus(gamehandler.getTurnState());
        server.sendToAllTCP(msg);
    }

    public void newTurnMsgFunctionality() {
        gamehandler.newTurn();
        ActivePlayerMessage msg = new ActivePlayerMessage();
        msg.setGame(gamehandler.getGame());
        server.sendToAllTCP(msg);
    }

    private void buyCardmsgFunctionality(Object object) {
        BuyCardMsg msg = (BuyCardMsg) object;
        BuyCardMsg returnmsg = new BuyCardMsg();
        if (msg.getActionTypeClicked() != null) {
            System.out.println(msg.getActionTypeClicked() + " buyed");
            returnmsg.setBoughtCard(gamehandler.buyCard(msg.getActionTypeClicked()));
        } else if (msg.getEstateTypeClicked() != null) {
            System.out.println(msg.getEstateTypeClicked() + " buyed");
            returnmsg.setBoughtCard(gamehandler.buyCard(msg.getEstateTypeClicked()));
        } else if (msg.getMoneyTypeClicked() != null) {
            System.out.println(msg.getMoneyTypeClicked() + " buyed");
            returnmsg.setBoughtCard(gamehandler.buyCard(msg.getMoneyTypeClicked()));
        }
        server.sendToAllTCP(returnmsg);
    }

    private void playCardmsgFunctionality(Object object) {
        PlayCardMsg msg = (PlayCardMsg) object;
        System.out.println(msg.getPlayedCard().getId() + "is played");
        gamehandler.playCard(msg.getPlayedCard());
        PlayCardMsg returnmsg = new PlayCardMsg();
        returnmsg.setGame(gamehandler.getGame());
        returnmsg.setPlayedCard(msg.getPlayedCard());
        returnmsg.setPlayStatus(gamehandler.getTurnState());
        server.sendToAllTCP(returnmsg);
    }
}