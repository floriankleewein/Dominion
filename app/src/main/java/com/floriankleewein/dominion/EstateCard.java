package com.floriankleewein.dominion;

public class EstateCard extends Card {
    private int estateValue;

    public EstateCard(int value, int estateValue, int numberOfCards) {
        super(value, numberOfCards);
        this.estateValue = estateValue;
    }

    public int getEstateValue() {
        return estateValue;
    }

    public void setEstateValue(int estateValue) {
        this.estateValue = estateValue;
    }

    // je Provinz 12 Karten
}
