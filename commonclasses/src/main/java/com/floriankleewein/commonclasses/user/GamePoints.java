package com.floriankleewein.commonclasses.user;

/**
 * Class that manage the points for a Player. Every Param can be increased and decreased. Those Methods should be called in the Class "Card"!
 */

public class GamePoints {


    private int Coins;
    private int WinningPoints;
    private int PlaysAmount;
    private int BuyAmounts;

    public GamePoints() {
        Coins = 0;
        WinningPoints = 0;
        PlaysAmount = 1;
        BuyAmounts = 1;
    }

    public void modifyCoins(int amountCoins) {
        this.Coins += amountCoins;
    }

    public void modifyWinningPoints(int wp) {
        this.WinningPoints += wp;
    }

    public void modifyPlayAmounts(int playsAmount) {
        this.PlaysAmount += playsAmount;
    }

    public void modifyBuyAmounts(int buyAmounts) {
        this.BuyAmounts += buyAmounts;
    }

    public void setPointsDefault() {
        Coins = 0;
        PlaysAmount = 1;
        BuyAmounts = 1;
    }

    public int getCoins() {
        return Coins;
    }

    public void setCoins(int coins) {
        Coins = coins;
    }

    public int getWinningPoints() {
        return WinningPoints;
    }

    public void setWinningPoints(int winningPoints) {
        WinningPoints = winningPoints;
    }

    public int getPlaysAmount() {
        return PlaysAmount;
    }

    public void setPlaysAmount(int playsAmount) {
        PlaysAmount = playsAmount;
    }

    public int getBuyAmounts() {
        return BuyAmounts;
    }

    public void setBuyAmounts(int buyAmounts) {
        BuyAmounts = buyAmounts;
    }

}


