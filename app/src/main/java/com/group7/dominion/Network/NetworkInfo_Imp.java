package com.group7.dominion.Network;

import com.group7.dominion.Interfaces.NetworkInfo;

public class Network_NetworkInfo implements NetworkInfo {
    private String message;
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
