package com.floriankleewein.commonclasses.Board;

import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.cards.EstateCard;
import com.floriankleewein.commonclasses.cards.EstateType;
import com.floriankleewein.commonclasses.cards.MoneyCard;
import com.floriankleewein.commonclasses.cards.MoneyType;

import java.util.ArrayList;
import java.util.List;

public class BuyField {
    private List<Card> cardsToBuy;

    public BuyField() {
        init();
    }

    public List<Card> getCardsToBuy() {
        return cardsToBuy;
    }

    public void setCardsToBuy(List<Card> cardsToBuy) {
        this.cardsToBuy = cardsToBuy;
    }

    public void init() {
        this.cardsToBuy = new ArrayList<>();
        // Init List mit Provinzen und Währung
        // 60 x Kupfer, 40 x Silber, 30 x Gold
        for(int i = 0; i < 30; i++) {
            // Gold
            this.cardsToBuy.add(new MoneyCard(6, 3, MoneyType.GOLD));
        }
        for(int i = 0; i <40; i++) {
            // Silber
            this.cardsToBuy.add(new MoneyCard(3, 2, MoneyType.SILBER));
        }
        for(int i = 0; i <60; i++) {
            // Kupfer
            this.cardsToBuy.add(new MoneyCard(0, 1, MoneyType.KUPFER));
        }

        // je Provinz 12 Karten
        for(int i = 0; i <12; i++) {
            this.cardsToBuy.add(new EstateCard(8, 6, EstateType.PROVINZ));
            this.cardsToBuy.add(new EstateCard(5, 3, EstateType.HERZOGTUM));
            this.cardsToBuy.add(new EstateCard(2, 1, EstateType.ANWESEN));
            this.cardsToBuy.add(new EstateCard(0, -1, EstateType.FLUCH));
        }
    }

    private boolean isTypeExistsInField(EstateType estateType){
        boolean typeFound = false;
        for(int i = 0; i < this.cardsToBuy.size(); i++) {
            if(this.cardsToBuy.get(i) instanceof EstateCard) {
                EstateCard estateCard = (EstateCard) this.cardsToBuy.get(i);
                if(estateCard.getEstateType() == estateType){
                    typeFound = true;
                    return typeFound;
                }
            }
        }
        return typeFound;
    }

    private boolean isTypeExistsInField(MoneyType moneyType){
        boolean typeFound = false;
        for(int i = 0; i < this.cardsToBuy.size(); i++) {
            if(this.cardsToBuy.get(i) instanceof MoneyCard) {
                MoneyCard moneyCard = (MoneyCard) this.cardsToBuy.get(i);
                if(moneyCard.getMoneyType() == moneyType){
                    typeFound = true;
                    return typeFound;
                }
            }
        }
        return typeFound;
    }

    public Card pickCard(MoneyType moneyType) {
        Card card = null;
        boolean cardFound = false;
        int cardIndex = 0;

        // Überprüfe ob der ActionType überhaupt noch im Stapel existiert
        if(isTypeExistsInField(moneyType)){
            for(int i = 0; i < this.cardsToBuy.size(); i++) {
                if(this.cardsToBuy.get(i) instanceof MoneyCard) {
                    MoneyCard moneyCard = (MoneyCard) this.cardsToBuy.get(i);
                    // Wenn der Kartentyp gefunden wird dann merke Index
                    if(moneyCard.getMoneyType() == moneyType) {
                        card = moneyCard;
                        cardIndex = i;
                        cardFound = true;
                    }
                }
            }

            // Hier wird dann die Karte gelöscht
            if(cardFound) {
                this.cardsToBuy.remove(cardIndex);
            }

            return card;
        } else {
            //falls benötigt
            return null;
        }
    }

    public Card pickCard(EstateType estateType) {
        Card card = null;
        boolean cardFound = false;
        int cardIndex = 0;

        // Überprüfe ob der ActionType überhaupt noch im Stapel existiert
        if(isTypeExistsInField(estateType)){
            for(int i = 0; i < this.cardsToBuy.size(); i++) {
                if(this.cardsToBuy.get(i) instanceof EstateCard) {
                    EstateCard estateCard = (EstateCard) this.cardsToBuy.get(i);
                    // Wenn der Kartentyp gefunden wird dann merke Index
                    if(estateCard.getEstateType() == estateType) {
                        card = estateCard;
                        cardIndex = i;
                        cardFound = true;
                    }
                }
            }

            // Hier wird dann die Karte gelöscht
            if(cardFound) {
                this.cardsToBuy.remove(cardIndex);
            }

            return card;
        } else {
            //falls benötigt
            return null;
        }
    }

    public Card getMoneyCard(MoneyType moneyType) {
        Card card = null;
        boolean cardFound = false;
        int cardIndex = 0;

        // Überprüfe ob der ActionType überhaupt noch im Stapel existiert
        if(isTypeExistsInField(moneyType)){
            for(int i = 0; i < this.cardsToBuy.size(); i++) {
                if(this.cardsToBuy.get(i) instanceof MoneyCard) {
                    MoneyCard moneyCard = (MoneyCard) this.cardsToBuy.get(i);
                    // Wenn der Kartentyp gefunden wird dann merke Index
                    if(moneyCard.getMoneyType() == moneyType) {
                        card = moneyCard;
                        cardIndex = i;
                        cardFound = true;
                    }
                }
            }
            return card;
        } else {
            //falls benötigt
            return null;
        }
    }

    public Card getEstateCard(EstateType estateType) {
        Card card = null;
        boolean cardFound = false;
        int cardIndex = 0;

        // Überprüfe ob der ActionType überhaupt noch im Stapel existiert
        if(isTypeExistsInField(estateType)){
            for(int i = 0; i < this.cardsToBuy.size(); i++) {
                if(this.cardsToBuy.get(i) instanceof EstateCard) {
                    EstateCard estateCard = (EstateCard) this.cardsToBuy.get(i);
                    // Wenn der Kartentyp gefunden wird dann merke Index
                    if(estateCard.getEstateType() == estateType) {
                        card = estateCard;
                        cardIndex = i;
                        cardFound = true;
                    }
                }
            }
            return card;
        } else {
            //falls benötigt
            return null;
        }
    }
}
