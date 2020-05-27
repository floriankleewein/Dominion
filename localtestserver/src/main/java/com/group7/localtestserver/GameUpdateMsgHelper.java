package com.group7.localtestserver;

import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.GameLogic.GameHandler;
import com.floriankleewein.commonclasses.Network.GameUpdateMsg;

public class GameUpdateMsgHelper {


    GameHandler gameHandler;

    GameUpdateMsgHelper (GameHandler gm) {this.gameHandler = gm;}

    public void handleGameUpdateMsg (GameUpdateMsg msg) {
        if (msg.getBoughtCard() != null) {

        }
    }
    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }


}
