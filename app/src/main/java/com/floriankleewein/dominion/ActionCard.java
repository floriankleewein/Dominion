package com.floriankleewein.dominion;

public class ActionCard extends Card {
    private Action action;
    private ActionType actionType;

    public ActionCard(int value, ActionType actionType) {
        super(value);
        this.action = calculateAction();
        this.actionType = actionType;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Action calculateAction() {
        Action action = null;
        // Überlegen obhier ein switch case geschickter wäre
        if(actionType == ActionType.TYPEONE) {
            // Baue Response
            action.setBuyCount(1);
        }
        // TODO: Other ifs
        return action;
    }
}
