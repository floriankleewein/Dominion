package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.board.Board;
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

    /**
     *LKDoc:    ActionCards Test - von jeder Karte werden jeweils 10 generiert
     *          wie viele sind in ArrayListe? 100 expected
     *          ist der Preis für diese Karte korrekt?
     */
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

    /**
     * LKDoc:
     *      1) CardIsFound: Karte wurde gefunden und ist nicht null
     *      2) CardNotFound: das Board wird zuerst geleert, dadurch wird auch keine Karte gefunden (=null)
     */
    @Test
    public void testActionFieldPickCardIsFound() {
        Card card = board.getActionField().pickCard(ActionType.KELLER);

        Assert.assertNotNull(card);
    }

    @Test
    public void testActionFieldPickCardNotFound() {
        board.getActionField().getActionCardsToBuy().removeAll(board.getActionField().getActionCardsToBuy());
        Card card = board.getActionField().pickCard(ActionType.KELLER);

        Assert.assertNull(card);
    }

    public void testBuyFieldInit() {
        List<Card> cardsToBuy =  board.getBuyField().getCardsToBuy();

        Assert.assertEquals(142, cardsToBuy.size());

        cardsToBuy.add(new MoneyCard(0, 1, MoneyType.KUPFER));
        assertMoneyCardType(cardsToBuy, MoneyType.GOLD, 6, 3);
        assertMoneyCardType(cardsToBuy, MoneyType.SILBER, 3, 2);
        assertMoneyCardType(cardsToBuy, MoneyType.KUPFER, 0, 1);
        assertEstateCardType(cardsToBuy, EstateType.PROVINZ, 8, 6);
        assertEstateCardType(cardsToBuy, EstateType.HERZOGTUM, 5, 3);
        assertEstateCardType(cardsToBuy, EstateType.ANWESEN, 2, 1);
        assertEstateCardType(cardsToBuy, EstateType.FLUCH, 0, -1);
    }

    @Test
    public void testBuyFieldMoneyTypePickCardIsFound() {
        Card card = board.getBuyField().pickCard(MoneyType.GOLD);

        Assert.assertNotNull(card);
    }

    @Test
    public void testBuyFieldMoneyTypePickCardNotFound() {
        board.getBuyField().getCardsToBuy().removeAll(board.getBuyField().getCardsToBuy());
        Card card = board.getBuyField().pickCard(MoneyType.GOLD);

        Assert.assertNull(card);
    }

    @Test
    public void testBuyFieldEstateTypePickCardIsFound() {
        Card card = board.getBuyField().pickCard(EstateType.PROVINZ);

        Assert.assertNotNull(card);
    }

    @Test
    public void testBuyFieldEstateTypePickCardNotFound() {
        board.getBuyField().getCardsToBuy().removeAll(board.getBuyField().getCardsToBuy());
        Card card = board.getBuyField().pickCard(EstateType.PROVINZ);

        Assert.assertNull(card);
    }

    /**
     * LKDoc:   hier wird durch die ganze Liste spezifischer Karten iteriert.
     *          Dann wird nochmal die Karte i geprüft und der Wert für diese Karte (z.B.price, worth, value
     * @param actionCardsToBuy
     * @param expectedActionType
     * @param expectedPrice
     */
    private void assertActionCardType(List<Card> actionCardsToBuy, ActionType expectedActionType, int expectedPrice) {
        for(int i = 0; i < actionCardsToBuy.size(); i++) {
            Assert.assertTrue(actionCardsToBuy.get(i) instanceof ActionCard);
            if(((ActionCard) actionCardsToBuy.get(i)).getActionType() == expectedActionType) {
                Assert.assertEquals(expectedPrice, ((ActionCard) actionCardsToBuy.get(i)).getPrice());
            }
        }
    }


    private void assertMoneyCardType(List<Card> moneyCardsToBuy, MoneyType expectedMoneyType, int expectedPrice, int expectedWorth) {
        for(int i = 0; i < moneyCardsToBuy.size(); i++) {
            if(((MoneyCard) moneyCardsToBuy.get(i)).getMoneyType() == expectedMoneyType) {
                Assert.assertEquals(expectedPrice, ((MoneyCard) moneyCardsToBuy.get(i)).getPrice());
                Assert.assertEquals(expectedWorth, ((MoneyCard) moneyCardsToBuy.get(i)).getWorth());
            }
        }
    }

    private void assertEstateCardType(List<Card> estateCardsToBuy, EstateType expectedEstateType, int expectedPrice, int expectedEstateValue) {
        for(int i = 0; i < estateCardsToBuy.size(); i++) {
            if(((EstateCard) estateCardsToBuy.get(i)).getEstateType() == expectedEstateType) {
                Assert.assertEquals(expectedPrice, ((EstateCard) estateCardsToBuy.get(i)).getPrice());
                Assert.assertEquals(expectedEstateValue, ((EstateCard) estateCardsToBuy.get(i)).getEstateValue());
            }
        }
    }
}

