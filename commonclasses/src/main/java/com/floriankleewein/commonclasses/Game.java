package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.Cheatfunction.CheatService;
import com.floriankleewein.commonclasses.User.User;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<User> playerList = new ArrayList<>();
    //hidden class variable for Singleton pattern.
    private static Game game;
    private User activePlayer;

    //overwriting constructor so it cannot be instanced.

    private static CheatService cheatService;

    Game() {
    }

    public static synchronized Game getGame() {
        if (Game.game == null) {
            Game.game = new Game();
            cheatService = new CheatService(game);
        }
        return Game.game;
    }

    public static void setGame(Game game) {
        Game.game = game;
    }

    public User getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(User activePlayer) {
        this.activePlayer = activePlayer;
    }

    public List<User> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<User> playerList) {
        this.playerList = playerList;
    }

    public boolean addPlayer(User user) {
        if (checkName(user.getUserName()) && checkSize()) {
            playerList.add(user);
            return true;
        }
        return false;
    }

    public boolean checkName(String name) {
        for (User user : playerList) {
            if (user.getUserName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkSize() {
        boolean ret = false;
        if (getPlayerNumber() < 4) {
            ret = true;
        }
        return ret;
    }


    public User findUser(String name) {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getUserName().equals(name)) {
                return playerList.get(i);
            }
        }
        return null;
    }

    public int getPlayerNumber() {
        return game.playerList.size();
    }

    public CheatService getCheatService() {
        return cheatService;
    }
}
