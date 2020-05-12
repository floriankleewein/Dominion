package com.floriankleewein.commonclasses.Network;

import com.floriankleewein.commonclasses.Board.Board;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.User.User;

public class GameUpdateMsg extends BaseMessage {
    private Game game;
    private Board board;
    private User activeUser;
    private Card playedCard;

    public Card getPlayedCard() {
        return playedCard;
    }

    public void setPlayedCard(Card playedCard) {
        this.playedCard = playedCard;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
