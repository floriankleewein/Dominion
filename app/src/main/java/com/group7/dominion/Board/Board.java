package com.group7.dominion.Board;

public class Board {
    private ActionField actionField;
    private BuyField buyField;

    public Board() {
        // TODO: Vielleicht füge Maximale Kartenanzahl hinzu
        this.actionField = new ActionField();
        this.buyField = new BuyField();
    }

    public ActionField getActionField() {
        return actionField;
    }

    public BuyField getBuyField() {
        return buyField;
    }
}
