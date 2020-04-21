package com.group7.dominion.Network;

import com.group7.dominion.Interfaces.NetworkInformation;

public class NetworkInfo_Imp implements NetworkInformation {
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
