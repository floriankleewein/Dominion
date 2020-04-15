package com.floriankleewein.dominion;

public class ActionCard extends Card {
    private Action action;
    private ActionType actionType;

    public ActionCard(int value, int numberOfCards, ActionType actionType) {
        super(value, numberOfCards);
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
        //if(actionType == ActionType.BURGGRABEN) {
            // Baue Response
         //   action.setBuyCount(1);
        //}
        // TODO: Other ifs

        switch(actionType){
            case BURGGRABEN:
                break;

            case DORF:
                // +1 Karte, +2 Aktionen
                break;

            case HOLZFAELLER:
                // +1 Kauf, +1 Geld
                break;

            case KELLER:
                // +1 Aktion, Lege eine beliebeige Anzahl an Handkarten ab.
                // Ziehe für jede abgelegte Karte eine Karte nach.
                break;

            case WERKSTATT:
                // Nimm dir eine Karte die bis zu 4 kostet.
                break;

            case SCHMIEDE:
                // +3 Karten
                break;

            case MARKT:
                // +1 Karte, +1 Aktion, +1 Kauf, +1 Geld
                break;

            case HEXE:
                // +2 Karten, Jeder Mitspieler muss sich ine Fluchkarte nehmen.
                break;

            case MINE:
                // Entsorge eine Geldkarte aus deiner Hand.
                // Nimm dir eine Geldkarte, die bis zu 3 mehr kostet.
                // Nimm diese Geldkarte sofort auf die Hand.
                break;

            case MILIZ:
                // +2 Geld
                // Jeder Mitspieler legt Karten ab, bis er nur och drei Karten auf der Hand hat.
                break;

        }
        return action;

        // bei 2 Spielern je 8 Karten
        // bei 3-4 Spielern je 12 Karten
        // weitere mögliche Aktionskarten: Umbau, Laboratorium, Jahrmarkt
    }
}
