package com.floriankleewein.commonclasses.cards;

public class ActionCard extends Card {
    private Action action;
    private ActionType actionType;

    public ActionCard(int price, ActionType actionType) {
        super(price);
        this.actionType = actionType;
        this.action = calculateAction();
    }

    public ActionCard () {}

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    /**
     * LKDoc:   Action ist am Anfang null - Werte werden abhängig vom ActionType vergeben
     * @return the action
     */
    private Action calculateAction() {
        Action action = null;

        switch(actionType){
            case BURGGRABEN:
                setId(0);
                action = new Action();
                action.setCardCount(2);
                action.setThrowEveryUserCardsUntilThreeLeft(false); /*LKDoc: Da die einzige Angriffskarte die Miliz ist hat diese keine Wirkung auf den Spieler und somit muss der boolean für diesen Spieler false sein
                Wenn ein Mitspiler eine Angriffskarte ausspielt, darfst du diese Karte aus deiner Hand aufdecken. Der Angriff hat damit keine Wirkung auf dich.*/
                break;

            case DORF:
                setId(1);
                action = new Action();
                action.setCardCount(1);
                action.setActionCount(2);
                //LKDoc: +1 Karte, +2 Aktionen
                break;

            case HOLZFAELLER:
                setId(2);
                action = new Action();
                action.setBuyCount(1);
                action.setMoneyValue(2);
                //LKDoc: +1 Kauf, +2 Geld
                break;

            case KELLER: //
                setId(3);
                action = new Action();
                action.setActionCount(1);
                action.setThrowAnyAmountCards(true); /*LKDoc: Maximale Anzahl an Handkarten kann hier abgelegt werden => Input für den User der auf diese Anzahl begrenzt
                +1 Aktion, Lege eine beliebeige Anzahl an Handkarten ab.
                Ziehe für jede abgelegte Karte eine Karte nach.*/
                break;

            case WERKSTATT: //
                setId(4);
                action = new Action();
                action.setBuyCount(1);
                action.setMoneyValue(4);
                //LKDoc: +4 geld
                break;

            case SCHMIEDE:
                setId(5);
                action = new Action();
                action.setCardCount(3);
                //LKDoc: +3 Karten
                break;

            case MARKT:
                setId(6);
                action = new Action();
                action.setCardCount(1);
                action.setActionCount(1);
                action.setMoneyValue(1);
                action.setBuyCount(1);
                //LKDoc: +1 Karte, +1 Aktion, +1 Kauf, +1 Geld
                break;

            case HEXE: //
                setId(7);
                action = new Action();
                action.setCardCount(1);
                action.setCurseCount(1);
                //LKDoc: +2 Karten, Jeder Mitspieler muss sich ine Fluchkarte nehmen.
                break;

            case MINE: //
                setId(8);
                action = new Action();
                action.setCardCount(-1);
                action.setTakeMoneyCardThatCostThreeMoreThanOld(true);
                action.setTakeCardOnHand(true);
                /*LKDoc: Entsorge eine Geldkarte aus deiner Hand.
                Nimm dir eine Geldkarte, die bis zu 3 mehr kostet.
                Nimm diese Geldkarte sofort auf die Hand.*/
                break;

            case MILIZ:
                setId(9);//LKDoc: Miliz ist eine Angriffskarte
                action = new Action();
                action.setMoneyValue(2);
                action.setThrowEveryUserCardsUntilThreeLeft(true);
                /* +2 Geld
                Jeder Mitspieler legt Karten ab, bis er nur noch drei Karten auf der Hand hat.*/
                break;

        }
        return action;
    }
}
