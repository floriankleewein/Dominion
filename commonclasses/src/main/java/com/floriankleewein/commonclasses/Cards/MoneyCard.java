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

    MoneyCard () {
        super();
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
                break;
            case SILBER:
                super.setId(15);
                break;
            case GOLD:
                super.setId(16);
                break;
        }
    }
}

