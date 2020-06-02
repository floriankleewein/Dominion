package com.floriankleewein.commonclasses.cards;

public class EstateCard extends Card {
    private int estateValue;
    private EstateType estateType;

    public EstateCard(int price, int estateValue, EstateType estateType) {
        super(price);
        this.estateValue = estateValue;
        this.estateType = estateType;
        setIdEstate(estateType);
    }
    public EstateCard () {
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


    public void setIdEstate(EstateType estateType) {
        switch (estateType) {
            case PROVINZ:
                setId(10);
                break;
            case HERZOGTUM:
                setId(11);
                break;
            case ANWESEN:
                setId(12);
                break;
            case FLUCH:
                setId(13);
                break;
        }
    }
}