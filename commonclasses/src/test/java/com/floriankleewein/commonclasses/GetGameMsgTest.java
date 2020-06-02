package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.gamelogic.PlayStatus;
import com.floriankleewein.commonclasses.network.GameUpdateMsg;
import com.floriankleewein.commonclasses.network.GetGameMsg;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetGameMsgTest {

    GetGameMsg msg;
    Game game;
    PlayStatus temp;

    @Before
    public void setup(){
        msg = new GetGameMsg();
        game = Game.getGame();
    }

    @Test
    public void testSetGetGame(){
        msg.setGame(game);
        Assert.assertEquals(game, msg.getGame());
    }

    @Test
    public void testSetGetPlayStatus(){
        Assert.assertEquals(null, msg.getPlayStatus());
        msg.setPlayStatus(PlayStatus.PLAY_COINS);
        Assert.assertEquals(PlayStatus.PLAY_COINS, msg.getPlayStatus());

    }

    @After
    public void clean(){
        msg = null;
        game = null;
    }
}
