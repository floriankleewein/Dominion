package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.Network.AddPlayerSuccessMsg;
import com.floriankleewein.commonclasses.User.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class AddPlayerSuccessMsgTest {

        AddPlayerSuccessMsg msg;
        User user;

        @Before
        public void setup() {
                msg = new AddPlayerSuccessMsg();
                user = new User("x");
        }

        @Test
        public void testPlayerName(){
                msg.setPlayerName("a");
                Assert.assertEquals("a", msg.getPlayerName());
        }

        @Test
        public void testUser(){
                msg.setUser(user);
                Assert.assertEquals(user, msg.getUser());
        }

        @Test
        public void testIsPlayerAdded(){
                Assert.assertEquals(false, msg.isPlayerAdded());
                msg.setUser(user);
                msg.setPlayerAdded(true);
                Assert.assertEquals(true, msg.isPlayerAdded());
        }

        @Test
        public void testFeedBackUI(){
                msg.setFeedbackUI(0);
                Assert.assertEquals(0, msg.getFeedbackUI());
        }

        @After
        public void clean() {
                msg = null;
                user = null;
        }


}
