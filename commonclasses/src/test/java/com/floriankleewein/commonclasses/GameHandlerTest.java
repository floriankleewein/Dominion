package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.Board.Board;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Cards.EstateCard;
import com.floriankleewein.commonclasses.Cards.EstateType;
import com.floriankleewein.commonclasses.Cards.MoneyCard;
import com.floriankleewein.commonclasses.Cards.MoneyType;
import com.floriankleewein.commonclasses.GameLogic.GameHandler;
import com.floriankleewein.commonclasses.User.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GameHandlerTest {

    private Game game;
    private GameHandler m_cut;
    private final int CON_COPPER = 7;
    private final int CON_ANWESEN = 3;

    @Before
    public void setup() {
        game = Game.getGame();
        m_cut = new GameHandler(game);
    }

    @Test(expected = Exception.class)
    public void checkNotEnoughPlayers() throws Exception {
        m_cut.prepareGame();
    }

    @Test
    public void checkBoardBuyFields() {
        Board board = new Board();
        m_cut.prepareGame();
        Assert.assertEquals(board.getBuyField().getCardsToBuy(), m_cut.getBoard().getBuyField().getCardsToBuy());
    }

    @Test
    public void checkBoardActionField() {
        Board board = new Board();
        m_cut.prepareGame();
        Assert.assertEquals(board.getActionField().getActionCardsToBuy(), m_cut.getBoard().getActionField().getActionCardsToBuy());
    }

    @Test
    public void checkStarterHands() {
        User user1 = new User("a");
        User user2 = new User("b");
        game.addPlayer(user1);
        game.addPlayer(user2);
        m_cut.prepareGame();
        Assert.assertEquals(generateStarterHands(), m_cut.getGame().getPlayerList().get(0).getUserCards().getDeck());
    }

    private ArrayList<Card> generateStarterHands() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < CON_COPPER; i++) {
            Card copper = new MoneyCard(0, 0, MoneyType.KUPFER);
            cards.add(copper);
        }
        for (int i = 0; i < CON_ANWESEN; i++) {
            Card anwesen = new EstateCard(2, 1, EstateType.ANWESEN);
            cards.add(anwesen);
        }
        return cards;
    }


}
