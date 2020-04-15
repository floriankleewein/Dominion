package UserTests;

import com.group7.dominion.User.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

public class UserClassTest {
    static String UserName = "TestUser";
    static String UserEmail = "test@email.com";
    static String UserPassword = "TestPassword";

    private User user;
    private LinkedList<Object> TestObjects;

    @Before
    public void setUp() {
        this.user = new User(UserName, UserEmail, UserPassword);
        this.user.setUpforGame();
        TestObjects = new LinkedList<>();
        TestObjects = fillwithTestCards(10);
    }

    private LinkedList<Object> fillwithTestCards(int amountCards) {
        LinkedList<Object> TestList = new LinkedList<>();
        for (int i = 0; i < amountCards; i++) {
            TestList.add("Card: " + i);
        }
        return TestList;
    }

    /**
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
        Object playedCard = this.user.getUserCards().getHandCards().get(0);
        Assert.assertEquals(5, this.user.getUserCards().getHandCards().size());
        Assert.assertEquals(0, this.user.getUserCards().getDiscardCards().size());

        //Card will be played

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

        String newCard = "NEW CARD";
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

        LinkedList<Object> newCards = fillwithTestCards(5);
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

    @After
    public void setNull() {
        this.user = null;
        this.TestObjects = null;
    }


}
