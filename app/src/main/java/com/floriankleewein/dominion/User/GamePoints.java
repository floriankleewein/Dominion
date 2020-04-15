package com.floriankleewein.dominion.User;

/**
 * Class that manage the points for a Player. Every Param can be increased and decreased. Those Methods should be called in the Class "Card"!
 */

public class GamePoints {


    private int Coins;
    private int WinningPoints;
    private int PlaysAmount;
    private int BuyAmounts;

    GamePoints() {
        Coins = 0;
        WinningPoints = 0;
        PlaysAmount = 1;
        BuyAmounts = 1;
    }

    public void increaseCoins(int amountCoins) {
        this.Coins = Coins + amountCoins;
    }

    public void decreaseCoins(int amountCoins) {
        if (amountCoins <= this.Coins) {
            this.Coins = Coins - amountCoins;
        }
        /**
         * Maybe we should add an Exception if necessary
         */
    }

    public void increaseWinningPoints(int wp) {
        this.WinningPoints = WinningPoints + wp;
    }

    public void decreaseWinningPoints(int wp) {
        this.WinningPoints = WinningPoints - wp;
        /**
         * No If because WinningPoints CAN be negative!
         */
    }

    public void increasePlaysAmount(int playsAmount) {
        this.PlaysAmount += playsAmount;
    }

    public void decreasePlaysAmount(int playsAmount) {
        if (playsAmount <= this.PlaysAmount) {
            this.PlaysAmount -= playsAmount;
        }
    }

    public void increaseBuyAmounts(int buyAmounts) {
        this.BuyAmounts += buyAmounts;
    }

    public void decreaseBuyAmounts(int buyAmounts) {
        if (buyAmounts <= this.BuyAmounts) {
            this.BuyAmounts -= buyAmounts;
        }
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


