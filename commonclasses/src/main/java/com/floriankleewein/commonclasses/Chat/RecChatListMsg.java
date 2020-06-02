package com.floriankleewein.commonclasses.Chat;

import com.floriankleewein.commonclasses.Network.BaseMessage;

import java.util.List;

public class RecChatListMsg extends BaseMessage {

    private List<Pair> messages;

    public RecChatListMsg() {}

    public List<Pair> getMessages() {
        return messages;
    }

    public void setMessages(List<Pair> messages) {
        this.messages = messages;
    }
}
