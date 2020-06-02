package com.floriankleewein.commonclasses.network;

public class SuspectMessage extends BaseMessage {

   private String SuspectedUserName;
    private String UserName;

    public String getSuspectedUserName() {
        return SuspectedUserName;
    }

    public void setSuspectedUserName(String suspectedUserName) {
        SuspectedUserName = suspectedUserName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }



}
