package com.floriankleewein.commonclasses;

import com.esotericsoftware.kryonet.Client;
import com.floriankleewein.commonclasses.Network.ClientConnector;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientConnectorTests {

    ClientConnector clientConnector;

    @Before
    public void setup(){
        clientConnector = ClientConnector.getClientConnector();
    }

    @Test
    public void singletonTest(){
        Assert.assertEquals(clientConnector, ClientConnector.getClientConnector());
    }

    @Test
    public void connectTest(){
        Assert.assertEquals(false, clientConnector.getClient().isConnected());
        clientConnector.connect();
        Assert.assertEquals(true, clientConnector.getClient().isConnected());
    }

    @After
    public void clean(){

    }
}
