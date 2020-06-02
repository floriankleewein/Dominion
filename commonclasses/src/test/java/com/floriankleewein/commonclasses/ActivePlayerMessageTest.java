package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.Network.ActivePlayerMessage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ActivePlayerMessageTest {
    ActivePlayerMessage msg;
    Game game;

    @Before
    public void setup() {
        msg = new ActivePlayerMessage();
        game = Game.getGame();
    }


    @Test
    public void testGetGame(){
       Assert.assertEquals(null, msg.getGame());
    }

    @Test
    public void testSetGame(){
        msg.setGame(game);
        Assert.assertEquals(game, msg.getGame());
    }

    @After
    public void clean() {
        msg = null;
        game = null;
    }
}
