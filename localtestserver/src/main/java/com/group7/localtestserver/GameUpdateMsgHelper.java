package com.group7.localtestserver;

import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.GameLogic.GameHandler;
import com.floriankleewein.commonclasses.Network.GameUpdateMsg;

public class GameUpdateMsgHelper {


    GameHandler gameHandler;


    public void handleGameUpdateMsg(GameUpdateMsg msg) {
        if (msg.getBoughtCard() != null) {
            gameHandler.buyCard(msg.getBoughtCard());
        } else if (msg.getPlayedCard() != null) {
            gameHandler.playCard(msg.getPlayedCard());
        } else if (msg.getMoneyTypeClicked() != null) {
            gameHandler.buyCard(msg.getMoneyTypeClicked());
        } else if (msg.getActionTypeClicked() != null) {
            System.out.println("WE BOUGHT AN ANCTION CARD YEEI");
            gameHandler.buyCard(msg.getActionTypeClicked());
        } else if (msg.getEstateTypeClicked() != null) {
            gameHandler.buyCard(msg.getEstateTypeClicked());
        }
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }


}
