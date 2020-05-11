package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.Network.UpdatePlayerNamesMsg;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UpdatePlayerNamesMsgTest {

    UpdatePlayerNamesMsg msg;
    List<String> temp;

    @Before
    public void setup(){
        msg = new UpdatePlayerNamesMsg();
        temp = new ArrayList<>();
    }

    @Test
    public void testNameList(){
        msg.setNameList(temp);
        Assert.assertEquals(temp, msg.getNameList());
    }

    @After
    public void clean(){
        msg = null;
        temp = null;
    }
}

