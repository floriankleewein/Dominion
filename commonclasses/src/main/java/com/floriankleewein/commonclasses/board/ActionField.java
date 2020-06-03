package com.floriankleewein.commonclasses.board;

import com.floriankleewein.commonclasses.cards.ActionCard;
import com.floriankleewein.commonclasses.cards.ActionType;
import com.floriankleewein.commonclasses.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class ActionField {
    private List<Card> actionCardsToBuy;

    public ActionField() {
        init();
    }

    public List<Card> getActionCardsToBuy() {
        return actionCardsToBuy;
    }

    public void setActionCardsToBuy(List<Card> actionCardsToBuy) {
        this.actionCardsToBuy = actionCardsToBuy;
    }

    private void init() {
        this.actionCardsToBuy = new ArrayList<>();
        // Hier sind alle Action Karten definiert => von jeder Karte gibt es 10
        for(int i = 0; i < 10; i++) {
            this.actionCardsToBuy.add(new ActionCard(2, ActionType.KELLER));
            this.actionCardsToBuy.add(new ActionCard(2, ActionType.BURGGRABEN));
            this.actionCardsToBuy.add(new ActionCard(3, ActionType.DORF));
            this.actionCardsToBuy.add(new ActionCard(3, ActionType.WERKSTATT));
            this.actionCardsToBuy.add(new ActionCard(3, ActionType.HOLZFAELLER));
            this.actionCardsToBuy.add(new ActionCard(4, ActionType.SCHMIEDE));
            this.actionCardsToBuy.add(new ActionCard(4, ActionType.HEXE));
            this.actionCardsToBuy.add(new ActionCard(4, ActionType.MILIZ));
            this.actionCardsToBuy.add(new ActionCard(5, ActionType.MARKT));
            this.actionCardsToBuy.add(new ActionCard(5, ActionType.MINE));
        }
    }

    private boolean isTypeExistsInField(ActionType actionType){
        boolean typeFound = false;
        for(int i = 0; i < this.actionCardsToBuy.size(); i++) {
            if(this.actionCardsToBuy.get(i) instanceof ActionCard) {
                ActionCard actionCard = (ActionCard) this.actionCardsToBuy.get(i);
                if(actionCard.getActionType() == actionType){
                    typeFound = true;
                    return typeFound;
                }
            }
        }
        return typeFound;
    }

    //LKDoc: pickCard Method to buy a card
    public Card pickCard(ActionType actionType) {
        Card card = null;
        boolean cardFound = false;
        int cardIndex = 0;

        // Überprüfe ob der ActionType überhaupt noch im Stapel existiert
        if(isTypeExistsInField(actionType)){
            for(int i = 0; i < this.actionCardsToBuy.size(); i++) {
                if(this.actionCardsToBuy.get(i) instanceof ActionCard) {
                    ActionCard actionCard = (ActionCard) this.actionCardsToBuy.get(i);
                    // Wenn der Kartentyp gefunden wird dann merke Index
                    if(actionCard.getActionType() == actionType) {
                        card = actionCard;
                        cardIndex = i;
                        cardFound = true;
                    }
                }
            }

            // Hier wird dann die Karte gelöscht
            if(cardFound) {
                this.actionCardsToBuy.remove(cardIndex);
            }

            return card;
        } else {
            //falls benötigt
            return card;
        }
    }

    //LKDoc - just returns the card (needed for the logic of Emanuel)
    public Card getActionCard(ActionType actionType) {
        Card card = null;

        // Überprüfe ob der ActionType überhaupt noch im Stapel existiert
        if(isTypeExistsInField(actionType)){
            for(int i = 0; i < this.actionCardsToBuy.size(); i++) {
                if (this.actionCardsToBuy.get(i) instanceof ActionCard) {
                    ActionCard actionCard = (ActionCard) this.actionCardsToBuy.get(i);
                    // Wenn der Kartentyp gefunden wird dann merke Index
                    if (actionCard.getActionType() == actionType) {
                        card = actionCard;
                    }
                }
            }
            return card;
        } else {
            //falls benötigt
            return card;
        }
    }

}
