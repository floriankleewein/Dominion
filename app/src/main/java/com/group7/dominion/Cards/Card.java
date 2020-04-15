package com.group7.dominion.Cards;

public class Card {
    private int value;
    private int numberOfCards;

    public Card(int value, int numberOfCards) {
        this.value = value;
        this.numberOfCards = numberOfCards;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }
}
