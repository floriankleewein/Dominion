package com.group7.dominion.Board;

import com.group7.dominion.Cards.Card;

import java.util.List;

public class BuyField {
    private List<Card> cardsToBuy;

    public BuyField() {
        // TODO: Rufe init Methoden auf
        init();
    }

    public List<Card> getCardsToBuy() {
        return cardsToBuy;
    }

    public void setCardsToBuy(List<Card> cardsToBuy) {
        this.cardsToBuy = cardsToBuy;
    }

    /*
        TODO: Definiere Methoden für das initialisieren des BuyFields am Board (Mehrere Methoden nötig?)
     */
    public void init() {
        // Init List mit Provinzen und Währung
    }
}
