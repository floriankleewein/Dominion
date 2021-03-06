package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.board.Board;
import com.floriankleewein.commonclasses.cards.ActionCard;
import com.floriankleewein.commonclasses.cards.ActionType;
import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.cards.EstateCard;
import com.floriankleewein.commonclasses.cards.EstateType;
import com.floriankleewein.commonclasses.cards.MoneyCard;
import com.floriankleewein.commonclasses.cards.MoneyType;
import com.floriankleewein.commonclasses.gamelogic.GameHandler;
import com.floriankleewein.commonclasses.gamelogic.PlayStatus;
import com.floriankleewein.commonclasses.network.GameUpdateMsg;
import com.floriankleewein.commonclasses.user.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class GameHandlerTest {

    private Game game;
    private GameHandler m_cut;
    private final int CON_COPPER = 7;
    private final int CON_ANWESEN = 3;

    @Before
    public void setup() {
        game = Game.getGame();
        User user1 = new User("Flo");
        User user2 = new User("Laura");
        User user3 = new User("Florian");
        User user4 = new User("TomTurbo");
        game.addPlayer(user1);
        game.addPlayer(user2);
        game.addPlayer(user3);
        game.addPlayer(user4);
        m_cut = new GameHandler(game);
        m_cut.prepareGame();
    }

    @Test
    public void checkBoardBuyFields() {
        Assert.assertEquals(178, m_cut.getBoard().getBuyField().getCardsToBuy().size());
    }

    @Test
    public void checkBoardActionField() {
        Assert.assertEquals(100, m_cut.getBoard().getActionField().getActionCardsToBuy().size());
    }

    @Test
    public void checkStarterHands() {
        for (User u : m_cut.getGame().getPlayerList()) {
            LinkedList<Card> allCards = new LinkedList<>();
            allCards.addAll(u.getUserCards().getDeck());
            allCards.addAll(u.getUserCards().getDiscardCards());
            allCards.addAll(u.getUserCards().getHandCards());
            Assert.assertEquals(10, allCards.size());
            Assert.assertEquals(7, allCards.stream().filter(card -> card.getId() == 14).count()); // Kupfer
            Assert.assertEquals(3, allCards.stream().filter(card -> card.getId() == 12).count()); // Anwesen
        }
    }

    @Test
    public void checkNewTurn() {
        List<User> activeUsers = new ArrayList<>();
        activeUsers.addAll(m_cut.getGame().getPlayerList());
        Assert.assertEquals(activeUsers.get(0).getUserName(), m_cut.getActiveUser().getUserName());
        m_cut.newTurn();
        Assert.assertEquals(activeUsers.get(1).getUserName(), m_cut.getActiveUser().getUserName());
    }

    @Test
    public void checkMoneyCardPlay() {
        Card card = new MoneyCard(0, 1, MoneyType.KUPFER);
        addCardtoHand(card);
        Assert.assertEquals(1, m_cut.getGame().getActivePlayer().getGamePoints().getPlaysAmount());
        m_cut.playCard(card);
        Assert.assertEquals(1, m_cut.getGame().getActivePlayer().getGamePoints().getPlaysAmount());
    }

    @After
    public void teardown() {
        Game.setGame(null);
        m_cut = null;
    }

    @Test
    public void changeTurnStateIfNull() {
        m_cut.setTurnState(null);
        Assert.assertEquals(null, m_cut.getTurnState());
    }

    @Test
    public void changeTurnStateSetter() {
        m_cut.setTurnState(null);
        Assert.assertEquals(null, m_cut.getTurnState());
        m_cut.setTurnState(1);
        Assert.assertEquals(PlayStatus.ACTION_PHASE, m_cut.getTurnState());
        m_cut.setTurnState(2);
        Assert.assertEquals(PlayStatus.BUY_PHASE, m_cut.getTurnState());
        m_cut.setTurnState(3);
        Assert.assertEquals(PlayStatus.NO_PLAY_PHASE, m_cut.getTurnState());
        m_cut.setTurnState(4);
        Assert.assertEquals(PlayStatus.NO_PLAY_PHASE, m_cut.getTurnState());
    }

    @Test
    public void updateGameHandlerWithMessage() {
        GameUpdateMsg msg = new GameUpdateMsg();
        msg.setBoard(new Board());
        Card playedCard = new MoneyCard(0, 1, MoneyType.KUPFER);
        msg.setPlayedCard(playedCard);
        Card boughtCard = new MoneyCard(3, 2, MoneyType.SILBER);
        msg.setClickedCard(boughtCard);
        msg.setGame(Game.getGame());
        m_cut.updateGameHandler(msg);
        Assert.assertEquals(playedCard.getId(), m_cut.getPlayedCard().getId());
        Assert.assertEquals(boughtCard.getId(), m_cut.getBuyCard().getId());
    }

    @Test
    public void checkPlayCardGeneral() {
        User myUser = m_cut.getActiveUser();
        myUser.getGamePoints().setPlaysAmount(1);
        m_cut.setTurnState(1);
        Card card = new ActionCard(5, ActionType.HEXE);
        m_cut.playCard(card);
        Assert.assertEquals(card, m_cut.getPlayedCard());
        Assert.assertEquals(0, myUser.getGamePoints().getPlaysAmount());
    }

    @Test
    public void checkPlayCardAction1() {
        Card card = new ActionCard(5, ActionType.HEXE);
        User myUser = m_cut.getActiveUser();
        m_cut.setTurnState(1);
        myUser.getGamePoints().setPlaysAmount(2);
        m_cut.playCard(card);
        Assert.assertEquals(1, myUser.getGamePoints().getPlaysAmount());
    }

    @Test
    public void checkPlayCardAction2() {
        Card card = new ActionCard(5, ActionType.DORF);
        addCardtoHand(card);
        User myUser = m_cut.getActiveUser();
        m_cut.setTurnState(1);
        Assert.assertEquals(1, myUser.getGamePoints().getPlaysAmount());
        Assert.assertEquals(6, myUser.getUserCards().getHandCards().size());
        m_cut.playCard(card);
        Assert.assertEquals(2, myUser.getGamePoints().getPlaysAmount());
        Assert.assertEquals(6, myUser.getUserCards().getHandCards().size());
    }

    @Test
    public void checkPlayCardActionWhenNotInCorrectPhase() {
        Card card = new ActionCard(5, ActionType.HEXE);
        m_cut.setTurnState(PlayStatus.BUY_PHASE);
        m_cut.playCard(card);
        Assert.assertEquals(1, m_cut.getActiveUser().getGamePoints().getPlaysAmount());
    }

    @Test
    public void checkPlayCardActionWhenNotInCorrectPhase2() {
        Card card = new ActionCard(5, ActionType.HEXE);
        m_cut.setTurnState(PlayStatus.NO_PLAY_PHASE);
        m_cut.playCard(card);
        Assert.assertEquals(1, m_cut.getActiveUser().getGamePoints().getPlaysAmount());
    }

    @Test
    public void checkPlayCardMoney() {
        Card card = new MoneyCard(6, 3, MoneyType.GOLD);
        Assert.assertEquals(0, m_cut.getActiveUser().getGamePoints().getCoins());
        m_cut.playCard(card);
        Assert.assertEquals(3, m_cut.getActiveUser().getGamePoints().getCoins());
    }

    @Test
    public void checkBuyMoneyCard() {
        Card card = new MoneyCard(6, 3, MoneyType.GOLD);
        m_cut.getActiveUser().getGamePoints().modifyCoins(6);
        Assert.assertEquals(6, m_cut.getActiveUser().getGamePoints().getCoins());
        m_cut.buyCard(card);
        Assert.assertEquals(0, m_cut.getActiveUser().getGamePoints().getCoins());
        Assert.assertEquals(0, m_cut.getActiveUser().getGamePoints().getBuyAmounts());
    }

    @Test
    public void checkBuyMoneyCardWithType() {
        Card card = new MoneyCard(6, 3, MoneyType.GOLD);
        m_cut.getActiveUser().getGamePoints().modifyCoins(6);
        User myUser = m_cut.getActiveUser();
        Assert.assertEquals(6, myUser.getGamePoints().getCoins());
        Assert.assertEquals(card.getId(), m_cut.buyCard(MoneyType.GOLD).getId());
        Assert.assertEquals(0, myUser.getGamePoints().getCoins());
        Assert.assertEquals(1, myUser.getGamePoints().getBuyAmounts());
        Assert.assertEquals(1, myUser.getUserCards().getDiscardCards().stream().filter(card1 -> card1.getId() == 16).count());
    }

    @Test
    public void checkBuyActionCard() {
        Card card = new ActionCard(5, ActionType.HOLZFAELLER);
        m_cut.getActiveUser().getGamePoints().modifyCoins(5);
        Assert.assertEquals(5, m_cut.getActiveUser().getGamePoints().getCoins());
        m_cut.buyCard(card);
        Assert.assertEquals(0, m_cut.getActiveUser().getGamePoints().getCoins());
        Assert.assertEquals(0, m_cut.getActiveUser().getGamePoints().getBuyAmounts());
    }

    @Test
    public void checkBuyActionCardWithType() {
        Card card = new ActionCard(3, ActionType.HOLZFAELLER);
        User myUser = m_cut.getActiveUser();
        myUser.getGamePoints().modifyCoins(5);
        Assert.assertEquals(5, myUser.getGamePoints().getCoins());
        Assert.assertEquals(card.getId(), m_cut.buyCard(ActionType.HOLZFAELLER).getId());
        Assert.assertEquals(0, myUser.getGamePoints().getCoins());
        Assert.assertEquals(1, myUser.getGamePoints().getBuyAmounts());
    }

    @Test
    public void checkBuyEstateCard() {
        Card card = new EstateCard(2, 1, EstateType.ANWESEN);
        User myUser = m_cut.getActiveUser();
        myUser.getGamePoints().modifyCoins(2);
        Assert.assertEquals(2, myUser.getGamePoints().getCoins());
        m_cut.buyCard(card);
        Assert.assertEquals(0, myUser.getGamePoints().getCoins());
        Assert.assertEquals(0, myUser.getGamePoints().getBuyAmounts());
        Assert.assertEquals(4, myUser.getGamePoints().getWinningPoints());
    }

    @Test
    public void checkBuyEstateCardWithType() {
        Card card = new EstateCard(2, 1, EstateType.ANWESEN);
        User myUser = m_cut.getActiveUser();
        myUser.getGamePoints().modifyCoins(2);
        Assert.assertEquals(2, myUser.getGamePoints().getCoins());
        Assert.assertEquals(card.getId(), m_cut.buyCard(EstateType.ANWESEN).getId());
        Assert.assertEquals(0, myUser.getGamePoints().getCoins());
        Assert.assertEquals(1, myUser.getGamePoints().getBuyAmounts());
        Assert.assertEquals(4, myUser.getGamePoints().getWinningPoints());
    }

    @Test
    public void checkHandCardsForActionCard() {
        Assert.assertEquals(false, m_cut.checkHandCards());
        Card card = new ActionCard(3, ActionType.HOLZFAELLER);
        addCardtoHand(card);
        Assert.assertEquals(true, m_cut.checkHandCards());
    }

    private void addCardtoHand(Card card) {
        LinkedList<Card> newHand = m_cut.getActiveUser().getUserCards().getHandCards();
        newHand.add(card);
    }
}
