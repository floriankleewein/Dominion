package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.User.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GamePointsTest {
    private static String UserName = "TestUser";

    private User user;

    @Before
    public void setUp() {
        this.user = new User(UserName);
        this.user.setUpforGame();
    }

    @Test
    public void testModifyCoins() {
        this.user.getGamePoints().modifyCoins(6);
        this.user.getGamePoints().modifyCoins(-2);

        Assert.assertEquals(4, this.user.getGamePoints().getCoins());
    }

    @Test
    public void testModifyWinningPoints () {
        this.user.getGamePoints().modifyWinningPoints(5);
        this.user.getGamePoints().modifyWinningPoints(3);
        this.user.getGamePoints().modifyWinningPoints(1);

        Assert.assertEquals(9,this.user.getGamePoints().getWinningPoints());
    }

    @Test
    public void testModifyPlaysAmount () {
        this.user.getGamePoints().modifyPlayAmounts(3);
        this.user.getGamePoints().modifyPlayAmounts(-1);
        Assert.assertEquals(3,this.user.getGamePoints().getPlaysAmount());
        //+1 because you start with one Play Amount
    }

    @Test
    public void testModifyBuymount () {
        this.user.getGamePoints().modifyBuyAmounts(3);
        this.user.getGamePoints().modifyBuyAmounts(-2);
        Assert.assertEquals(2,this.user.getGamePoints().getBuyAmounts());
        //+1 because you start with one Buy Amount
    }
    @Test
   public void testsetPointsDefault (){
        this.user.getGamePoints().setCoins(6);
        this.user.getGamePoints().setBuyAmounts(3);
        this.user.getGamePoints().setPlaysAmount(4);
        this.user.getGamePoints().setWinningPoints(15);

        this.user.getGamePoints().setPointsDefault();

        Assert.assertEquals(0,this.user.getGamePoints().getCoins());
        Assert.assertEquals(1,this.user.getGamePoints().getBuyAmounts());
        Assert.assertEquals(1,this.user.getGamePoints().getPlaysAmount());
        Assert.assertEquals(15, this.user.getGamePoints().getWinningPoints());
    }

    @After
    public void setNull() {
        this.user = null;
    }
}
