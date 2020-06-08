package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.cards.ActionCard;
import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.cards.EstateCard;
import com.floriankleewein.commonclasses.cards.EstateType;
import com.floriankleewein.commonclasses.cards.MoneyCard;
import com.floriankleewein.commonclasses.cards.MoneyType;
import com.floriankleewein.commonclasses.user.GamePoints;
import com.floriankleewein.commonclasses.user.User;
import com.floriankleewein.commonclasses.user.UserCards;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static com.floriankleewein.commonclasses.cards.ActionType.BURGGRABEN;
import static com.floriankleewein.commonclasses.cards.ActionType.HEXE;

public class UserClassTest {
    static String UserName = "TestUser";
    private User user;
    private LinkedList<Card> TestObjects;

    @Before
    public void setUp() {
        this.user = new User(UserName);
        this.user.setUpforGame();
        TestObjects = new LinkedList<>();
        TestObjects = fillwithTestCards(10);
    }

    private LinkedList<Card> fillwithTestCards(int amountCards) {
        Card one = new ActionCard(2, HEXE);
        Card two = new EstateCard(3, 3, EstateType.PROVINZ);
        Card three = new MoneyCard(2, 3, MoneyType.GOLD);

        LinkedList<Card> TestList = new LinkedList<>();
        for (int i = 0; i < amountCards; i++) {
            if (i > 3) {
                TestList.add(one);
            } else if (i < 3 && i > 6) {
                TestList.add(two);
            } else TestList.add(three);

        }
        return TestList;
    }

    /**
     * @Author Maurer Florian
     * Tests compare the size of the List, with an excpected value.
     * REASON : You cant compare the Objects because you dont know where which card is (Shuffle Function)
     */


    @Test
    public void testgetFirstCards() {
        this.user.getUserCards().getFirstCards(TestObjects);
        Assert.assertEquals(5, user.getUserCards().getHandCards().size());
        Assert.assertEquals(5, user.getUserCards().getDeck().size());
        Assert.assertEquals(0, user.getUserCards().getDiscardCards().size());
    }

    @Test
    public void testPlayCard() {
        this.user.getUserCards().getFirstCards(TestObjects);
        Card playedCard = this.user.getUserCards().getHandCards().get(0);
        Assert.assertEquals(5, this.user.getUserCards().getHandCards().size());
        Assert.assertEquals(0, this.user.getUserCards().getDiscardCards().size());

        /** * Card will be played*/
        this.user.getUserCards().playCard(playedCard);

        Assert.assertEquals(4, this.user.getUserCards().getHandCards().size());
        Assert.assertEquals(1, this.user.getUserCards().getDiscardCards().size());

    }

    @Test
    public void testAddCardToDeck() {
        this.user.getUserCards().getFirstCards(TestObjects);

        Assert.assertEquals(5, this.user.getUserCards().getDeck().size());
        Assert.assertEquals(5, this.user.getUserCards().getHandCards().size());
        Assert.assertEquals(0, this.user.getUserCards().getDiscardCards().size());

        Card newCard = new ActionCard(1, BURGGRABEN);
        //Draw new Card
        this.user.getUserCards().addCardtoDeck(newCard);
        Assert.assertEquals(6, this.user.getUserCards().getDeck().size());
        Assert.assertEquals(5, this.user.getUserCards().getHandCards().size());
        Assert.assertEquals(0, this.user.getUserCards().getDiscardCards().size());

    }

    @Test
    public void testputCardsintheDeck() {
        this.user.getUserCards().getFirstCards(TestObjects);

        Assert.assertEquals(5, this.user.getUserCards().getDeck().size());
        Assert.assertEquals(5, this.user.getUserCards().getHandCards().size());
        Assert.assertEquals(0, this.user.getUserCards().getDiscardCards().size());

        LinkedList<Card> newCards = fillwithTestCards(5);
        this.user.getUserCards().putCardsinTheDeck(newCards);
        Assert.assertEquals(10, this.user.getUserCards().getDeck().size());

    }

    @Test

    public void testLessThanFiveDeckCards() {
        this.user.getUserCards().setDeck(fillwithTestCards(4));
        this.user.getUserCards().setDiscardCards(fillwithTestCards(6));
        this.user.getUserCards().setHandCards(fillwithTestCards(2));

        this.user.getUserCards().drawNewCards();

        Assert.assertEquals(7, this.user.getUserCards().getDeck().size());
        Assert.assertEquals(5, this.user.getUserCards().getHandCards().size());
        Assert.assertEquals(0, this.user.getUserCards().getDiscardCards().size());

    }

    @Test
    public void addDeckCardtoHandCard() {
        this.user.getUserCards().setDeck(fillwithTestCards(4));
        this.user.getUserCards().setDiscardCards(fillwithTestCards(6));
        this.user.getUserCards().setHandCards(fillwithTestCards(2));

        this.user.getUserCards().addDeckCardtoHandCard(3);

        Assert.assertEquals(7, this.user.getUserCards().getDeck().size());
        Assert.assertEquals(5, this.user.getUserCards().getHandCards().size());
        Assert.assertEquals(0, this.user.getUserCards().getDiscardCards().size());
    }

    //Test are only for Coverage
    @Test
    public void testGetterAndSetter() {
        User user = new User();
        UserCards TestCards = new UserCards();
        GamePoints TestPoints = new GamePoints();

        user.setUserName("TestUser");
        user.setCheater(true);
        user.setGameCreator(false);
        user.setGamePoints(TestPoints);
        user.setUserCards(TestCards);


        Assert.assertEquals("TestUser", user.getUserName());
        Assert.assertEquals(true, user.isCheater());
        Assert.assertEquals(false, user.isGameCreator());
        Assert.assertEquals(TestCards, user.getUserCards());
        Assert.assertEquals(TestPoints, user.getGamePoints());
    }

    @After
    public void setNull() {
        this.user = null;
        this.TestObjects = null;
    }


}
