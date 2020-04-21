package com.group7.localtestserver;

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
