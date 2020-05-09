package com.floriankleewein.commonclasses.GameLogic;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.User.User;

import java.util.ArrayList;
import java.util.List;

public class GameHandler {
    Game game;
    List<User> playerList = new ArrayList<>();

    public GameHandler(Game game) {
        this.game = game;
    }

    public void addPlayer(User user) {
        playerList.add(user);
    }
}
