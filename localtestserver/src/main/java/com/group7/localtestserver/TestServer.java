package com.group7.localtestserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.floriankleewein.commonclasses.board.Board;
import com.floriankleewein.commonclasses.chat.ChatMessage;
import com.floriankleewein.commonclasses.chat.GetChatMessages;
import com.floriankleewein.commonclasses.chat.Pair;
import com.floriankleewein.commonclasses.chat.RecChatListMsg;
import com.floriankleewein.commonclasses.ClassRegistration;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.gamelogic.GameHandler;
import com.floriankleewein.commonclasses.network.ActivePlayerMessage;
import com.floriankleewein.commonclasses.network.AddPlayerSuccessMsg;
import com.floriankleewein.commonclasses.network.AllPlayersInDominionActivityMsg;
import com.floriankleewein.commonclasses.network.CheckButtonsMsg;
import com.floriankleewein.commonclasses.network.CreateGameMsg;
import com.floriankleewein.commonclasses.network.GameUpdateMsg;
import com.floriankleewein.commonclasses.network.GetGameMsg;
import com.floriankleewein.commonclasses.network.GetPlayerMsg;
import com.floriankleewein.commonclasses.network.HasCheatedMessage;
import com.floriankleewein.commonclasses.network.BuyCardMsg;
import com.floriankleewein.commonclasses.network.PlayCardMsg;
import com.floriankleewein.commonclasses.network.StartbuttonMsg;
import com.floriankleewein.commonclasses.network.messages.EndGameMsg;
import com.floriankleewein.commonclasses.network.messages.NewTurnMessage;
import com.floriankleewein.commonclasses.network.messages.NotEnoughRessourcesMsg;
import com.floriankleewein.commonclasses.network.ResetMsg;
import com.floriankleewein.commonclasses.network.ReturnPlayersMsg;
import com.floriankleewein.commonclasses.network.StartGameMsg;
import com.floriankleewein.commonclasses.network.SuspectMessage;
import com.floriankleewein.commonclasses.network.UpdatePlayerNamesMsg;
import com.floriankleewein.commonclasses.network.messages.SetGameNullMsg;
import com.floriankleewein.commonclasses.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestServer {

    private Server server;
    private Game game;
    private Board board;
    private boolean hasGame = false;
    private GameHandler gamehandler;
    private Map<User, Connection> userClientConnectorMap = new HashMap<>(); // TODO fix bad variable name
    private List<Pair> messageList = new ArrayList<>();
    private static final String BOUGHT = " bought";

    /**
     * FKDoc: here the kryonet-server sets the BufferSize. Needed to adapt the size of sent objects.
     */
    public TestServer() {
        server = new Server(65536, 65536);
    }

    /**
     * FKDoc: its called to begin with the server start.
     * @throws InterruptedException
     */
    public void startServer() throws InterruptedException {
        Log.info("Running Server!");
        //FKDoc: Register classes
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                registerClasses();
            }
        });
        thread.start();
        thread.join();


        /**
         * FKDoc: here the kryonet server is started.
         */
        server.start();

        /**
         * FKDoc: here the kryonet server is bound to the correct tcp Port.
         */
        try {
            //server.bind(8080);
            server.bind(53217);
        } catch (IOException e) {
            Log.error("ERROR: ", "bind to port failed!");
        }
        /**
         * FKDoc: adds all listeners.
         */
        addListeners();
    }

    public void updateAll(GameUpdateMsg msg) {
        setBoard(msg.getBoard());
        game.setGame(msg.getGame());
        gamehandler.updateGameHandler(msg);
    }

    /**
     * FKDoc: creates the game, to be sure the instance is there when needed.
     */
    public void createGame() {
        game = Game.getGame();
        hasGame = true;
        Log.info("GAME, game instanced - started");
    }

    public void sendCheatInformation(String cheaterName) {
        HasCheatedMessage msg = new HasCheatedMessage();
        msg.setName(cheaterName);
        for (Connection con : server.getConnections()) {
            con.sendTCP(msg);
        }
    }

    public void sendSuspectInformation(String suspectName, String username) {
        SuspectMessage msg = new SuspectMessage();
        msg.setSuspectedUserName(suspectName);
        msg.setUserName(username);
        for (Connection con : server.getConnections()) {
            con.sendTCP(msg);
        }
    }

    /**
     * ESDoc: Can be used to send ErrorMessages to the active User in the game.
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

    /**
     * FKDoc: here the instance fo the ClassRegistration is made. Pass the kryonet-server attribute
     *        to the method, to register all needed classes.
     */
    public void registerClasses() {

        ClassRegistration reg = new ClassRegistration();
        reg.registerAllClassesForServer(server);
    }

    /**
     * FKDoc: here one listener is added to the game. multiple listeners arent needed, since kryonet handles them
     *        in a ListenerArray anyway. thats why there is an else case for each method. Inside, the corresponding
     *        method which holds the information is called.
     */
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
                    buyCardmsgFunctionality(object, con);
                } else if (object instanceof GetChatMessages) {
                    getChatMessagesFunctionality(con);
                } else if (object instanceof SetGameNullMsg) {
                    setGameNull();
                } else if (object instanceof StartbuttonMsg){
                    startbuttonMsgFunctionality(object);
                }
            }
        });
    }


    /**
     * ESDoc: Creates Starter Deck for all players and returns true if game was created successfully.
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

    /**
     * FKDoc: gets the playername. then the size and name is checked. if both are checked successfull, the player
     *        is put in the map and added to the game aswell. the feedback "success" is set in the message.
     *        Depending on which check fails, a different correct information is set in the message. Msg is sent back then.
     * @param object
     * @param con
     */
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
                Log.info("Player added: " + player.getUserName());


            } else {
                addPlayerMsg.setFeedbackUI(1);
            }
        } else {
            addPlayerMsg.setFeedbackUI(2);
        }
        con.sendTCP(addPlayerMsg);
    }

    /**
     * FKDoc: clears the playerlist and the corresponding map. that way a server-restart is avoided.
     */
    public void resetMsgFunctionality() {
        game.getPlayerList().clear();
        userClientConnectorMap.clear();
    }

    public void startGameMsgFunctionality(Connection con) {
        StartGameMsg msg = new StartGameMsg();
        //Check if game started successfully, and notify client
        Log.info("Got the StartGameMsg on Server");

        if (setupGame()) {
            msg.setFeedbackUI(0);
            msg.setGame(gamehandler.getGame());
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
        Log.info("Got the GetPlayerMsg");
        ReturnPlayersMsg msg = new ReturnPlayersMsg();
        con.sendTCP(msg);
    }

    public void chatMessageFunctionality(Object object, Connection con) {
        ChatMessage msg = (ChatMessage) object;

        System.out.println("Receive msg from client:" + msg.getPlayerName());


        msg.setSentByMe(false);

        String message = msg.getMessage();

        Log.info("Receive msg from client:" + message);


        //speichert die ID des Clients, der die Nachricht sendet und die Nachricht selbst
        Pair chatPair = new Pair();
        chatPair.setChatMessage(msg);
        chatPair.setPlayerId(con.getID());

        messageList.add(chatPair);

        //sende die Nachrihct an die anderen Spieler
        //server.sendToAllTCP(msg);
        server.sendToAllExceptTCP(con.getID(), msg);
    }

    public void hasCheatedMessageFunctionality(Object object) {
        HasCheatedMessage cheatMsg = (HasCheatedMessage) object;
        gamehandler.getGame().findUser(cheatMsg.getName()).getUserCards().addDeckCardtoHandCard(1);
        sendCheatInformation(cheatMsg.getName());
    }

    /**
     * FKDoc: iterates over the game's playerlist and adds every username to the message, which also contains a list for the names.
     *        After that the broadcast done and each player receives the message, which then will trigger an update for the UI.
     */
    public void updatePlayerNamesMsgFunctionality() {
        UpdatePlayerNamesMsg msg = new UpdatePlayerNamesMsg();
        for (User x : game.getPlayerList()) {
            msg.getNameList().add(x.getUserName());
        }
        server.sendToAllTCP(msg);
    }

    public void suspectMessageFunctionality(Object object) {
        SuspectMessage msg = (SuspectMessage) object;
        Log.info("GOT SUSPECT MESSAGE FROM" + msg.getUserName());
        gamehandler.getGame().getCheatService().suspectUser(msg.getSuspectedUserName(), msg.getUserName());
        sendSuspectInformation(msg.getSuspectedUserName(), msg.getUserName());
    }

    public void gameUpdateMsgFunctionality(Object object) {
        GameUpdateMsg gameUpdateMsg = (GameUpdateMsg) object;
        if (gameUpdateMsg.getGameHandler() != null) {
            updateAll(gameUpdateMsg);
            gameUpdateMsg.setGameHandler(gamehandler);
            server.sendToAllTCP(gameUpdateMsg);
        }
    }

    /**
     * FKDoc: in this method the state for the buttons are checked. depending on if the game already exists or not,
     *        boolean values are set and returned with the message.
     * @param object
     * @param con
     */
    public void checkButtonsMsgFunctionality(Object object, Connection con) {
        CheckButtonsMsg msg = (CheckButtonsMsg) object;
        if (hasGame) {
            msg.setCreateValue(false);
            msg.setJoinValue(true);
        } else {
            msg.setCreateValue(true);
            msg.setJoinValue(false);
        }
        con.sendTCP(msg);
    }

    public void allPlayersInDominionActivityMsgFunctionality(Object object) {
        AllPlayersInDominionActivityMsg msg = (AllPlayersInDominionActivityMsg) object;
        server.sendToAllTCP(msg);
    }

    public void getGameMsgFunctionality() {
        GetGameMsg msg = new GetGameMsg();
        Log.info("Got Get GameMsg on Server");
        msg.setGame(gamehandler.getGame());
        msg.setPlayStatus(gamehandler.getTurnState());
        server.sendToAllTCP(msg);
    }

    public void newTurnMsgFunctionality() {
        NewTurnMessage msg = new NewTurnMessage();
        msg.setGame(gamehandler.getGame());
        msg.setPlayStatus(gamehandler.getTurnState());
        server.sendToAllTCP(msg);
    }

    /**
     * LKDoc:   Überprüft ob geklickte Karte gekauft werden kann und schickt die msg zurück
     *          --> BuyCardMsg --> ClientConnector
     * @param object
     * @param con
     */
    private void buyCardmsgFunctionality(Object object, Connection con) {
        BuyCardMsg msg = (BuyCardMsg) object;
        BuyCardMsg returnmsg = new BuyCardMsg();
        if (msg.getActionTypeClicked() != null) {
            Log.info(msg.getActionTypeClicked() + BOUGHT);
            returnmsg.setBoughtCard(gamehandler.buyCard(msg.getActionTypeClicked()));
            if (gamehandler.getBoard().getActionField().getNotAvailableCards().contains(msg.getActionTypeClicked())) {
                returnmsg.setCantBuyCard(true);
            }
        } else if (msg.getEstateTypeClicked() != null) {
            Log.info(msg.getEstateTypeClicked() + BOUGHT);
            returnmsg.setBoughtCard(gamehandler.buyCard(msg.getEstateTypeClicked()));
            if (gamehandler.getBoard().getBuyField().getNotAvailableCards().contains(msg.getEstateTypeClicked())) {
                returnmsg.setCantBuyCard(true);
            }
        } else if (msg.getMoneyTypeClicked() != null) {
            Log.info(msg.getMoneyTypeClicked() + BOUGHT);
            returnmsg.setBoughtCard(gamehandler.buyCard(msg.getMoneyTypeClicked()));
            if (gamehandler.getBoard().getBuyField().getNotAvailableCards().contains(msg.getMoneyTypeClicked())) {
                returnmsg.setCantBuyCard(true);
            }
        }
        if (gamehandler.isNewTurn()) {
            Log.info("WE HAVE A NEW PLAYER");
            newTurnMsgFunctionality();
            gamehandler.setNewTurn(false);
        }
        /**
         * This condition ends the game!!
         */
        if ((gamehandler.getBoard().getBuyField().isNoEstateCards()) || (gamehandler.getBoard()
                .getActionField().getNotAvailableCards().size() >= 3)) {
            EndGameMsg endGameMsg = new EndGameMsg();
            endGameMsg.setGame(gamehandler.getGame());
            endGameMsg.setWinningUser(gamehandler.declareWinner());
            server.sendToAllTCP(endGameMsg);
        }
        returnmsg.setGame(Game.getGame());
        con.sendTCP(returnmsg);
    }

    private void playCardmsgFunctionality(Object object) {
        PlayCardMsg msg = (PlayCardMsg) object;
        Log.info(msg.getPlayedCard().getId() + "is played");
        gamehandler.playCard(msg.getPlayedCard());
        PlayCardMsg returnmsg = new PlayCardMsg();
        returnmsg.setGame(gamehandler.getGame());
        returnmsg.setPlayedCard(msg.getPlayedCard());
        returnmsg.setPlayStatus(gamehandler.getTurnState());
        server.sendToAllTCP(returnmsg);
    }


    public void getChatMessagesFunctionality(Connection con) {
        System.out.println("SEND CHAT MESSAGES TO CLIENT");
        System.out.println("SERVER MESSAGE LIST SIZE: " + messageList.size());
        RecChatListMsg msg = new RecChatListMsg();
        msg.setMessages(messageList);
        server.sendToTCP(con.getID(), msg);
    }

    public void startbuttonMsgFunctionality(Object object){
        StartbuttonMsg msg = (StartbuttonMsg) object;
        if(game.getPlayerList().size() > 1) {
            msg.setStartValue(true);
        }else{
            msg.setStartValue(false);
        }
        server.sendToAllTCP(msg);

    }

    public void setGameNull() {
        game = null;
        gamehandler = null;
        board = null;
    }
}
