package com.floriankleewein.commonclasses.GameLogic;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.User.User;
import com.floriankleewein.commonclasses.User.UserCards;

public class PlayerTurn {
    Game game;
    User user;
    UserCards userCards;

    public PlayerTurn(Game game, User user) {
        this.game = game;
        this.user = user;
    }


}
