package com.group7.dominion.Cards;

import com.group7.dominion.CardActivity;
import com.group7.dominion.R;

public class ActionCard extends Card {
    private Action action;
    private ActionType actionType;

    public ActionCard(CardActivity cardActivity, int price, ActionType actionType) {
        super(cardActivity, price);
        this.actionType = actionType;
    }

    public void init() {
        this.action = calculateAction();
    }

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

    private void setImageResource(int id) {
        getImageButton().setImageResource(id);
    }

    private Action calculateAction() {
        Action action = null;

        switch(actionType){
            case BURGGRABEN:
                setImageResource(R.drawable.burggraben);
                action = new Action();
                action.setCardCount(2);
                action.setThrowEveryUserCardsUntilThreeLeft(false); // Da die einzige Angriffskarte die Miliz ist hat diese keine Wirkung auf den Spieler und somit muss der boolean für diesen Spieler false sein
                // Wenn ein Mitspiler eine Angriffskarte ausspielt, darfst du diese Karte aus deiner Hand aufdecken. Der Angriff hat damit keine Wirkung auf dich.
                break;

            case DORF:
                //setImageResource(R.drawable.dorf);
                action = new Action();
                action.setCardCount(1);
                action.setActionCount(2);
                // +1 Karte, +2 Aktionen
                break;

            case HOLZFAELLER:
                //setImageResource(R.drawable.holzfaeller);
                action = new Action();
                action.setBuyCount(1);
                action.setMoneyValue(1);
                // +1 Kauf, +1 Geld
                break;

            case KELLER: //
                //setImageResource(R.drawable.keller);
                action = new Action();
                action.setActionCount(1);
                action.setThrowAnyAmountCards(true); // Maximale Anzahl an Handkarten kann hier abgelegt werden => Input für den User der auf diese Anzahl begrenzt
                // +1 Aktion, Lege eine beliebeige Anzahl an Handkarten ab.
                // Ziehe für jede abgelegte Karte eine Karte nach.
                break;

            case WERKSTATT: //
                //setImageResource(R.drawable.werkstatt);
                action = new Action();
                action.setCardCount(1);
                action.setMaxMoneyValue(4);
                // Nimm dir eine Karte die bis zu 4 kostet.
                break;

            case SCHMIEDE:
                //setImageResource(R.drawable.schmiede);
                action = new Action();
                action.setCardCount(3);
                // +3 Karten
                break;

            case MARKT:
                //setImageResource(R.drawable.markt);
                action = new Action();
                action.setCardCount(1);
                action.setActionCount(1);
                action.setMoneyValue(1);
                action.setBuyCount(1);
                // +1 Karte, +1 Aktion, +1 Kauf, +1 Geld
                break;

            case HEXE: //
                //setImageResource(R.drawable.hexe);
                action = new Action();
                action.setCardCount(1);
                action.setCurseCount(1);
                // +2 Karten, Jeder Mitspieler muss sich ine Fluchkarte nehmen.
                break;

            case MINE: //
                //setImageResource(R.drawable.mine);
                action = new Action();
                action.setCardCount(-1);
                action.setTakeMoneyCardThatCostThreeMoreThanOld(true);
                action.setTakeCardOnHand(true);
                // Entsorge eine Geldkarte aus deiner Hand.
                // Nimm dir eine Geldkarte, die bis zu 3 mehr kostet.
                // Nimm diese Geldkarte sofort auf die Hand.
                break;

            case MILIZ: // Miliz ist eine Angriffskarte
                //setImageResource(R.drawable.miliz);
                action = new Action();
                action.setMoneyValue(2);
                action.setThrowEveryUserCardsUntilThreeLeft(true);
                // +2 Geld
                // Jeder Mitspieler legt Karten ab, bis er nur noch drei Karten auf der Hand hat.
                break;

        }
        return action;

        // bei 2 Spielern je 8 Karten
        // bei 3-4 Spielern je 12 Karten
        // weitere mögliche Aktionskarten: Umbau, Laboratorium, Jahrmarkt
    }
}
