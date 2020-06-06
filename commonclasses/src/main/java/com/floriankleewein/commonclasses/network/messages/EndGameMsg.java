package com.floriankleewein.commonclasses.network.messages;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.network.BaseMessage;
import com.floriankleewein.commonclasses.user.User;

public class EndGameMsg extends BaseMessage {
    User winningUser;
    Game game;

    public User getWinningUser() {
        return winningUser;
    }

    public void setWinningUser(User winningUser) {
        this.winningUser = winningUser;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


}
