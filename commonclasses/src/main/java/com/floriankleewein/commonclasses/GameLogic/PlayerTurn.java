package com.floriankleewein.commonclasses.GameLogic;

import com.floriankleewein.commonclasses.Board.Board;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.User.User;
import com.floriankleewein.commonclasses.User.UserCards;

public class PlayerTurn {
    Game game;
    User user;
    UserCards userCards;
    Board board;

    public PlayerTurn(Game game, User user) {
        this.game = game;
        this.user = user;
        this.userCards = user.getUserCards();
        //this.board = game.getBoard();
    }


    public void actionPhase() {
        userCards.drawNewCards();
    }

    public void buyPhase() {

    }

    public void cleanUpPhase() {

    }




}
