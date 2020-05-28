package com.group7.localtestserver;

import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.GameLogic.GameHandler;
import com.floriankleewein.commonclasses.Network.GameUpdateMsg;

public class GameUpdateMsgHelper {



    GameUpdateMsg ReturnMsg;
    GameHandler gameHandler;


    public GameUpdateMsg handleGameUpdateMsg(GameUpdateMsg msg) {
       ReturnMsg = new GameUpdateMsg();
        if (msg.getBoughtCard() != null) {
            gameHandler.buyCard(msg.getBoughtCard());
        } else if (msg.getPlayedCard() != null) {
            gameHandler.playCard(msg.getPlayedCard());
        } else if (msg.getMoneyTypeClicked() != null) {
            gameHandler.buyCard(msg.getMoneyTypeClicked());
        } else if (msg.getActionTypeClicked() != null) {
            System.out.println("WE BOUGHT AN ANCTION CARD YEEI");
            ReturnMsg.setBoughtCard(gameHandler.buyCard(msg.getActionTypeClicked()));
            testSomething();
        } else if (msg.getEstateTypeClicked() != null) {
            gameHandler.buyCard(msg.getEstateTypeClicked());
        }
        else if (msg.isNewHandCards()) {
            System.out.println("WE WANT NEW HAND CARDS");
            gameHandler.newTurn();
            ReturnMsg.setGameHandler(gameHandler);
            ReturnMsg.setNewHandCards(true);
        }
        return null;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public GameUpdateMsg getReturnMsg() {
        return ReturnMsg;
    }

    public void setReturnMsg(GameUpdateMsg returnMsg) {
        ReturnMsg = returnMsg;
    }


    public void testSomething () {
        for (int i = 0; i < gameHandler.getActiveUser().getUserCards().getDeck().size() ; i++) {
           System.out.println(gameHandler.getActiveUser().getUserCards().getDeck().get(i).getId());
        }

    }

}
