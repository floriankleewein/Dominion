package com.floriankleewein.commonclasses;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClassRegistrationTest {

    ClassRegistration cr;
    Server server;
    Client client;

    @Before
    public void setUp() {
        cr = new ClassRegistration();
        server = new Server();
        client = new Client();
    }

    //TODO: FK: ClassRegistration will need a return value, otherwise no assert tests are possible. the 2 methods dont return anything so...

    @Test
    public void testRegistrationForServer() {
        //cr.registerAllClassesForServer(server);
    }

    @Test
    public void testRegistrationForClient() {
        //cr.registerAllClassesForClient(client);
    }

    @After
    public void clean(){
        cr = null;
        server = null;
        client = null;
    }
}
