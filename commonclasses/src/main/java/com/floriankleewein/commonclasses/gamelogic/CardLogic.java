package com.floriankleewein.commonclasses.gamelogic;

import com.floriankleewein.commonclasses.cards.Action;
import com.floriankleewein.commonclasses.cards.ActionCard;
import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.cards.EstateType;
import com.floriankleewein.commonclasses.cards.MoneyCard;
import com.floriankleewein.commonclasses.cards.MoneyType;
import com.floriankleewein.commonclasses.user.User;

import java.util.Collections;
import java.util.LinkedList;

public class CardLogic {
    private GameHandler gameHandler;
    private int drawableCards;
    private int addBuyPts;
    private int addActionPts;
    private int curseCount;
    private int moneyValue;
    private int winningPts;
    boolean discardCardsOfOtherPlayers;
    boolean replaceMoneyCard;

    /**
     * Empty Konstruktor in case it needs to be sent over kryonet.
     */
    public CardLogic() {

    }

    /**
     * Initialize CardLogic for the gamehandler.
     * @param gameHandler
     */
    public CardLogic(GameHandler gameHandler) {
        setGameHandler(gameHandler);
    }

    /**
     * Returns true if attack card was played..
     * @return
     */
    public boolean isDiscardCardsOfOtherPlayers() {
        return discardCardsOfOtherPlayers;
    }

    /**
     * Set to true if attack card is played.
     * @param discardCardsOfOtherPlayers
     */
    public void setDiscardCardsOfOtherPlayers(boolean discardCardsOfOtherPlayers) {
        this.discardCardsOfOtherPlayers = discardCardsOfOtherPlayers;
    }

    /**
     * Return winning points.
     * @return
     */
    public int getWinningPts() {
        return winningPts;
    }

    /**
     * Set WinningPts.
     * @param winningPts
     */
    public void setWinningPts(int winningPts) {
        this.winningPts = winningPts;
    }

    /**
     * Return money modified of played card.
     * @return
     */
    public int getMoneyValue() {
        return moneyValue;
    }

    /**
     * Set Money value.
     * @param moneyValue
     */
    public void setMoneyValue(int moneyValue) {
        this.moneyValue = moneyValue;
    }

    /**
     * Set gamehandler.
     * @param gameHandler
     */
    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    /**
     * Get how many cards need to be drawn.
     * @return
     */
    public int getDrawableCards() {
        return drawableCards;
    }

    /**
     * Sets how many cards should be drawn
     * @param drawableCards
     */
    public void setDrawableCards(int drawableCards) {
        this.drawableCards = drawableCards;
    }

    /**
     * Get how many points should be added to Buy Value.
     * @return
     */
    public int getAddBuyPts() {
        return addBuyPts;
    }

    /**
     * Set how many points should be added to Buy Value.
     * @param addBuyPts
     */
    public void setAddBuyPts(int addBuyPts) {
        this.addBuyPts = addBuyPts;
    }

    /**
     * Get how many Action Points should be added.
     * @return
     */
    public int getAddActionPts() {
        return addActionPts;
    }

    /**
     * Set how many Action Points should be added.
     * @param addActionPts
     */
    public void setAddActionPts(int addActionPts) {
        this.addActionPts = addActionPts;
    }

    /**
     * Return the curse count.
     * @return
     */
    public int getCurseCount() {
        return curseCount;
    }

    /**
     * Set the curse value.
     * @param curseCount
     */
    public void setCurseCount(int curseCount) {
        this.curseCount = curseCount;
    }

    /**
     * Return if MoneyCard needs to be replaced.
     * @return
     */
    public boolean isReplaceMoneyCard() {
        return replaceMoneyCard;
    }

    /**
     * Set if MoneyCard should be replaced.
     * @param replaceMoneyCard
     */
    public void setReplaceMoneyCard(boolean replaceMoneyCard) {
        this.replaceMoneyCard = replaceMoneyCard;
    }

    /**
     * Set all Variables depending if card is MoneyCard or ActionCard.
     * @param card
     */
    public void setVariables(Card card) {
        if (card instanceof MoneyCard) setVariables((MoneyCard) card);
        else if (card instanceof ActionCard) setVariables((ActionCard) card);
    }

    /**
     * Sets all possible value for Action cards.
     * @param card
     */
    public void setVariables(ActionCard card) {
        Action action = card.getAction();
        setDrawableCards(action.getCardCount());
        setAddBuyPts(action.getBuyCount());
        setAddActionPts(action.getActionCount());
        setCurseCount(action.getCurseCount());
        setMoneyValue(action.getMoneyValue());
        setDiscardCardsOfOtherPlayers(action.isThrowEveryUserCardsUntilThreeLeft());
        setReplaceMoneyCard(action.isTakeMoneyCardThatCostThreeMoreThanOld());
    }

    /**
     * Sets all possible Variables for MoneyCards.
     * @param card
     */
    public void setVariables(MoneyCard card) {
        setMoneyValue(card.getWorth());
    }

    /**
     * Call the correct Method to execute Card Effects on the game.
     * @param card
     */
    public void doCardLogic(Card card) {
        if (card instanceof ActionCard) doCardLogic((ActionCard) card);
        else if (card instanceof MoneyCard) doCardLogic((MoneyCard) card);
    }


    /**
     * Handles Action Card logic. Check the set values and calls the implemented methods
     * to handle card logic. Does not require a check for a specific ActionCard as it simply
     * checks the values of the card that needs to be played.
     * @param card
     */
    public void doCardLogic(ActionCard card) {
        setVariables(card);

        if (drawableCards > 0) {
            drawCards();
        }
        if (addActionPts > 0) {
            modifyActionPts();
        }

        if (addBuyPts > 0) {
            modifyBuyPts();
        }
        if (curseCount > 0) {
            cursePlayers();
        }

        if (moneyValue > 0) {
            modifyMoneyAmount();
        }

        if (isDiscardCardsOfOtherPlayers()) {
            makePlayersDropCards();
        }

        if (isReplaceMoneyCard()) {
            replaceOldMoneyCard();
        }
        gameHandler.getActiveUser().getUserCards().playCard(card);
    }

    /**
     * Handles card logic for MoneyCards. Check the set Values of the played card and
     * executes it.
     * @param card
     */
    public void doCardLogic(MoneyCard card) {
        setVariables(card);
        modifyMoneyAmount();
        gameHandler.getActiveUser().getUserCards().playCard(card);
    }

    /**
     * Replaces Money Card in hand. Copper -> Silver, Silver -> Gold. If there are only Gold
     * cards in hand or no Money Cards at all it will have no effect.
     */
    private void replaceOldMoneyCard() {
        LinkedList<Card> hand = gameHandler.getActiveUser().getUserCards().getHandCards();
        LinkedList<MoneyCard> moneyCardsinHand = new LinkedList<>();
        int indexOfCard = -1;
        boolean hasCopperMoney = hand.stream().filter(card -> card instanceof MoneyCard).anyMatch(card -> card.getId() == 14); //Kuper = 13, Silber = 14 etc.
        boolean hasSilverMoney = hand.stream().filter(card -> card instanceof MoneyCard).anyMatch(card -> card.getId() == 15);
        for (Card card : hand) {
            if (card instanceof MoneyCard) {
                moneyCardsinHand.add((MoneyCard) card);
            }
        }
        if (hasSilverMoney) {
            for (MoneyCard card : moneyCardsinHand) {
                if (card.getMoneyType().equals(MoneyType.SILBER)) {
                    indexOfCard = hand.indexOf(card);
                }
            }
        } else if (hasCopperMoney) {
            for (MoneyCard card : moneyCardsinHand) {
                if (card.getMoneyType().equals(MoneyType.KUPFER)) {
                    indexOfCard = hand.indexOf(card);
                }
            }
        } else {
            return;
        }
        if (indexOfCard >= 0) {
            hand.remove(indexOfCard);
            if (hasSilverMoney) {
                hand.add(gameHandler.getBoard().getBuyField().pickCard(MoneyType.GOLD));
            } else {
                hand.add(gameHandler.getBoard().getBuyField().pickCard(MoneyType.SILBER));
            }
            gameHandler.getActiveUser().getUserCards().setHandCards(hand);
        }
    }

    /**
     * Modifies the Buy Value of the active player.
     */
    private void modifyBuyPts() {
        gameHandler.getActiveUser().getGamePoints().modifyBuyAmounts(addBuyPts);
    }

    /**
     * Modifies the Action Value of the active player.
     */
    private void modifyActionPts() {
        gameHandler.getActiveUser().getGamePoints().modifyPlayAmounts(addActionPts);
    }

    /**
     * Draws the required amount of cards for the active player.
     */
    private void drawCards() {
        gameHandler.getActiveUser().getUserCards().addDeckCardtoHandCard(drawableCards); // draw cards if possible
    }

    /**
     * Checks if players have a Moat in hand otherwise makes them drop down to three cards in Hand.
     * Random cards will be dropped until only three are left in hand.
     */
    private void makePlayersDropCards() {
        for (User u : gameHandler.getGame().getPlayerList()) {
            if (!u.equals(gameHandler.getActiveUser()) && !u.getUserCards().hasMoat()) {
                while (u.getUserCards().getHandCards().size() > 3) {
                    Collections.shuffle(u.getUserCards().getHandCards());
                    u.getUserCards().playCard(u.getUserCards().getHandCards().getLast());
                }
            }
        }
    }

    /**
     * Puts a curse in the deck of cursed players unless they have a Moat to counter attack cards.
     */
    private void cursePlayers() {
        for (User u : gameHandler.getGame().getPlayerList()) {
            if (!u.getUserCards().hasMoat() && !u.equals(gameHandler.getActiveUser())) {
                u.getUserCards().addCardtoDeck(gameHandler.getBoard().getBuyField().pickCard(EstateType.FLUCH));
                u.getGamePoints().modifyWinningPoints(-1);
            }
        }
    }

    /**
     * Modifies the coin value of the active player.
     */
    private void modifyMoneyAmount() {
        gameHandler.getActiveUser().getGamePoints().modifyCoins(moneyValue);
    }
}
