package com.floriankleewein.commonclasses.network;

public class HasCheatedMessage extends BaseMessage {

    private String cheaterName;


    public String getName() {
        return cheaterName;
    }

    public void setName(String name) {
        this.cheaterName = name;
    }

}
