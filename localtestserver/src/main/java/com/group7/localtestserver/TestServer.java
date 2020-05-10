package com.group7.localtestserver;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.floriankleewein.commonclasses.Chat.ChatMessage;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.AddPlayerSuccessMsg;
import com.floriankleewein.commonclasses.Network.BaseMessage;
import com.floriankleewein.commonclasses.Network.UpdatePlayerNamesMsg;
import com.floriankleewein.commonclasses.Network.ResetMsg;
import com.floriankleewein.commonclasses.Network.CreateGameMsg;
import com.floriankleewein.commonclasses.Network.GameInformationMsg;
import com.floriankleewein.commonclasses.Network.NetworkInformationMsg;
import com.floriankleewein.commonclasses.Network.StartGameMsg;
import com.floriankleewein.commonclasses.User.User;

import java.io.IOException;
import java.util.ArrayList;

public class TestServer {

    private Server server;
    private Game game;
    private boolean hasGame = false;
    private final String Tag = "TEST-SERVER"; // debugging only
    //private Map<User, ClientConnector> userClientConnectorMap = new HashMap<>();


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
        registerClass(ChatMessage.class);
        registerClass(UpdatePlayerNamesMsg.class);

        //Start Server
        server.start();

        try {
            //server.bind(8080);
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
                else if(object instanceof CreateGameMsg){
                    createGame();
                    CreateGameMsg startGameMsg = (CreateGameMsg) object;
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
                            System.out.println("Player added: " + player.getUserName());

                        }else{
                            addPlayerMsg.setFeedbackUI(1);
                        }
                    }else{
                        addPlayerMsg.setFeedbackUI(2);
                    }
                    con.sendTCP(addPlayerMsg);
                }
                else if(object instanceof ResetMsg){
                    System.out.println("Received Reset Message.");
                    reset();
                    //ResetMsg msg = (ResetMsg) object;

                }else if(object instanceof StartGameMsg){
                    StartGameMsg msg = new StartGameMsg();
                    con.sendTCP(msg);

                }else if(object instanceof ChatMessage){
                    ChatMessage msg = (ChatMessage) object;

                    String message = msg.getMessage();

                    Log.error("Receive msg from client:" + message);

                    ChatMessage responseMsg = new ChatMessage();
                    responseMsg.setMessage(msg.getMessage());
                    responseMsg.setSentByMe(false);


                    for (Connection c : server.getConnections()) {
                        if (c != con) {
                            server.sendToTCP(c.getID(), responseMsg);
                        }
                    }

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


    public void reset(){
        game.getPlayerList().clear();
        System.out.println("Playerlist cleared!");
    }


    public boolean hasGame() {
        return hasGame;
    }

    public Game getGame() {
        return game;
    }

}
