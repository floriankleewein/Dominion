package com.floriankleewein.commonclasses;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.floriankleewein.commonclasses.board.ActionField;
import com.floriankleewein.commonclasses.board.Board;
import com.floriankleewein.commonclasses.board.BuyField;
import com.floriankleewein.commonclasses.cards.Action;
import com.floriankleewein.commonclasses.cards.ActionCard;
import com.floriankleewein.commonclasses.cards.ActionType;
import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.cards.EstateCard;
import com.floriankleewein.commonclasses.cards.EstateType;
import com.floriankleewein.commonclasses.cards.MoneyCard;
import com.floriankleewein.commonclasses.cards.MoneyType;
import com.floriankleewein.commonclasses.chat.ChatMessage;
import com.floriankleewein.commonclasses.chat.GetChatMessages;
import com.floriankleewein.commonclasses.chat.Pair;
import com.floriankleewein.commonclasses.chat.RecChatListMsg;
import com.floriankleewein.commonclasses.cheatfunction.CheatService;
import com.floriankleewein.commonclasses.gamelogic.CardLogic;
import com.floriankleewein.commonclasses.gamelogic.GameHandler;
import com.floriankleewein.commonclasses.gamelogic.PlayStatus;
import com.floriankleewein.commonclasses.network.ActivePlayerMessage;
import com.floriankleewein.commonclasses.network.AddPlayerSuccessMsg;
import com.floriankleewein.commonclasses.network.AllPlayersInDominionActivityMsg;
import com.floriankleewein.commonclasses.network.BaseMessage;
import com.floriankleewein.commonclasses.network.CheckButtonsMsg;
import com.floriankleewein.commonclasses.network.CreateGameMsg;
import com.floriankleewein.commonclasses.network.GameUpdateMsg;
import com.floriankleewein.commonclasses.network.GetGameMsg;
import com.floriankleewein.commonclasses.network.HasCheatedMessage;
import com.floriankleewein.commonclasses.network.BuyCardMsg;
import com.floriankleewein.commonclasses.network.PlayCardMsg;
import com.floriankleewein.commonclasses.network.StartbuttonMsg;
import com.floriankleewein.commonclasses.network.messages.EndGameMsg;
import com.floriankleewein.commonclasses.network.messages.NewTurnMessage;
import com.floriankleewein.commonclasses.network.messages.NotEnoughRessourcesMsg;
import com.floriankleewein.commonclasses.network.NetworkInformationMsg;
import com.floriankleewein.commonclasses.network.ResetMsg;
import com.floriankleewein.commonclasses.network.StartGameMsg;
import com.floriankleewein.commonclasses.network.SuspectMessage;
import com.floriankleewein.commonclasses.network.UpdatePlayerNamesMsg;
import com.floriankleewein.commonclasses.network.messages.SetGameNullMsg;
import com.floriankleewein.commonclasses.user.GamePoints;
import com.floriankleewein.commonclasses.user.User;
import com.floriankleewein.commonclasses.user.UserCards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ClassRegistration {

    /**
     * FKDoc: here all classes are stored. client and server can make a instance of ClassRegistration to register it.
     *        Thats very important for sending them via TCP. Also the order plays a very important role. With this
     *        type of implementation the order always is the same since client and server use the same ressource.
     */
    private Class[] constants = {BaseMessage.class, GameUpdateMsg.class, NetworkInformationMsg.class, Game.class,
            CreateGameMsg.class, AddPlayerSuccessMsg.class, ArrayList.class, User.class, ResetMsg.class, StartGameMsg.class, ChatMessage.class,
            HasCheatedMessage.class, ActivePlayerMessage.class, UpdatePlayerNamesMsg.class, SuspectMessage.class, CheckButtonsMsg.class,
            GetGameMsg.class, UserCards.class, GamePoints.class, LinkedList.class, Card.class, MoneyCard.class, ActionCard.class,
            GameHandler.class, Action.class, Board.class, BuyField.class, ActionType.class,
            EstateType.class, MoneyType.class, CheatService.class, EstateCard.class, ActionField.class, AllPlayersInDominionActivityMsg.class,
            HashMap.class, NewTurnMessage.class, NotEnoughRessourcesMsg.class, CardLogic.class, BuyCardMsg.class, PlayCardMsg.class, PlayStatus.class,
            CardLogic.class,RecChatListMsg.class, Pair.class,GetChatMessages.class, EndGameMsg.class, StartbuttonMsg.class, SetGameNullMsg.class
    };

    /**
     * FKDoc: here the server can pass the kryonet-server, and register all classes in the given order.
     * @param server
     */
    public void registerAllClassesForServer(Server server){

        for(Class c: constants){
            server.getKryo().register(c);
        }
    }

    /**
     * FKDoc: here the client can pass the kryonet-client, and register all classes in the given order.
     * @param client
     */
    public void registerAllClassesForClient(Client client){

        for(Class c: constants){
            client.getKryo().register(c);
        }
    }

}
