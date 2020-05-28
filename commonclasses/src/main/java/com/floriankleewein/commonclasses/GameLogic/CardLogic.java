package com.floriankleewein.commonclasses.GameLogic;

import com.floriankleewein.commonclasses.Cards.Action;
import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Cards.EstateType;
import com.floriankleewein.commonclasses.Cards.MoneyCard;
import com.floriankleewein.commonclasses.Cards.MoneyType;
import com.floriankleewein.commonclasses.User.User;

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

    public CardLogic() {

    }

    public CardLogic(GameHandler gameHandler) {
        setGameHandler(gameHandler);
    }

    public boolean isDiscardCardsOfOtherPlayers() {
        return discardCardsOfOtherPlayers;
    }

    public void setDiscardCardsOfOtherPlayers(boolean discardCardsOfOtherPlayers) {
        this.discardCardsOfOtherPlayers = discardCardsOfOtherPlayers;
    }

    public int getWinningPts() {
        return winningPts;
    }

    public void setWinningPts(int winningPts) {
        this.winningPts = winningPts;
    }

    public int getMoneyValue() {
        return moneyValue;
    }

    public void setMoneyValue(int moneyValue) {
        this.moneyValue = moneyValue;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public int getDrawableCards() {
        return drawableCards;
    }

    public void setDrawableCards(int drawableCards) {
        this.drawableCards = drawableCards;
    }

    public int getAddBuyPts() {
        return addBuyPts;
    }

    public void setAddBuyPts(int addBuyPts) {
        this.addBuyPts = addBuyPts;
    }

    public int getAddActionPts() {
        return addActionPts;
    }

    public void setAddActionPts(int addActionPts) {
        this.addActionPts = addActionPts;
    }

    public int getCurseCount() {
        return curseCount;
    }

    public void setCurseCount(int curseCount) {
        this.curseCount = curseCount;
    }

    public boolean isReplaceMoneyCard() {
        return replaceMoneyCard;
    }

    public void setReplaceMoneyCard(boolean replaceMoneyCard) {
        this.replaceMoneyCard = replaceMoneyCard;
    }

    public void setVariables(Card card) {
        if (card instanceof MoneyCard) setVariables((MoneyCard) card);
        else if (card instanceof ActionCard) setVariables((ActionCard) card);
    }

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

    public void setVariables(MoneyCard card) {
        setMoneyValue(card.getWorth());
    }

    public void doCardLogic(Card card) {
        if (card instanceof ActionCard) doCardLogic((ActionCard) card);
        else if (card instanceof MoneyCard) doCardLogic((MoneyCard) card);
    }


    /**
     * Method that handles a card irregardless of type.
     *
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
    }


    private void replaceOldMoneyCard() {
        LinkedList<Card> hand = gameHandler.getActiveUser().getUserCards().getHandCards();
        int indexOfCard = -1;
        boolean hasCopperMoney = hand.stream().filter(card -> card instanceof MoneyCard).anyMatch(card -> card.getId() == 13); //Kuper = 13, Silber = 14 etc.
        boolean hasSilverMoney = hand.stream().filter(card -> card instanceof MoneyCard).anyMatch(card -> card.getId() == 14);
        if (hasSilverMoney) {
            for (Card card : hand) {
                if (card instanceof MoneyCard) {
                    MoneyCard mnyCard = (MoneyCard) card;
                    if (mnyCard.getMoneyType().equals(MoneyType.SILBER)) {
                        indexOfCard = hand.indexOf(card);
                    }
                }
            }
        } else if (hasCopperMoney) {
            for (Card card : hand) {
                if (card instanceof MoneyCard) {
                    MoneyCard mnyCard = (MoneyCard) card;
                    if (mnyCard.getMoneyType().equals(MoneyType.KUPFER)) {
                        indexOfCard = hand.indexOf(card);
                    }
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

    private void modifyBuyPts() {
        gameHandler.getActiveUser().getGamePoints().modifyBuyAmounts(addBuyPts);
    }

    private void modifyActionPts() {
        gameHandler.getActiveUser().getGamePoints().modifyPlayAmounts(addActionPts);
    }

    private void drawCards() {
        gameHandler.getActiveUser().getUserCards().addDeckCardtoHandCard(drawableCards); // draw cards if possible
    }

    private void makePlayersDropCards() {
        for (User u : gameHandler.getGame().getPlayerList()) {
            if (!u.equals(gameHandler.getActiveUser()) && !u.getUserCards().hasMoat() && u.getUserCards().getHandCards().size() > 3) {
                for (int i = 0; i < 3; i++) {
                    Collections.shuffle(u.getUserCards().getHandCards());
                    u.getUserCards().playCard(u.getUserCards().getHandCards().getLast());
                }
            }
        }
    }

    private void cursePlayers() {
        for (User u : gameHandler.getGame().getPlayerList()) {
            if (!u.getUserCards().hasMoat() && !u.equals(gameHandler.getActiveUser())) {
                u.getUserCards().addCardtoDeck(gameHandler.getBoard().getBuyField().pickCard(EstateType.FLUCH));
                u.getGamePoints().modifyWinningPoints(-1);
            }
        }
    }

    public void doCardLogic(MoneyCard card) {
        setVariables(card);
        modifyMoneyAmount();
    }

    private void modifyMoneyAmount() {
        gameHandler.getActiveUser().getGamePoints().modifyCoins(moneyValue);
    }
}
