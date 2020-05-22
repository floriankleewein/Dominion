package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.Network.ClientConnector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class ClientConnectorTest {
    ClientConnector clientConnector;

    @Before
    public void setUp() {
        clientConnector = ClientConnector.getClientConnector();
    }


    @Test
    public void testConnect() {
        clientConnector.connect();

        //And then..??
        Assert.assertEquals(true, clientConnector.isConnected());
    }
    @Test
    public void testCreateGame () {
        clientConnector.connect();
        clientConnector.createGame();
    }
}
