package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.Network.StartGameMsg;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StartGameMsgTest {

    StartGameMsg msg;

    @Before
    public void setup(){
        msg = new StartGameMsg();
    }

    @Test
    public void testFeedbackUI(){
        msg.setFeedbackUI(0);
        Assert.assertEquals(0, msg.getFeedbackUI());
    }

    @Test
    public void testGame(){
        Assert.assertEquals(null, msg.getGame());
        Game temp = Game.getGame();
        msg.setGame(temp);
        Assert.assertEquals(temp, msg.getGame());
    }
}
