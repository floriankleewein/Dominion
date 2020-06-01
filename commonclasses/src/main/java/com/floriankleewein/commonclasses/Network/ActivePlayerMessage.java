package com.floriankleewein.commonclasses.Network;

import com.floriankleewein.commonclasses.Game;

public class ActivePlayerMessage implements BaseMessage {
    Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
