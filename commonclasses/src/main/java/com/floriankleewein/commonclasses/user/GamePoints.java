package com.floriankleewein.commonclasses.user;

/**@Author Maurer Florian
 * Class that manage the points for a Player. Every Param can be increased and decreased.
 */

public class GamePoints {


    private int coins;
    private int winningPoints;
    private int playsAmount;
    private int buyAmounts;

    public GamePoints() {
        coins = 0;
        winningPoints = 0;
        playsAmount = 1;
        buyAmounts = 1;
    }

    public void modifyCoins(int amountCoins) {
        this.coins += amountCoins;
    }

    public void modifyWinningPoints(int wp) {
        this.winningPoints += wp;
    }

    public void modifyPlayAmounts(int playsAmount) {
        this.playsAmount += playsAmount;
    }

    public void modifyBuyAmounts(int buyAmounts) {
        this.buyAmounts += buyAmounts;
    }

    /**@Author Maurer Florian
     * Set back the Points to the default. a player has 1x play and buy amount per turn.
     */
    public void setPointsDefault() {
        coins = 0;
        playsAmount = 1;
        buyAmounts = 1;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getWinningPoints() {
        return winningPoints;
    }

    public void setWinningPoints(int winningPoints) {
        this.winningPoints = winningPoints;
    }

    public int getPlaysAmount() {
        return playsAmount;
    }

    public void setPlaysAmount(int playsAmount) {
        this.playsAmount = playsAmount;
    }

    public int getBuyAmounts() {
        return buyAmounts;
    }

    public void setBuyAmounts(int buyAmounts) {
        this.buyAmounts = buyAmounts;
    }

}


