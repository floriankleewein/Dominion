package com.floriankleewein.commonclasses.network;

public class SuspectMessage extends BaseMessage {

   private String suspectedUserName;
    private String userName;

    public String getSuspectedUserName() {
        return suspectedUserName;
    }

    public void setSuspectedUserName(String suspectedUserName) {
        this.suspectedUserName = suspectedUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



}
