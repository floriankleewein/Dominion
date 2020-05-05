package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.CheatFunction.CheatService;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.User.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.LinkedList;


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


    private LinkedList<Object> fillwithTestCards(int amountCards) {
        LinkedList<Object> TestList = new LinkedList<>();
        for (int i = 0; i < amountCards; i++) {
            TestList.add("Card: " + i);
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