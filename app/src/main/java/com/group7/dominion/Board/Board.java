package com.group7.dominion.Board;

import android.widget.LinearLayout;

import com.group7.dominion.CardActivity;

public class Board {
    private ActionField actionField;
    private BuyField buyField;

    public Board(CardActivity cardActivity) {
        // TODO: Vielleicht füge Maximale Kartenanzahl hinzu
        this.actionField = new ActionField(cardActivity);
        this.buyField = new BuyField(cardActivity);
    }

    public void init() {
        actionField.initCardTypesAndButtonImages();
        buyField.initCardTypesAndButtonImages();
    }

    public ActionField getActionField() {
        return actionField;
    }

    public BuyField getBuyField() {
        return buyField;
    }
}
