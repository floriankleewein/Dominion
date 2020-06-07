package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.cards.ActionCard;
import com.floriankleewein.commonclasses.cards.ActionType;
import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.cards.EstateCard;
import com.floriankleewein.commonclasses.cards.EstateType;
import com.floriankleewein.commonclasses.cards.MoneyCard;
import com.floriankleewein.commonclasses.cards.MoneyType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CardTest {
    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void before() {

    }

    @AfterClass
    public static void afterClass() {

    }

    @After
    public void after() {

    }

    /**
     * LKDoc:   ActionCard testen
     *          ActionCard: Integers/Booleans rausholen + vergleichen
     *          ob es mit den expected Werten zusammenpasst
     */
    @Test
    public void testActionCardCalculationAction() {
        ActionCard actionCard = new ActionCard(1, ActionType.BURGGRABEN);
        assertActionIntegers(actionCard, 2, 0, 0, 0,0,0);
        assertActionBooleans(actionCard, false, false, false, false);

        actionCard = new ActionCard(1, ActionType.DORF);
        assertActionIntegers(actionCard, 1, 2, 0, 0,0,0);
        assertActionBooleans(actionCard, false, false, false, false);

        actionCard = new ActionCard(1, ActionType.HOLZFAELLER);
        assertActionIntegers(actionCard, 0, 0, 1, 0,0,2);
        assertActionBooleans(actionCard, false, false, false, false);

        actionCard = new ActionCard(1, ActionType.KELLER);
        assertActionIntegers(actionCard, 0, 1, 0, 0,0,0);
        assertActionBooleans(actionCard, false, false, true, false);

        actionCard = new ActionCard(1, ActionType.WERKSTATT);
        assertActionIntegers(actionCard, 0, 0, 1, 0,0,4);
        assertActionBooleans(actionCard, false, false, false, false);

        actionCard = new ActionCard(1, ActionType.SCHMIEDE);
        assertActionIntegers(actionCard, 3, 0, 0, 0,0,0);
        assertActionBooleans(actionCard, false, false, false, false);

        actionCard = new ActionCard(1, ActionType.MARKT);
        assertActionIntegers(actionCard, 1, 1, 1, 0,0,1);
        assertActionBooleans(actionCard, false, false, false, false);

        actionCard = new ActionCard(1, ActionType.HEXE);
        assertActionIntegers(actionCard, 1, 0, 0, 1,0,0);
        assertActionBooleans(actionCard, false, false, false, false);

        actionCard = new ActionCard(1, ActionType.MINE);
        assertActionIntegers(actionCard, -1, 0, 0, 0,0,0);
        assertActionBooleans(actionCard, true, true, false, false);

        actionCard = new ActionCard(1, ActionType.MILIZ);
        assertActionIntegers(actionCard, 0, 0, 0, 0,0,2);
        assertActionBooleans(actionCard, false, false, false, true);
    }

    /**
     * LKDoc:   Testet die Card allgemein
     *          Preis wird gesetzt - richtig? Preis wird erneut gesetzt
     */
    @Test
    public void testCard() {
        Card card = new Card(1);
        Assert.assertEquals(1, card.getPrice());
        card.setPrice(2);
        Assert.assertEquals(2, card.getPrice());
    }


    /**
     * LKDoc:   Diese Tests testen den Typ ActionType,  EstateType, Moneytype
     *          Typ setzen, value & worth auch testen falls benötigt
     */
    @Test
    public void testActionCard() {
        Card card = new ActionCard(1, ActionType.KELLER);
        Assert.assertEquals(ActionType.KELLER, ((ActionCard) card).getActionType());
        ((ActionCard) card).setActionType(ActionType.BURGGRABEN);
        Assert.assertEquals(ActionType.BURGGRABEN, ((ActionCard) card).getActionType());
    }

    @Test
    public void testEstateCard() {
        Card card = new EstateCard(1,1, EstateType.PROVINZ);
        Assert.assertEquals(1, ((EstateCard) card).getEstateValue());
        Assert.assertEquals(EstateType.PROVINZ, ((EstateCard) card).getEstateType());
        ((EstateCard) card).setEstateValue(2);
        ((EstateCard) card).setEstateType(EstateType.ANWESEN);
        Assert.assertEquals(2, ((EstateCard) card).getEstateValue());
        Assert.assertEquals(EstateType.ANWESEN, ((EstateCard) card).getEstateType());
    }

    @Test
    public void testMoneyCard() {
        Card card = new MoneyCard(1,1, MoneyType.GOLD);
        Assert.assertEquals(1, ((MoneyCard) card).getWorth());
        Assert.assertEquals(MoneyType.GOLD, ((MoneyCard) card).getMoneyType());
        ((MoneyCard) card).setWorth(2);
        ((MoneyCard) card).setMoneyType(MoneyType.KUPFER);
        Assert.assertEquals(2, ((MoneyCard) card).getWorth());
        Assert.assertEquals(MoneyType.KUPFER, ((MoneyCard) card).getMoneyType());
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

