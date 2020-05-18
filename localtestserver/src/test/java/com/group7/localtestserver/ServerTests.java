


package com.group7.localtestserver;

import com.floriankleewein.commonclasses.Game;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServerTests {
    TestServer server;

    @Before
    public void setup(){
        server = new TestServer();
    }

    @Test
    public void startServerTest(){
        server.startServer();
        Assert.assertEquals(0, server.getServer().getConnections().length);
    }

    @Test
    public void createGameTest(){
        Assert.assertEquals(false, server.hasGame());
        server.createGame();
        Assert.assertEquals(true, server.hasGame());
        Assert.assertEquals(Game.getGame(), server.getGame());
    }

    @After
    public void clean(){
        server = null;
    }
}


