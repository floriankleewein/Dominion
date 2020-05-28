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
    private Map<User, Connection> userClientConnectorMap = new HashMap<>();


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

    public void registerClasses(){

        ClassRegistration reg = new ClassRegistration();
        reg.registerAllClassesForServer(server);
    }

    public void addListeners(){
        server.addListener(new Listener() {
            public void received(Connection con, Object object) {
               if (object instanceof CreateGameMsg) {
                    CreateGameMsgFunctionality(object, con);

                } else if (object instanceof AddPlayerSuccessMsg) {
                    AddPlayerSuccessMsgFunctionality(object, con);

                } else if (object instanceof ResetMsg) {
                   ResetMsgFunctionality();

                } else if (object instanceof StartGameMsg) {
                    StartGameMsgFunctionality(con);

                } else if (object instanceof GetPlayerMsg) {
                    GetPlayerMsgFunctionality(con);

                } else if (object instanceof ChatMessage) {
                    ChatMessageFunctionality(object, con);

                } else if (object instanceof HasCheatedMessage) {
                    HasCheatedMessageFunctionality(object);

                } else if (object instanceof UpdatePlayerNamesMsg) {
                    UpdatePlayerNamesMsgFunctionality();

                } else if (object instanceof SuspectMessage) {
                    SuspectMessageFunctionality(object);

                } else if (object instanceof GameUpdateMsg) {
                   GameUpdateMsgFunctionality(object);

                } else if (object instanceof CheckButtonsMsg) {
                    CheckButtonsMsgFunctionality(object, con);

                } else if (object instanceof AllPlayersInDominionActivityMsg) {
                    AllPlayersInDominionActivityMsgFunctionality(object);

                } else if (object instanceof GetGameMsg) {
                    GetGameMsgFunctionality();
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

    public void CreateGameMsgFunctionality(Object object, Connection con){
        createGame();
        CreateGameMsg startGameMsg = (CreateGameMsg) object;
        startGameMsg.setGame(getGame());
        startGameMsg.setHasGame(hasGame());
        con.sendTCP(startGameMsg);
    }

    public void AddPlayerSuccessMsgFunctionality(Object object, Connection con){
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

    public void ResetMsgFunctionality() {
        game.getPlayerList().clear();
        userClientConnectorMap.clear();
        System.out.println("Playerlist cleared!");
    }

    public void StartGameMsgFunctionality(Connection con){
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

    }

    public void GetPlayerMsgFunctionality(Connection con){
        System.out.println("Got the GetPlayerMsg");
        ReturnPlayersMsg msg = new ReturnPlayersMsg();
        con.sendTCP(msg);
    }

    public void ChatMessageFunctionality(Object object, Connection con){
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

    public void HasCheatedMessageFunctionality(Object object){
        HasCheatedMessage CheatMsg = (HasCheatedMessage) object;
        //game.getCheatService().addCardtoUser(CheatMsg.getName());
        // Not working now because the User has now DeckCards --> Null Pointer

        sendCheatInformation(CheatMsg.getName());
    }

    public void UpdatePlayerNamesMsgFunctionality(){
        UpdatePlayerNamesMsg msg = new UpdatePlayerNamesMsg();
        for (User x : game.getPlayerList()) {
            msg.getNameList().add(x.getUserName());
        }
        server.sendToAllTCP(msg);
    }

    public void SuspectMessageFunctionality(Object object){
        SuspectMessage msg = (SuspectMessage) object;
        System.out.println("GOT SUSPECT MESSAGE FROM" + msg.getUserName());
        //game.getCheatService().suspectUser(msg.getSuspectedUserName(),msg.getUserName());

        sendSuspectInformation(msg.getSuspectedUserName(), msg.getUserName());
    }

    public void GameUpdateMsgFunctionality(Object object){
        GameUpdateMsg gameUpdateMsg = (GameUpdateMsg) object;
        if (gameUpdateMsg.getGameHandler() != null) {
            updateAll(gameUpdateMsg);
            gameUpdateMsg.setGameHandler(gamehandler); // TODO take care on GameupdateMessage
            server.sendToAllTCP(gameUpdateMsg);
        }
    }

    public void CheckButtonsMsgFunctionality(Object object, Connection con){
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

    public void AllPlayersInDominionActivityMsgFunctionality(Object object){
        AllPlayersInDominionActivityMsg msg = (AllPlayersInDominionActivityMsg) object;
        server.sendToAllTCP(msg);
    }

    public void GetGameMsgFunctionality(){
        GetGameMsg msg = new GetGameMsg();
        System.out.println("Got Get GameMsg on Server");
        msg.setGm(gamehandler);
        server.sendToAllTCP(msg);
    }
}
/*
se2-demo.aau.at
53200
 */