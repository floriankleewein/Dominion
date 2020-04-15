package com.group7.dominion.Board;

import com.group7.dominion.Cards.ActionType;
import com.group7.dominion.Cards.Card;

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

    /*
        TODO: Definiere Methoden für das initialisieren des ActionFields am Board (Mehrere Methoden nötig?)
     */
    public void init() {
        // Init List mit Provinzen und Währung
        //for(...)
        //actionCardsToBuy.add(new ActionCard(1, ActionType.TYPEONE));
    }

    public Card pickCard(ActionType actionType) {
        if(actionType == ActionType.BURGGRABEN) {
            // TODO: Check if card can be picked
        }
        return null;
    }
}
