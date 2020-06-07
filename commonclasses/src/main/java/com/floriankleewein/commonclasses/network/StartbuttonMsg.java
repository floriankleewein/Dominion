package com.floriankleewein.commonclasses.network;

public class StartbuttonMsg extends BaseMessage {
    boolean startValue;

    public boolean isStartValue() {
        return startValue;
    }

    public void setStartValue(boolean startValue) {
        this.startValue = startValue;
    }
}
