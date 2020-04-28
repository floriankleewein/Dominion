package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.User.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


public class GameTest {

    private Game game;
    private User user1;

    @Before
    public void setupGame() {
        game = Game.getGame();
        user1 = new User("testName1");
    }

    @Test
    public void testSingletonWithNull() {
        Assert.assertEquals(game, Game.getGame());
    }

    @Test
    public void testCheckName1(){
        Assert.assertEquals(true, game.checkName(user1.getUserName()));
    }

    @Test
    public void testCheckName2(){
        game.addPlayer(user1);
        Assert.assertEquals(false, game.checkName(user1.getUserName()));
    }

    @Test
    public void testCheckSize1(){
        Assert.assertEquals(true, game.checkSize());
    }

    @Test
    public void testCheckSize2(){
        game.addPlayer(new User("a"));
        game.addPlayer(new User("b"));
        game.addPlayer(new User("c"));
        game.addPlayer(new User("d"));
        Assert.assertEquals(false, game.checkSize());
    }

    @Test
    public void testAddPlayer1(){
        Assert.assertEquals(0, game.getPlayerNumber());
        game.addPlayer(user1);
        Assert.assertEquals(1, game.getPlayerNumber());
    }

    @Test
    public void testAddPlayer2(){
        game.addPlayer(new User("a"));
        game.addPlayer(new User("b"));
        game.addPlayer(new User("c"));
        game.addPlayer(new User("d"));
        Assert.assertEquals(false, game.addPlayer(user1));
    }

    @Test
    public void testAddPlayer3(){
        Assert.assertEquals(true, game.addPlayer(user1));
    }

    @After
    public void setNull() {
        this.game.setPlayerList(new ArrayList<>());
        this.game = null;
        this.user1 = null;
    }


}
