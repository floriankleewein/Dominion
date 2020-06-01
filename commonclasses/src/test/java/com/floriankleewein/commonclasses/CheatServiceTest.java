package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.cards.ActionCard;
import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.cards.EstateCard;
import com.floriankleewein.commonclasses.cards.EstateType;
import com.floriankleewein.commonclasses.cards.MoneyCard;
import com.floriankleewein.commonclasses.cards.MoneyType;
import com.floriankleewein.commonclasses.cheatfunction.CheatService;
import com.floriankleewein.commonclasses.user.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.LinkedList;

import static com.floriankleewein.commonclasses.cards.ActionType.HEXE;


public class CheatServiceTest {

    CheatService cheatService;
    ArrayList<User> Playerlist;


    @Before
    public void setup() {
        /**
         * Objects like game or user should be mocked!!!!
         */

        Game game = new Game();
        Playerlist = new <User>ArrayList();
        Playerlist.add(new User("User1"));
        Playerlist.add(new User("User2"));
        Playerlist.add(new User("User3"));
        Playerlist.add(new User("User4"));
        addCardstoUser();
        game.setPlayerList(Playerlist);
        this.cheatService = new CheatService(game);

    }

    @Test
    public void testaddCardtoUser() {
        cheatService.addCardtoUser("User1");
        cheatService.addCardtoUser("User3");

        Assert.assertEquals(true, Playerlist.get(0).isCheater());
        Assert.assertEquals(false, Playerlist.get(1).isCheater());
        Assert.assertEquals(true, Playerlist.get(2).isCheater());
        Assert.assertEquals(false, Playerlist.get(3).isCheater());


        Assert.assertEquals(4, Playerlist.get(0).getUserCards().getDeck().size());
        Assert.assertEquals(6, Playerlist.get(0).getUserCards().getHandCards().size());

    }

    @Test
    public void testSuspectUser() {

        cheatService.addCardtoUser("User1");
        cheatService.suspectUser("User1", "User2");
        cheatService.suspectUser("User2", "User3");
        Assert.assertEquals(-5, cheatService.findUser("User1").getGamePoints().getWinningPoints());
        Assert.assertEquals(0, cheatService.findUser("User2").getGamePoints().getWinningPoints());
        Assert.assertEquals(-5, cheatService.findUser("User3").getGamePoints().getWinningPoints());
    }

    @After
    public void setNull() {
        this.cheatService = null;
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

    private void addCardstoUser() {
        for (int i = 0; i < Playerlist.size(); i++) {
            Playerlist.get(i).setUpforGame();
            Playerlist.get(i).getUserCards().getFirstCards(fillwithTestCards(10));
        }
    }
}