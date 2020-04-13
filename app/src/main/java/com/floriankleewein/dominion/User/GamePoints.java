package com.floriankleewein.dominion.User;

/**
 * Class that manage the points for a Player. Every Param can be increased and decreased. Those Methods should be called in the Class "Card"!
 */

public class GamePoints {
    int Coins;
    int WinningPoints;
    int PlaysAmount;
    int BuyAmounts;

    GamePoints() {
        Coins = 0;
        WinningPoints = 0;
        PlaysAmount = 1;
        BuyAmounts = 1;
    }

    String getCardType(Object PlayedCard) {
        String CardType = "";
        /**
         * Should return the Type of the Card, so it can decied if it's a Card for Points, coins or action
         *  Not necessary if the Method underneath will be called in the Cards.
         */
        return CardType;
    }

    void increaseCoins(int amountCoins) {
        this.Coins = Coins + amountCoins;
    }

    void decreaseCoins(int amountCoins) {
        if (amountCoins <= this.Coins) {
            this.Coins = Coins - amountCoins;
        }
        /**
         * Maybe we should add an Exception if necessary
         */
    }

    void increaseWinningPoints(int wp) {
        this.WinningPoints = WinningPoints + wp;
    }

    void decreaseWinningPoints(int wp) {
        this.WinningPoints = WinningPoints - wp;
        /**
         * No If because WinningPoints CAN be negative!
         */
    }

    void increasePlaysAmount(int playsAmount) {
        this.PlaysAmount += playsAmount;
    }

    void decreasePlaysAmount(int playsAmount) {
        if (playsAmount <= this.PlaysAmount) {
            this.PlaysAmount -= playsAmount;
        }
    }

    void increaseBuyAmounts(int buyAmounts) {
        this.BuyAmounts += buyAmounts;
    }

    void decreaseBuyAmounts(int buyAmounts) {
        if (buyAmounts <= this.BuyAmounts) {
            this.BuyAmounts -= buyAmounts;
        }
    }

}


