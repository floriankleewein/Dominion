package com.floriankleewein.commonclasses.Network;

public class CheckButtonsMsg extends BaseMessage {
    boolean createValue;
    boolean joinValue;

    public void setCreateValue(boolean b){
        this.createValue = b;
    }

    public boolean getCreateValue(){
        return this.createValue;
    }

    public void setJoinValue(boolean b){
        this.joinValue = b;
    }

    public boolean getJoinValue(){
        return this.joinValue;
    }
}
