package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.cards.ActionCard;
import com.floriankleewein.commonclasses.cards.ActionType;
import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.cards.MoneyCard;
import com.floriankleewein.commonclasses.cards.MoneyType;
import com.floriankleewein.commonclasses.gamelogic.CardLogic;
import com.floriankleewein.commonclasses.gamelogic.GameHandler;
import com.floriankleewein.commonclasses.user.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;


public class CardLogicTest {

    private CardLogic m_cut;
    private GameHandler gh;

    @Before
    public void setup() {
        init();
        m_cut = new CardLogic(gh);
    }

    @Test
    public void checkVariablesMine() {
        Card card = new ActionCard(5, ActionType.MINE);
        m_cut.setVariables(card);
        Assert.assertEquals(true, m_cut.isReplaceMoneyCard());
    }

    @Test
    public void checkEffectsMineNoMoney() {
        Card card1 = new ActionCard(5, ActionType.MINE);
        Card card2 = new ActionCard(5, ActionType.MINE);
        Card card3 = new ActionCard(5, ActionType.MINE);
        initCardsForMine(); // No cards in hand
        addCardtoHand(card1);
        addCardtoHand(card2);
        addCardtoHand(card3);
        m_cut.doCardLogic(card1);
        Assert.assertEquals(2, gh.getActiveUser().getUserCards().getHandCards().size());
    }

    @Test
    public void checkEffectsMineWithCopper() {
        Card card1 = new ActionCard(5, ActionType.MINE);
        Card card2 = new ActionCard(5, ActionType.MINE);
        Card card3 = new MoneyCard(0, 1, MoneyType.KUPFER);
        initCardsForMine(); // No cards in hand
        addCardtoHand(card1);
        addCardtoHand(card2);
        addCardtoHand(card3);
        m_cut.doCardLogic(card1);
        Assert.assertEquals(1, gh.getActiveUser().getUserCards().getHandCards().stream().filter(card -> card.getId() == 15).count());
    }

    @Test
    public void checkEffectsMineWithSilver() {
        Card card1 = new ActionCard(5, ActionType.MINE);
        Card card2 = new ActionCard(5, ActionType.MINE);
        Card card3 = new MoneyCard(3, 2, MoneyType.SILBER);
        initCardsForMine(); // No cards in hand
        addCardtoHand(card1);
        addCardtoHand(card2);
        addCardtoHand(card3);
        m_cut.doCardLogic(card1);
        Assert.assertEquals(1, gh.getActiveUser().getUserCards().getHandCards().stream().filter(card -> card.getId() == 16).count());
    }

    @Test
    public void checkEffectsMineWithGold() {
        Card card1 = new ActionCard(5, ActionType.MINE);
        Card card2 = new ActionCard(5, ActionType.MINE);
        Card card3 = new MoneyCard(6, 3, MoneyType.GOLD);
        initCardsForMine(); // No cards in hand
        addCardtoHand(card1);
        addCardtoHand(card2);
        addCardtoHand(card3);
        m_cut.doCardLogic(card1);
        Assert.assertEquals(1, gh.getActiveUser().getUserCards().getHandCards().stream().filter(card -> card.getId() == 16).count());
    }

    @Test
    public void checkVariablesWitch() {
        Card card = new ActionCard(5, ActionType.HEXE);
        m_cut.setVariables(card);
        Assert.assertEquals(1, m_cut.getCurseCount());
        Assert.assertEquals(1, m_cut.getDrawableCards());
    }

    @Test
    public void checkEffectOfWitch() {
        Card card = new ActionCard(5, ActionType.HEXE);
        for (User u : gh.getGame().getPlayerList()) {
            Assert.assertEquals(3, u.getGamePoints().getWinningPoints());
        }
        m_cut.doCardLogic(card);

        for (User u : gh.getGame().getPlayerList()) {
            if (u.equals(gh.getActiveUser())) continue;
            Assert.assertEquals(2, u.getGamePoints().getWinningPoints());
        }
    }

    @Test
    public void checkVariablesMilitia() {
        Card card = new ActionCard(5, ActionType.MILIZ);
        m_cut.setVariables(card);
        Assert.assertEquals(2, m_cut.getMoneyValue());
        Assert.assertEquals(true, m_cut.isDiscardCardsOfOtherPlayers());
    }

    @Test
    public void checkEffectMilitia() {
        Card card = new ActionCard(5, ActionType.MILIZ);
        for (User u : gh.getGame().getPlayerList()) {
            Assert.assertEquals(5, u.getUserCards().getHandCards().size());
        }
        m_cut.doCardLogic(card);

        for (User u : gh.getGame().getPlayerList()) {
            if (u.equals(gh.getActiveUser())) continue;
            Assert.assertEquals(3, u.getUserCards().getHandCards().size());
        }
    }

    @Test
    public void checkVariablesCellar() {
        Card card = new ActionCard(2, ActionType.KELLER);
        m_cut.setVariables(card);
        Assert.assertEquals(1, m_cut.getAddActionPts());
    }

    @Test
    public void checkEffectsCellar() {
        Card card = new ActionCard(2, ActionType.KELLER);
        m_cut.setVariables(card);
        Assert.assertEquals(1, gh.getActiveUser().getGamePoints().getPlaysAmount());
    }

    @Test
    public void checkVariablesSmithy() {
        Card card = new ActionCard(4, ActionType.SCHMIEDE);
        m_cut.setVariables(card);
        Assert.assertEquals(3, m_cut.getDrawableCards());
    }

    @Test
    public void checkEffectsSmithy() {
        Card card = new ActionCard(4, ActionType.SCHMIEDE);
        addCardtoHand(card);
        Assert.assertEquals(6, gh.getActiveUser().getUserCards().getHandCards().size());
        m_cut.doCardLogic(card);
        Assert.assertEquals(8, gh.getActiveUser().getUserCards().getHandCards().size());
    }

    @Test
    public void checkVariablesMoat() {
        Card card = new ActionCard(2, ActionType.BURGGRABEN);
        addCardtoHand(card);
        m_cut.setVariables(card);
        Assert.assertEquals(2, m_cut.getDrawableCards());
        Assert.assertEquals(true, gh.getActiveUser().getUserCards().hasMoat());
    }

    @Test
    public void checkEffectsMoat() {
        Card card = new ActionCard(4, ActionType.BURGGRABEN);
        addCardtoHand(card);
        Assert.assertEquals(6, gh.getActiveUser().getUserCards().getHandCards().size());
        m_cut.doCardLogic(card);
        Assert.assertEquals(7, gh.getActiveUser().getUserCards().getHandCards().size());
        Assert.assertEquals(false, gh.getActiveUser().getUserCards().hasMoat());
    }

    @Test
    public void checkVariablesVillage() {
        Card card = new ActionCard(4, ActionType.DORF);
        m_cut.setVariables(card);
        Assert.assertEquals(1, m_cut.getDrawableCards());
        Assert.assertEquals(2, m_cut.getAddActionPts());
    }

    @Test
    public void checkEffectsVillage() {
        Card card = new ActionCard(4, ActionType.DORF);
        addCardtoHand(card);
        Assert.assertEquals(6, gh.getActiveUser().getUserCards().getHandCards().size());
        m_cut.doCardLogic(card);
        Assert.assertEquals(6, gh.getActiveUser().getUserCards().getHandCards().size());
        Assert.assertEquals(3, gh.getActiveUser().getGamePoints().getPlaysAmount());
    }

    @Test
    public void checkVariablesWoodcutter() {
        Card card = new ActionCard(3, ActionType.HOLZFAELLER);
        m_cut.setVariables(card);
        Assert.assertEquals(1, m_cut.getAddBuyPts());
        Assert.assertEquals(2, m_cut.getMoneyValue());
    }

    @Test
    public void checkEffectsWoodcutter() {
        Card card = new ActionCard(3, ActionType.HOLZFAELLER);
        addCardtoHand(card);
        Assert.assertEquals(1, gh.getActiveUser().getGamePoints().getBuyAmounts());
        m_cut.doCardLogic(card);
        Assert.assertEquals(5, gh.getActiveUser().getUserCards().getHandCards().size());
        Assert.assertEquals(2, gh.getActiveUser().getGamePoints().getCoins());
        Assert.assertEquals(2, gh.getActiveUser().getGamePoints().getBuyAmounts());
    }

    @Test
    public void checkVariablesWorkshop() {
        Card card = new ActionCard(3, ActionType.WERKSTATT);
        m_cut.setVariables(card);
        Assert.assertEquals(4, m_cut.getMoneyValue());
        Assert.assertEquals(1, m_cut.getAddBuyPts());
    }

    @Test
    public void checkEffectsWorkshop() {
        Card card = new ActionCard(3, ActionType.WERKSTATT);
        addCardtoHand(card);
        Assert.assertEquals(0, gh.getActiveUser().getGamePoints().getCoins());
        Assert.assertEquals(1, gh.getGame().getActivePlayer().getGamePoints().getBuyAmounts());
        m_cut.doCardLogic(card);
        Assert.assertEquals(4, gh.getActiveUser().getGamePoints().getCoins());
        Assert.assertEquals(2, gh.getGame().getActivePlayer().getGamePoints().getBuyAmounts());
        Assert.assertEquals(5, gh.getActiveUser().getUserCards().getHandCards().size());
    }


    @Test
    public void checkVariablesMarket() {
        Card card = new ActionCard(5, ActionType.MARKT);
        m_cut.setVariables(card);
        Assert.assertEquals(1, m_cut.getMoneyValue());
        Assert.assertEquals(1, m_cut.getAddBuyPts());
        Assert.assertEquals(1, m_cut.getAddActionPts());
        Assert.assertEquals(1, m_cut.getDrawableCards());
    }

    @Test
    public void checkEffectsMarket() {
        Card card = new ActionCard(5, ActionType.MARKT);
        addCardtoHand(card);
        Assert.assertEquals(0, gh.getActiveUser().getGamePoints().getCoins());
        Assert.assertEquals(6, gh.getActiveUser().getUserCards().getHandCards().size());
        Assert.assertEquals(1, gh.getActiveUser().getGamePoints().getBuyAmounts());
        Assert.assertEquals(1, gh.getActiveUser().getGamePoints().getPlaysAmount());

        m_cut.doCardLogic(card);

        Assert.assertEquals(1, gh.getActiveUser().getGamePoints().getCoins());
        Assert.assertEquals(6, gh.getActiveUser().getUserCards().getHandCards().size());
        Assert.assertEquals(2, gh.getActiveUser().getGamePoints().getBuyAmounts());
        Assert.assertEquals(2, gh.getActiveUser().getGamePoints().getPlaysAmount());
    }

    @Test
    public void checkVariablesCopper() {
        Card card = new MoneyCard(0, 1, MoneyType.KUPFER);
        m_cut.setVariables(card);
        Assert.assertEquals(1, m_cut.getMoneyValue());
    }

    @Test
    public void checkVariablesSilver() {
        Card card = new MoneyCard(3, 2, MoneyType.GOLD);
        m_cut.setVariables(card);
        Assert.assertEquals(2, m_cut.getMoneyValue());
    }

    @Test
    public void checkVariablesGold() {
        Card card = new MoneyCard(6, 3, MoneyType.KUPFER);
        m_cut.setVariables(card);
        Assert.assertEquals(3, m_cut.getMoneyValue());
    }

    @Test
    public void checkEffectsCopper() {
        Card card = new MoneyCard(0, 1, MoneyType.KUPFER);
        addCardtoHand(card);
        Assert.assertEquals(0, gh.getActiveUser().getGamePoints().getCoins());
        m_cut.doCardLogic(card);
        Assert.assertEquals(1, gh.getActiveUser().getGamePoints().getCoins());
        Assert.assertEquals(5, gh.getActiveUser().getUserCards().getHandCards().size());
    }

    @Test
    public void checkEffectsSilver() {
        Card card = new MoneyCard(3, 2, MoneyType.SILBER);
        addCardtoHand(card);
        Assert.assertEquals(0, gh.getActiveUser().getGamePoints().getCoins());
        m_cut.doCardLogic(card);
        Assert.assertEquals(2, gh.getActiveUser().getGamePoints().getCoins());
        Assert.assertEquals(5, gh.getActiveUser().getUserCards().getHandCards().size());
    }

    @Test
    public void checkEffectsGold() {
        Card card = new MoneyCard(6, 3, MoneyType.KUPFER);
        addCardtoHand(card);
        Assert.assertEquals(0, gh.getActiveUser().getGamePoints().getCoins());
        m_cut.doCardLogic(card);
        Assert.assertEquals(3, gh.getActiveUser().getGamePoints().getCoins());
        Assert.assertEquals(5, gh.getActiveUser().getUserCards().getHandCards().size());
    }

    @After
    public void teardown() {
        m_cut = null;
        gh = null;
        Game.setGame(null);
    }

    private void init() {
        gh = new GameHandler(Game.getGame());
        Game.getGame().addPlayer(new User("Flo"));
        Game.getGame().addPlayer(new User("Laura"));
        Game.getGame().addPlayer(new User("Florian"));
        Game.getGame().addPlayer(new User("TomTurbo"));
        gh.prepareGame();
    }

    private void addCardtoHand(Card card) {
        LinkedList<Card> newHand = gh.getActiveUser().getUserCards().getHandCards();
        newHand.add(card);
    }

    private void initCardsForMine() {
        LinkedList<Card> newHand = new LinkedList<>();
        gh.getActiveUser().getUserCards().setHandCards(newHand);
    }
}
