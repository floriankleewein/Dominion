package com.floriankleewein.commonclasses.Board;

public class Board {
    private ActionField actionField;
    private BuyField buyField;

    private static Board board;

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
