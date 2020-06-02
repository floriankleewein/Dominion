package com.floriankleewein.commonclasses.network.messages;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.gamelogic.PlayStatus;
import com.floriankleewein.commonclasses.network.BaseMessage;

public class NewTurnMessage extends BaseMessage {
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public PlayStatus getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(PlayStatus playStatus) {
        this.playStatus = playStatus;
    }

    PlayStatus playStatus;
    Game game;
}
