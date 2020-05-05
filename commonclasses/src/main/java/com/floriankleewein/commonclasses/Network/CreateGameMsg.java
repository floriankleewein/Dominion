package com.floriankleewein.commonclasses.Network;

import com.floriankleewein.commonclasses.Game;

public class CreateGameMsg extends BaseMessage {
    private boolean hasGame = false;
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isHasGame() {
        return hasGame;
    }

    public void setHasGame(boolean hasGame) {
        this.hasGame = hasGame;
    }
}
