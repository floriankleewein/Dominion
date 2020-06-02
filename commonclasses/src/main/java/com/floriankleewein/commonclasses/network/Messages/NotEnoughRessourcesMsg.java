package com.floriankleewein.commonclasses.network.Messages;

import com.floriankleewein.commonclasses.network.BaseMessage;

public class NotEnoughRessourcesMsg extends BaseMessage {
    /**
     * Send back to Client if they do not have enough Action- or Buypoints to play a card
     * or if they don't have enough Money to buy a card.
     */

    boolean notEnoughMoney = false;
    boolean notEnougAP = false;
    boolean notEnoughBP = false;

    public NotEnoughRessourcesMsg() {

    }

    /**
     * 1 = not enough ActionPts, 2 = Not Enough BuyPts, 3 = Not Enough Money, 4 = Any Other Error
     *
     * @param errorNumber
     */
    public NotEnoughRessourcesMsg(int errorNumber) {
        switch (errorNumber) {
            case 1:
                setNotEnougAP(true);
                break;
            case 2:
                setNotEnoughBP(true);
                break;
            case 3:
                setNotEnoughMoney(true);
                break;
            default:
                break;
        }
    }

    public boolean isNotEnoughMoney() {
        return notEnoughMoney;
    }

    public void setNotEnoughMoney(boolean notEnoughMoney) {
        this.notEnoughMoney = notEnoughMoney;
    }

    public boolean isNotEnougAP() {
        return notEnougAP;
    }

    public void setNotEnougAP(boolean notEnougAP) {
        this.notEnougAP = notEnougAP;
    }

    public boolean isNotEnoughBP() {
        return notEnoughBP;
    }

    public void setNotEnoughBP(boolean notEnoughBP) {
        this.notEnoughBP = notEnoughBP;
    }

    /**
     * Checks which error got triggered, 1 = Not enough Action Pts, 2 = Not enough Buy Pts,
     * 3 = Not Enough Money, 4 = Any Other Error.
     *
     * @return
     */
    public int checkError() {
        if (isNotEnougAP()) return 1;
        else if (isNotEnoughBP()) return 2;
        else if (isNotEnoughMoney()) return 3;
        else return 4;
    }

}
