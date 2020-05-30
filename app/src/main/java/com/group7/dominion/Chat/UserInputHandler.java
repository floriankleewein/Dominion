package com.group7.dominion.Chat;

public interface UserInputHandler {

    String getMessageToBeSent();
    // hiermit wird die Message, die an die anderen Spieler gesendet werden soll gefiltert

    void clearInput();
    //löscht den aktuellen Input aus dem Eingabefeld
}
