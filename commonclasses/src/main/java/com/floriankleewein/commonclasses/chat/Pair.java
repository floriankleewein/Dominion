package com.floriankleewein.commonclasses.chat;

//dient dazu, um ein Tuppel von Player IDs und der ChatMessage des Spielers zu erstellen
public class Pair {

    //Spieler ID
    private int playerId;

    //Nachricht, die vom Spieler versendet wird
    private ChatMessage chatMessage;

    //um Objekte über das Netzwerk schicken zu können, fordert Kryonet einen parameterlosen Konstruktor.
    public Pair() {}

    //Liefert die Player ID mit der die Verbindung zum Server hergestellt worden ist
    //@return client ID
    public int getPlayerId() {
        return playerId;
    }

    //dient zum setzen der aktuellen Client ID
    public void setPlayerId(int playerID) {
        this.playerId = playerID;
    }

    //Liefert die ChatMessage die vom Spieler gesendet worden ist
    //@return ChatMessage
    public ChatMessage getChatMessage() {
        return chatMessage;
    }

    //dient zum Setzen der Nachricht, die vom Spieler gesendet wird
    public void setChatMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }




}
