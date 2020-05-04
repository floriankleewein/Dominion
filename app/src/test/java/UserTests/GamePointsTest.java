package UserTests;

import com.floriankleewein.commonclasses.User.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GamePointsTest {
    static String UserName = "TestUser";
    static String UserEmail = "test@email.com";
    static String UserPassword = "TestPassword";

    private User user;

    @Before
    public void setUp() {
        this.user = new User(UserName, UserEmail, UserPassword);
        this.user.setUpforGame();
    }

    @Test
    public void testIncreaseCoins() {
        this.user.getGamePoints().increaseCoins(1);
        this.user.getGamePoints().increaseCoins(3);

        Assert.assertEquals(4, this.user.getGamePoints().getCoins());
    }

    @Test
    public void testDecreaseCoins() {
        this.user.getGamePoints().setCoins(10);
        this.user.getGamePoints().decreaseCoins(10);
        Assert.assertEquals(0, this.user.getGamePoints().getCoins());
    }

    @Test
    public void testIncreaseWinningPoints () {
        this.user.getGamePoints().increaseWinningPoints(1);
        this.user.getGamePoints().increaseWinningPoints(3);
        this.user.getGamePoints().increaseWinningPoints(6);

        Assert.assertEquals(10,this.user.getGamePoints().getWinningPoints());
    }

    @Test
    public void testDecreaseWinningPoints () {
        this.user.getGamePoints().setWinningPoints(10);
        this.user.getGamePoints().decreaseWinningPoints(11);

        Assert.assertEquals(-1,this.user.getGamePoints().getWinningPoints());
    }

    @Test
    public void testIncreasePlaysAmount () {
        this.user.getGamePoints().increasePlaysAmount(1);
        this.user.getGamePoints().increasePlaysAmount(2);
        Assert.assertEquals(4,this.user.getGamePoints().getPlaysAmount());
        //+1 because you start with one Play Amount
    }
    @Test
    public void testDecreasePlaysAmount () {
        this.user.getGamePoints().setPlaysAmount(3);
        this.user.getGamePoints().decreasePlaysAmount(2);
        this.user.getGamePoints().decreasePlaysAmount(1);
        Assert.assertEquals(0,this.user.getGamePoints().getPlaysAmount());
    }

    @Test
    public void testIncreaseBuymount () {
        this.user.getGamePoints().increaseBuyAmounts(1);
        this.user.getGamePoints().increaseBuyAmounts(2);
        Assert.assertEquals(4,this.user.getGamePoints().getBuyAmounts());
        //+1 because you start with one Buy Amount
    }
    @Test
    public void testDecreaseBuyAmount () {
        this.user.getGamePoints().setBuyAmounts(3);
        this.user.getGamePoints().decreaseBuyAmounts(2);
        this.user.getGamePoints().decreaseBuyAmounts(1);
        Assert.assertEquals(0,this.user.getGamePoints().getBuyAmounts());
    }

    @After
    public void setNull() {
        this.user = null;
    }
}
