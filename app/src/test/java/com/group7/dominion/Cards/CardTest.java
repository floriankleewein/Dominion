package com.group7.dominion.Cards;

import android.view.ViewGroup;
import android.widget.ImageButton;

import com.group7.dominion.CardActivity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
public class CardTest {
    private CardActivity cardActivity;

    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void before() {
        cardActivity = mock(CardActivity.class);
    }

    @AfterClass
    public static void afterClass() {

    }

    @After
    public void after() {

    }

    @Test
    public void testActionCardCalculationAction() {
        /* TODO: Cannot be tested because there needs to be a mock for method setImageResource in ActionField class
        ActionCard actionCard = new ActionCard(cardActivity, 1, ActionType.BURGGRABEN);
        actionCard.init();
        assertActionIntegers(actionCard, 2, 0, 0, 0, 0, 0);
        assertActionBooleans(actionCard, false, false, false, false);
        */

        ActionCard actionCard = new ActionCard(cardActivity, 1, ActionType.DORF);
        actionCard.init();
        assertActionIntegers(actionCard, 1, 2, 0, 0, 0, 0);
        assertActionBooleans(actionCard, false, false, false, false);

        actionCard = new ActionCard(cardActivity, 1, ActionType.HOLZFAELLER);
        actionCard.init();
        assertActionIntegers(actionCard, 0, 0, 1, 0, 0, 1);
        assertActionBooleans(actionCard, false, false, false, false);

        actionCard = new ActionCard(cardActivity, 1, ActionType.KELLER);
        actionCard.init();
        assertActionIntegers(actionCard, 0, 1, 0, 0, 0, 0);
        assertActionBooleans(actionCard, false, false, true, false);

        actionCard = new ActionCard(cardActivity, 1, ActionType.WERKSTATT);
        actionCard.init();
        assertActionIntegers(actionCard, 1, 0, 0, 0, 4, 0);
        assertActionBooleans(actionCard, false, false, false, false);

        actionCard = new ActionCard(cardActivity, 1, ActionType.SCHMIEDE);
        actionCard.init();
        assertActionIntegers(actionCard, 3, 0, 0, 0, 0, 0);
        assertActionBooleans(actionCard, false, false, false, false);

        actionCard = new ActionCard(cardActivity, 1, ActionType.MARKT);
        actionCard.init();
        assertActionIntegers(actionCard, 1, 1, 1, 0, 0, 1);
        assertActionBooleans(actionCard, false, false, false, false);

        actionCard = new ActionCard(cardActivity, 1, ActionType.HEXE);
        actionCard.init();
        assertActionIntegers(actionCard, 1, 0, 0, 1, 0, 0);
        assertActionBooleans(actionCard, false, false, false, false);

        actionCard = new ActionCard(cardActivity, 1, ActionType.MINE);
        actionCard.init();
        assertActionIntegers(actionCard, -1, 0, 0, 0, 0, 0);
        assertActionBooleans(actionCard, true, true, false, false);

        actionCard = new ActionCard(cardActivity, 1, ActionType.MILIZ);
        actionCard.init();
        assertActionIntegers(actionCard, 0, 0, 0, 0, 0, 2);
        assertActionBooleans(actionCard, false, false, false, true);
    }

    @Test
    public void testCard() {
        Card card = new Card(cardActivity, 1);

        Assert.assertEquals(1, card.getPrice());

        card.setPrice(2);

        Assert.assertEquals(2, card.getPrice());
    }

    @Test
    public void testActionCard() {
        ActionCard card = new ActionCard(cardActivity, 1, ActionType.KELLER);

        Assert.assertEquals(ActionType.KELLER, card.getActionType());

        card.setActionType(ActionType.BURGGRABEN);

        Assert.assertEquals(ActionType.BURGGRABEN, card.getActionType());
    }

    @Test
    public void testEstateCard() {
        EstateCard card = new EstateCard(cardActivity, 1, 1, EstateType.PROVINZ);

        Assert.assertEquals(1, card.getEstateValue());
        Assert.assertEquals(EstateType.PROVINZ, card.getEstateType());

        card.setEstateValue(2);
        card.setEstateType(EstateType.ANWESEN);

        Assert.assertEquals(2, card.getEstateValue());
        Assert.assertEquals(EstateType.ANWESEN, card.getEstateType());
    }

    @Test
    public void testMoneyCard() {
        MoneyCard card = new MoneyCard(cardActivity, 1, 1, MoneyType.GOLD);

        Assert.assertEquals(1, card.getWorth());
        Assert.assertEquals(MoneyType.GOLD, card.getMoneyType());

        card.setWorth(2);
        card.setMoneyType(MoneyType.KUPFER);

        Assert.assertEquals(2, card.getWorth());
        Assert.assertEquals(MoneyType.KUPFER, card.getMoneyType());
    }

    private void assertActionIntegers(ActionCard actionCard,
                                      int expectedCardCount,
                                      int expectedActionCount,
                                      int expectedBuyCount,
                                      int expectedCurseCount,
                                      int expectedMaxMoneyValue,
                                      int expectedMoneyValue) {
        Assert.assertEquals(expectedCardCount, actionCard.getAction().getCardCount());
        Assert.assertEquals(expectedActionCount, actionCard.getAction().getActionCount());
        Assert.assertEquals(expectedBuyCount, actionCard.getAction().getBuyCount());
        Assert.assertEquals(expectedCurseCount, actionCard.getAction().getCurseCount());
        Assert.assertEquals(expectedMaxMoneyValue, actionCard.getAction().getMaxMoneyValue());
        Assert.assertEquals(expectedMoneyValue, actionCard.getAction().getMoneyValue());

    }

    private void assertActionBooleans(ActionCard actionCard,
                                      boolean expectedTakeCardOnHand,
                                      boolean expectedTakeMoneyCardThatCostThreeMoreThanOld,
                                      boolean expectedThrowAnyAmountCards,
                                      boolean expectedThrowEveryUserCardsUntilThreeLeft) {
        Assert.assertEquals(expectedTakeCardOnHand, actionCard.getAction().isTakeCardOnHand());
        Assert.assertEquals(expectedTakeMoneyCardThatCostThreeMoreThanOld, actionCard.getAction().isTakeMoneyCardThatCostThreeMoreThanOld());
        Assert.assertEquals(expectedThrowAnyAmountCards, actionCard.getAction().isThrowAnyAmountCards());
        Assert.assertEquals(expectedThrowEveryUserCardsUntilThreeLeft, actionCard.getAction().isThrowEveryUserCardsUntilThreeLeft());
    }
}
