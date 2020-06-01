package com.floriankleewein.commonclasses.network;

public class CheckButtonsMsg extends BaseMessage {
    boolean CreateValue;
    boolean JoinValue;

    public void setCreateValue(boolean b){
        this.CreateValue = b;
    }

    public boolean getCreateValue(){
        return this.CreateValue;
    }

    public void setJoinValue(boolean b){
        this.JoinValue = b;
    }

    public boolean getJoinValue(){
        return this.JoinValue;
    }
}
