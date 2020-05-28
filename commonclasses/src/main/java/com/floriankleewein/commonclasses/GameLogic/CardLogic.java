package com.floriankleewein.commonclasses.GameLogic;

import com.floriankleewein.commonclasses.Cards.Action;
import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Cards.EstateType;
import com.floriankleewein.commonclasses.Cards.MoneyCard;
import com.floriankleewein.commonclasses.User.User;

public class CardLogic {
    private GameHandler gameHandler;
    private int drawableCards;
    private int addBuyPts;
    private int addActionPts;
    private int curseCount;
    private int moneyValue;

    public CardLogic() {

    }

    public GameHandler getGameHandler() {
        return gameHandler;
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

    private void setVariables(ActionCard card) {
        Action action = card.getAction();
        setDrawableCards(action.getCardCount());
        setAddBuyPts(action.getBuyCount());
        setAddActionPts(action.getActionCount());
        setCurseCount(action.getCurseCount());
    }

    public void setVariables(MoneyCard card) {

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
        if (getCurseCount() > 0) {
            cursePlayers();
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

    private void cursePlayers() {
        for (User u : gameHandler.getGame().getPlayerList()) {
            if (!u.getUserCards().hasMoat() && !u.equals(getGameHandler().getActiveUser())) {
                u.getUserCards().addCardtoDeck(getGameHandler().getBoard().getBuyField().pickCard(EstateType.FLUCH));
                u.getGamePoints().modifyWinningPoints(-1);
            }
        }
    }

    public void doCardLogic(MoneyCard card) {
        setVariables(card);
    }
}
