package com.floriankleewein.commonclasses.Chat;

// realisiert die Message, die zwischen den einzelnen Spielern ausgetauscht wird

import com.floriankleewein.commonclasses.Network.BaseMessage;

public class ChatMessage  extends BaseMessage{

    private String message;
    private boolean sentByMe;

    public ChatMessage(){ }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(boolean sentByMe) {
        this.sentByMe = sentByMe;
    }
}
