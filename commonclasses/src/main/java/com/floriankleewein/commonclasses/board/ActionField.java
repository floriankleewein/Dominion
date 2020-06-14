package com.floriankleewein.commonclasses.board;

import com.floriankleewein.commonclasses.cards.ActionCard;
import com.floriankleewein.commonclasses.cards.ActionType;
import com.floriankleewein.commonclasses.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class ActionField {

    //LKDoc: Liste von Karten definiert - Card hat ID & price
    private List<Card> actionCardsToBuy;
    private List<ActionType> notAvailableCards;

    public ActionField() {
        init();
    }

    public List<Card> getActionCardsToBuy() {
        return actionCardsToBuy;
    }

    public void setActionCardsToBuy(List<Card> actionCardsToBuy) {
        this.actionCardsToBuy = actionCardsToBuy;
    }

    public List<ActionType> getNotAvailableCards() {
        return notAvailableCards;
    }

    public void setNotAvailableCards(List<ActionType> notAvailableCards) {
        this.notAvailableCards = notAvailableCards;
    }

    /**
     * LKDoc: Hier sind alle Action Karten definiert => von jeder Karte gibt es 10
     * FM: Fürs testen die Karten auf zwei reduziert, da es sonst sehr lange dauert
     */
    private void init() {
        this.actionCardsToBuy = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            this.actionCardsToBuy.add(new ActionCard(2, ActionType.KELLER));
            this.actionCardsToBuy.add(new ActionCard(2, ActionType.BURGGRABEN));
            this.actionCardsToBuy.add(new ActionCard(3, ActionType.DORF));
            this.actionCardsToBuy.add(new ActionCard(3, ActionType.WERKSTATT));
            this.actionCardsToBuy.add(new ActionCard(5, ActionType.HOLZFAELLER));
            this.actionCardsToBuy.add(new ActionCard(4, ActionType.SCHMIEDE));
            this.actionCardsToBuy.add(new ActionCard(4, ActionType.HEXE));
            this.actionCardsToBuy.add(new ActionCard(4, ActionType.MILIZ));
            this.actionCardsToBuy.add(new ActionCard(5, ActionType.MARKT));
            this.actionCardsToBuy.add(new ActionCard(5, ActionType.MINE));
        }
        this.notAvailableCards = new ArrayList<>();
    }

    /**
     * LKDoc: Überprüfe ob der Typ (ActionCard) noch in der Liste existiert
     * @param actionType
     * @return
     */
    private boolean isTypeExistsInField(ActionType actionType) {
        boolean typeFound = false;
        for (int i = 0; i < this.actionCardsToBuy.size(); i++) {
            if (this.actionCardsToBuy.get(i) instanceof ActionCard) {
                ActionCard actionCard = (ActionCard) this.actionCardsToBuy.get(i);
                if (actionCard.getActionType() == actionType) {
                    typeFound = true;
                    return typeFound;
                }
            }
        }
        return typeFound;
    }

    /**
     * LKDoc: pickCard Method to buy a card
     * @param actionType
     * @return
     */
    public Card pickCard(ActionType actionType) {
        Card card = null;
        boolean cardFound = false;
        int cardIndex = 0;

        //LKDoc: Überprüfe ob der ActionType überhaupt noch im Stapel existiert
        if (isTypeExistsInField(actionType)) {
            for (int i = 0; i < this.actionCardsToBuy.size(); i++) {
                if (this.actionCardsToBuy.get(i) instanceof ActionCard) {
                    ActionCard actionCard = (ActionCard) this.actionCardsToBuy.get(i);
                    //LKDoc: Wenn der Kartentyp gefunden wird dann merke Index
                    if (actionCard.getActionType() == actionType) {
                        card = actionCard;
                        cardIndex = i;
                        cardFound = true;
                    }
                }
            }

            //LKDoc: Hier wird dann die Karte gelöscht
            if (cardFound) {
                this.actionCardsToBuy.remove(cardIndex);
            }

            return card;
        } else {
            //Card is null in that case
            return card;
        }
    }

    /**
     * LKDoc - gibt die Karte zurück (Methode wird für die buyCard Methode im Gamehandler benötigt)
     * @param actionType
     * @return
     */
    public Card getActionCard(ActionType actionType) {
        Card card = null;

        if (isTypeExistsInField(actionType)) {
            for (int i = 0; i < this.actionCardsToBuy.size(); i++) {
                if (this.actionCardsToBuy.get(i) instanceof ActionCard) {
                    ActionCard actionCard = (ActionCard) this.actionCardsToBuy.get(i);
                    if (actionCard.getActionType() == actionType) {
                        card = actionCard;
                    }
                }
            }
            return card;
        } else {
            if (!notAvailableCards.contains(actionType)) {
                this.notAvailableCards.add(actionType);
            }
            return card;
        }
    }
}
