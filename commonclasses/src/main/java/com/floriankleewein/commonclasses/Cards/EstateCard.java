package com.floriankleewein.commonclasses.Cards;

public class EstateCard extends Card {
    private int estateValue;
    private EstateType estateType;

    public EstateCard(int price, int estateValue, EstateType estateType) {
        super(price);
        this.estateValue = estateValue;
        this.estateType = estateType;
    }

    public int getEstateValue() {
        return estateValue;
    }

    public void setEstateValue(int estateValue) {
        this.estateValue = estateValue;
    }

    public EstateType getEstateType() {
        return estateType;
    }

    public void setEstateType(EstateType estateType) {
        this.estateType = estateType;
    }
}