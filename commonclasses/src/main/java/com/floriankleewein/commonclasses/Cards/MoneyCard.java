package com.floriankleewein.commonclasses.Cards;

public class MoneyCard extends Card {
    private MoneyType moneyType;
    private int worth;

    public MoneyCard(int price, int worth, MoneyType moneyType) {
        super(price);
        this.worth = worth;
        this.moneyType = moneyType;
        setIdMoney(moneyType);
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

    public void setIdMoney(MoneyType moneyType) {
        switch (moneyType) {
            case KUPFER:
                super.setId(14);
            case SILBER:
                super.setId(15);
            case GOLD:
                super.setId(16);
        }
    }
}

