package com.group7.dominion.Cards;

public class TreasureCard extends Card {
    private int worth;

    public TreasureCard(int value, int numberOfCards, int worth){
        super(value, numberOfCards);
        this.worth = worth;
    }

    public int getWorth() {
        return worth;
    }

    public void setWorth(int worth) {
        this.worth = worth;
    }

    // 60 x Kupfer, 40 x Silber, 30 x Gold
}
