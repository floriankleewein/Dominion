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

import java.util.LinkedList;


public class GameHandlerTest {

    private Game game;
    private GameHandler m_cut;
    private final int CON_COPPER = 7;
    private final int CON_ANWESEN = 3;

    @Before
    public void setup() {
        game = Game.getGame();
        game.addPlayer(new User("c"));
        m_cut = new GameHandler(game);
    }

    @Test
    public void checkBoardBuyFields() {
        User user1 = new User("a");
        User user2 = new User("b");
        game.addPlayer(user1);
        game.addPlayer(user2);
        Board board = new Board();
        m_cut.prepareGame();
        Assert.assertEquals(board.getBuyField().getCardsToBuy().size(), m_cut.getBoard().getBuyField().getCardsToBuy().size());
    }

    @Test
    public void checkBoardActionField() {
        User user1 = new User("a");
        User user2 = new User("b");
        game.addPlayer(user1);
        game.addPlayer(user2);
        Board board = new Board();
        m_cut.prepareGame();
        Assert.assertEquals(board.getActionField().getActionCardsToBuy().size(), m_cut.getBoard().getActionField().getActionCardsToBuy().size());
    }

    @Test
    public void checkStarterHands() {
        User user1 = new User("a");
        User user2 = new User("b");
        game.addPlayer(user1);
        game.addPlayer(user2);
        m_cut.prepareGame();
        int m_cut_starterhand = Game.getGame().getActivePlayer().getUserCards().getDeck().size();
        int m_cut_deck = Game.getGame().getActivePlayer().getUserCards().getHandCards().size();
        Assert.assertEquals(generateStarterCards().size(), m_cut_starterhand + m_cut_deck);
    }

    private LinkedList<Card> generateStarterCards() {
        LinkedList<Card> cards = new LinkedList<>();
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
