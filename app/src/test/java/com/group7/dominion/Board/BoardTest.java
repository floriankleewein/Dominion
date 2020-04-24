package com.group7.dominion.Board;

import com.group7.dominion.Cards.ActionCard;
import com.group7.dominion.Cards.ActionType;
import com.group7.dominion.Cards.Card;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class BoardTest {
    private Board board;

    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void before() {
        board = new Board();
    }

    @AfterClass
    public static void afterClass() {

    }

    @After
    public void after() {

    }

    @Test
    public void testActionFieldInit() {
        List<Card> actionCardsToBuy =  board.getActionField().getActionCardsToBuy();

        Assert.assertEquals(100, actionCardsToBuy.size());
        assertActionCardType(actionCardsToBuy, ActionType.KELLER, 2);
        assertActionCardType(actionCardsToBuy, ActionType.BURGGRABEN, 2);
        assertActionCardType(actionCardsToBuy, ActionType.DORF, 3);
        assertActionCardType(actionCardsToBuy, ActionType.WERKSTATT, 3);
        assertActionCardType(actionCardsToBuy, ActionType.HOLZFAELLER, 3);
        assertActionCardType(actionCardsToBuy, ActionType.SCHMIEDE, 4);
        assertActionCardType(actionCardsToBuy, ActionType.HEXE, 4);
        assertActionCardType(actionCardsToBuy, ActionType.MILIZ, 4);
        assertActionCardType(actionCardsToBuy, ActionType.MARKT, 5);
        assertActionCardType(actionCardsToBuy, ActionType.MINE, 5);
    }

    @Test
    public void testActionFieldPickCard() {
        //board.getActionField()
    }


    public void assertActionCardType(List<Card> actionCardsToBuy, ActionType expectedActionType, int expectedPrice) {
        for(int i = 0; i < actionCardsToBuy.size(); i++) {
            Assert.assertTrue(actionCardsToBuy.get(i) instanceof ActionCard);
            if(((ActionCard) actionCardsToBuy.get(i)).getActionType() == expectedActionType) {
                Assert.assertEquals(expectedPrice, ((ActionCard) actionCardsToBuy.get(i)).getPrice());
            }
        }
    }
}
