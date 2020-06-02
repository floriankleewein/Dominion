package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.Network.GameUpdateMsg;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameUpdateMsgTest {

    GameUpdateMsg msg;

    @Before
    public void setup(){
        msg = new GameUpdateMsg();
    }

    @Test
    public void test1(){

    }

    @After
    public void clean(){
        msg = null;
    }
}
