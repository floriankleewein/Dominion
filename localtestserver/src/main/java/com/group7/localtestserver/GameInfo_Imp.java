package com.group7.dominion.Network;

import com.group7.dominion.Interfaces.GameInfo;

class GameInfo_Imp implements GameInfo {

    private String message;

    @Override
    public void updateServer() {
        //TODO
    }

    @Override
    public void updateClient() {
        //TODO
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
