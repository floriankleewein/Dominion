package com.floriankleewein.dominion;

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

    public void setActionField(ActionField actionField) {
        this.actionField = actionField;
    }

    public BuyField getBuyField() {
        return buyField;
    }

    public void setBuyField(BuyField buyField) {
        this.buyField = buyField;
    }
}
