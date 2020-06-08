package com.group7.localtestserver;

import com.esotericsoftware.kryonet.Connection;
import com.floriankleewein.commonclasses.network.AddPlayerSuccessMsg;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestServerIntegrationTest {
    TestServer m_cut;

    @Before
    public void setup() {
        m_cut = new TestServer();
    }

    @After
    public void teardown() {
        m_cut = null;
    }


    @Test
    public void startGame() {
        m_cut.createGame();
        Assert.assertEquals(true, m_cut.hasGame());
    }

    @Test
    public void createGame() {
        m_cut.createGame();
        Assert.assertEquals(true, m_cut.setupGame());
    }

}
