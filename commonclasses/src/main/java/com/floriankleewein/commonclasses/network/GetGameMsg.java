package com.floriankleewein.commonclasses.network;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.gamelogic.PlayStatus;

public class GetGameMsg extends BaseMessage {


    private Game game;
    private PlayStatus playStatus;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPlayStatus(PlayStatus playStatus) {
        this.playStatus = playStatus;
    }

    public PlayStatus getPlayStatus() {
        return playStatus;
    }
}
