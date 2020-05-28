package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.Cards.ActionType;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.GameLogic.CardLogic;
import com.floriankleewein.commonclasses.GameLogic.GameHandler;
import com.floriankleewein.commonclasses.User.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
            if(u.equals(gh.getActiveUser())) continue;
            Assert.assertEquals(2, u.getGamePoints().getWinningPoints());
        }
    }

    @Test
    public void checkVariablesMilitia() {
        Card card = new ActionCard(5, ActionType.HEXE);
        m_cut.setVariables(card);
        Assert.assertEquals(1, m_cut.getCurseCount());
        Assert.assertEquals(1, m_cut.getDrawableCards());
    }

    @Test
    public void checkEffectMilitia() {
        Card card = new ActionCard(5, ActionType.HEXE);
        m_cut.setVariables(card);
        Assert.assertEquals(1, m_cut.getCurseCount());
        Assert.assertEquals(1, m_cut.getDrawableCards());
    }

    private void init() {
        gh = new GameHandler(Game.getGame());
        Game.getGame().addPlayer(new User("Flo"));
        Game.getGame().addPlayer(new User("Laura"));
        Game.getGame().addPlayer(new User("Florian"));
        Game.getGame().addPlayer(new User("TomTurbo"));
        gh.prepareGame();
    }
}
