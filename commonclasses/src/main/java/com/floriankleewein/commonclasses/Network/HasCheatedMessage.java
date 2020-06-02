package com.floriankleewein.commonclasses.Network;

public class HasCheatedMessage extends BaseMessage {

    String CheaterName;


    public String getName() {
        return CheaterName;
    }

    public void setName(String name) {
        this.CheaterName = name;
    }

}
