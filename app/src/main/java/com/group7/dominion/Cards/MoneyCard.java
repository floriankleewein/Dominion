package com.group7.dominion.Cards;

import com.group7.dominion.CardActivity;

public class MoneyCard extends Card {
    private MoneyType moneyType;
    private int worth;

    public MoneyCard(CardActivity cardActivity, int price, int worth, MoneyType moneyType){
        super(cardActivity, price);
        this.worth = worth;
        this.moneyType = moneyType;
    }

    public void init() {
        switch (moneyType){
            case GOLD:
                //getImageButton().setImageResource(R.drawable.gold);
                break;
            case SILBER:
                //getImageButton().setImageResource(R.drawable.silber);
                break;
            case KUPFER:
                //getImageButton().setImageResource(R.drawable.kupfer);
                break;
        }
    }

    public int getWorth() {
        return worth;
    }

    public void setWorth(int worth) {
        this.worth = worth;
    }

    public MoneyType getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(MoneyType moneyType) {
        this.moneyType = moneyType;
    }
}
