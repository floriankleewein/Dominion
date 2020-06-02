package com.floriankleewein.commonclasses.network.messages;

import com.floriankleewein.commonclasses.network.BaseMessage;
import com.floriankleewein.commonclasses.user.User;

public class EndGameMsg extends BaseMessage {
    User winningUser;

    public User getWinningUser() {
        return winningUser;
    }

    public void setWinningUser(User winningUser) {
        this.winningUser = winningUser;
    }

}
