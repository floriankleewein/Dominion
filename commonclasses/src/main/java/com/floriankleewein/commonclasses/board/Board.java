package com.floriankleewein.commonclasses.board;

public class Board {
    private ActionField actionField;
    private BuyField buyField;

    /**
     * ActionField und BuyField werden initialisiert wenn wir das Board initalisieren
     */
    public Board() {
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
