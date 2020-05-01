package com.floriankleewein.commonclasses.Network;

import com.floriankleewein.commonclasses.User.User;

public class AddPlayerMsg extends BaseMessage{

    private String playerName;
    private User user;
    private boolean playerAdded = false;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String userName) {
        this.playerName = userName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isPlayerAdded() {
        return playerAdded;
    }

    public void setPlayerAdded(boolean playerAdded) {
        this.playerAdded = playerAdded;
    }
}






