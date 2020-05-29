package com.floriankleewein.commonclasses;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.floriankleewein.commonclasses.Board.ActionField;
import com.floriankleewein.commonclasses.Board.Board;
import com.floriankleewein.commonclasses.Board.BuyField;
import com.floriankleewein.commonclasses.Cards.Action;
import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.Cards.ActionType;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Cards.EstateCard;
import com.floriankleewein.commonclasses.Cards.EstateType;
import com.floriankleewein.commonclasses.Cards.MoneyCard;
import com.floriankleewein.commonclasses.Cards.MoneyType;
import com.floriankleewein.commonclasses.Chat.ChatMessage;
import com.floriankleewein.commonclasses.CheatFunction.CheatService;
import com.floriankleewein.commonclasses.GameLogic.CardLogic;
import com.floriankleewein.commonclasses.GameLogic.GameHandler;
import com.floriankleewein.commonclasses.Network.ActivePlayerMessage;
import com.floriankleewein.commonclasses.Network.AddPlayerSuccessMsg;
import com.floriankleewein.commonclasses.Network.AllPlayersInDominionActivityMsg;
import com.floriankleewein.commonclasses.Network.BaseMessage;
import com.floriankleewein.commonclasses.Network.CheckButtonsMsg;
import com.floriankleewein.commonclasses.Network.CreateGameMsg;
import com.floriankleewein.commonclasses.Network.GameUpdateMsg;
import com.floriankleewein.commonclasses.Network.GetGameMsg;
import com.floriankleewein.commonclasses.Network.HasCheatedMessage;
import com.floriankleewein.commonclasses.Network.Messages.NewTurnMessage;
import com.floriankleewein.commonclasses.Network.Messages.NotEnoughRessourcesMsg;
import com.floriankleewein.commonclasses.Network.NetworkInformationMsg;
import com.floriankleewein.commonclasses.Network.ResetMsg;
import com.floriankleewein.commonclasses.Network.StartGameMsg;
import com.floriankleewein.commonclasses.Network.SuspectMessage;
import com.floriankleewein.commonclasses.Network.UpdatePlayerNamesMsg;
import com.floriankleewein.commonclasses.User.GamePoints;
import com.floriankleewein.commonclasses.User.User;
import com.floriankleewein.commonclasses.User.UserCards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ClassRegistration {

    private Class[] constants = {BaseMessage.class, GameUpdateMsg.class, NetworkInformationMsg.class, Game.class,
            CreateGameMsg.class, AddPlayerSuccessMsg.class, ArrayList.class, User.class, ResetMsg.class, StartGameMsg.class, ChatMessage.class,
            HasCheatedMessage.class, ActivePlayerMessage.class, UpdatePlayerNamesMsg.class, SuspectMessage.class, CheckButtonsMsg.class,
            GetGameMsg.class, UserCards.class, GamePoints.class, LinkedList.class, Card.class, MoneyCard.class, ActionCard.class,
            GameHandler.class, Action.class, Board.class, BuyField.class, ActionType.class,
            EstateType.class, MoneyType.class, CheatService.class, EstateCard.class, ActionField.class, AllPlayersInDominionActivityMsg.class,
            HashMap.class, NewTurnMessage.class, NotEnoughRessourcesMsg.class, CardLogic.class
    };

    public void registerAllClassesForServer(Server server){

        for(Class c: constants){
            server.getKryo().register(c);
        }
    }

    public void registerAllClassesForClient(Client client){

        for(Class c: constants){
            client.getKryo().register(c);
        }
    }

}
