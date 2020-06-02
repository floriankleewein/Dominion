package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.network.HasCheatedMessage;
import com.floriankleewein.commonclasses.network.SuspectMessage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CheatMessagesTest {

        HasCheatedMessage hasCheatedMessage;
        SuspectMessage suspectMessage;

        @Before
        public void setUp () {
            this. hasCheatedMessage = new HasCheatedMessage();
            this.suspectMessage = new SuspectMessage();

        }

        @Test
        public void testHasCheatedMessage () {
            hasCheatedMessage.setName("Cheater");
            Assert.assertEquals("Cheater",hasCheatedMessage.getName());
        }
        @Test
        public void testsuspectMessage () {
            suspectMessage.setUserName("Player1");
            suspectMessage.setSuspectedUserName("Player2");

            Assert.assertEquals("Player1",suspectMessage.getUserName());
            Assert.assertEquals("Player2",suspectMessage.getSuspectedUserName());
        }
    }

