package com.group7.dominion.Network;

import com.group7.dominion.Interfaces.GameInfo;
import com.group7.dominion.User.User;

class GameInfo_Imp implements GameInfo {

    private String message;
    private User user;

    @Override
    public void updateServer() {
        //TODO
    }

    @Override
    public void updateClient() {
        //TODO
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
