package com.group7.dominion.Board;

import com.group7.dominion.CardActivity;
import com.group7.dominion.Cards.ActionCard;
import com.group7.dominion.Cards.Card;
import com.group7.dominion.Cards.EstateCard;
import com.group7.dominion.Cards.EstateType;
import com.group7.dominion.Cards.MoneyCard;
import com.group7.dominion.Cards.MoneyType;

import java.util.ArrayList;
import java.util.List;

public class BuyField {
    private List<Card> cardsToBuy;
    private CardActivity cardActivity;

    public BuyField(CardActivity cardActivity) {
        this.cardActivity = cardActivity;
        initCards();
    }

    public List<Card> getCardsToBuy() {
        return cardsToBuy;
    }

    public void setCardsToBuy(List<Card> cardsToBuy) {
        this.cardsToBuy = cardsToBuy;
    }

    public void initCardTypesAndButtonImages() {
        for(Card card: this.cardsToBuy) {
            if(card instanceof MoneyCard){
                MoneyCard moneyCard = (MoneyCard) card;
                moneyCard.initImageButton();
                moneyCard.init();
            }
            if(card instanceof EstateCard){
                EstateCard estateCard = (EstateCard) card;
                estateCard.initImageButton();
                estateCard.init();
            }
        }
    }

    private void initCards() {
        this.cardsToBuy = new ArrayList<>();
        // Init List mit Provinzen und Währung
        // 60 x Kupfer, 40 x Silber, 30 x Gold
        for(int i = 0; i < 30; i++) {
            // Gold
            this.cardsToBuy.add(new MoneyCard(cardActivity, 6, 3, MoneyType.GOLD));
        }
        for(int i = 0; i <40; i++) {
            // Silber
            this.cardsToBuy.add(new MoneyCard(cardActivity,3, 2, MoneyType.SILBER));
        }
        for(int i = 0; i <60; i++) {
            // Kupfer
            this.cardsToBuy.add(new MoneyCard(cardActivity,0, 1, MoneyType.KUPFER));
        }

        // je Provinz 12 Karten
        for(int i = 0; i <12; i++) {
            this.cardsToBuy.add(new EstateCard(cardActivity,8, 6, EstateType.PROVINZ));
            this.cardsToBuy.add(new EstateCard(cardActivity,5, 3, EstateType.HERZOGTUM));
            this.cardsToBuy.add(new EstateCard(cardActivity,2, 1, EstateType.ANWESEN));
            this.cardsToBuy.add(new EstateCard(cardActivity,0, -1, EstateType.FLUCH));
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
            // TODO: Gebe hier auch eine Log Message oder User Message aus, dass dieser Typ von Karten nicht mehr im Stapel existiert
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
            // TODO: Gebe hier auch eine Log Message oder User Message aus, dass dieser Typ von Karten nicht mehr im Stapel existiert
            return null;
        }
    }

    /* Falls benötigt => Diese Logik muss beim User passieren
    public void setCards(List<Card> cards) {
        this.cardsToBuy.addAll(cards);
    }
    */
}
