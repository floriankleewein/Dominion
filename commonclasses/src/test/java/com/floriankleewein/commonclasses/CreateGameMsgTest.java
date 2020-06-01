package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.network.CreateGameMsg;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateGameMsgTest {

    CreateGameMsg msg;

    @Before
    public void setup(){
        msg = new CreateGameMsg();
    }

    @Test
    public void testHasGame(){
        Assert.assertEquals(false, msg.isHasGame());
        msg.setHasGame(true);
        Assert.assertEquals(true, msg.isHasGame());
    }

    @Test
    public void testGame(){
        Assert.assertEquals(null, msg.getGame());
        Game temp = Game.getGame();
        msg.setGame(Game.getGame());
        Assert.assertEquals(temp, msg.getGame());
    }

    @After
    public void clean(){

    }

}
