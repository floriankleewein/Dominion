package com.floriankleewein.commonclasses.Cards;

public class Action {
    private int actionCount; // Man darf eine Action wie Karten ablegen durchführen (dieser Wert beschreibt die Aktionen die man dürführen darf)
    private int cardCount; // Nimm irgend eine Karte (dieser Wert beschreibt die Anzahl der Karten die man nehmen darf)
    private int buyCount; // Kauf dir eine Actions wir auch Provinz oder Geld (dieser Wert beschreibt die Anzahl der Karten die man kaufen darf)
    private int moneyValue; // Nimm eine Kupfer, Silber oder Gold Karte je nach Wert
    private int maxMoneyValue; // Maximaler Karten Wert
    private int curseCount; // Das sind die Anzahl an Fluch Karten die genommen werden müssen
    private boolean takeCardOnHand;
    private boolean throwAnyAmountCards;
    private boolean takeMoneyCardThatCostThreeMoreThanOld;
    private boolean throwEveryUserCardsUntilThreeLeft;
    // Füge andere Eingenschaften später hinzu

    public int getActionCount() {
        return actionCount;
    }

    public void setActionCount(int actionCount) {
        this.actionCount = actionCount;
    }

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public int getMoneyValue() {
        return moneyValue;
    }

    public void setMoneyValue(int moneyValue) {
        this.moneyValue = moneyValue;
    }

    public int getMaxMoneyValue() {
        return maxMoneyValue;
    }

    public void setMaxMoneyValue(int maxMoneyValue) {
        this.maxMoneyValue = maxMoneyValue;
    }

    public boolean isThrowAnyAmountCards() {
        return throwAnyAmountCards;
    }

    public void setThrowAnyAmountCards(boolean throwAnyAmountCards) {
        this.throwAnyAmountCards = throwAnyAmountCards;
    }

    public int getCurseCount() {
        return curseCount;
    }

    public void setCurseCount(int curseCount) {
        this.curseCount = curseCount;
    }

    public boolean isTakeCardOnHand() {
        return takeCardOnHand;
    }

    public void setTakeCardOnHand(boolean takeCardOnHand) {
        this.takeCardOnHand = takeCardOnHand;
    }

    public boolean isTakeMoneyCardThatCostThreeMoreThanOld() {
        return takeMoneyCardThatCostThreeMoreThanOld;
    }

    public void setTakeMoneyCardThatCostThreeMoreThanOld(boolean takeMoneyCardThatCostThreeMoreThanOld) {
        this.takeMoneyCardThatCostThreeMoreThanOld = takeMoneyCardThatCostThreeMoreThanOld;
    }

    public boolean isThrowEveryUserCardsUntilThreeLeft() {
        return throwEveryUserCardsUntilThreeLeft;
    }

    public void setThrowEveryUserCardsUntilThreeLeft(boolean throwEveryUserCardsUntilThreeLeft) {
        this.throwEveryUserCardsUntilThreeLeft = throwEveryUserCardsUntilThreeLeft;
    }
}
