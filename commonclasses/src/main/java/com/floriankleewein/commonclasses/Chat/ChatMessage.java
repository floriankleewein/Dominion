package com.floriankleewein.commonclasses.Chat;

import com.floriankleewein.commonclasses.Network.BaseMessage;

//realisiert die Message, die zwischen den einzelnen Spielern ausgetauscht wird
public class ChatMessage extends BaseMessage{

    //Nachricht die versendet wird
    private String message;

    //Name des Spielers, der die Nachricht versendet hat
    private String playerName;

    //gibt an, ob die Nachricht von mir selbst geschrieben wurde
    private boolean sentByMe;

    public ChatMessage(){ }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPlayerName() { return playerName; }

    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public boolean isSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(boolean sentByMe) {
        this.sentByMe = sentByMe;
    }
}
