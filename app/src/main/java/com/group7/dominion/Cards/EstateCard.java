package com.group7.dominion.Cards;

import com.group7.dominion.CardActivity;

public class EstateCard extends Card {
    private int estateValue;
    private EstateType estateType;

    public EstateCard(CardActivity cardActivity, int price, int estateValue, EstateType estateType) {
        super(cardActivity, price);
        this.estateValue = estateValue;
        this.estateType = estateType;
    }

    public void init() {
        switch (estateType){
            case PROVINZ:
                //getImageButton().setImageResource(R.drawable.provinz);
                break;
            case HERZOGTUM:
                //getImageButton().setImageResource(R.drawable.herzogtum);
                break;
            case ANWESEN:
                //getImageButton().setImageResource(R.drawable.anwesen);
                break;
            case FLUCH:
                //getImageButton().setImageResource(R.drawable.fluch);
                break;
        }
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
