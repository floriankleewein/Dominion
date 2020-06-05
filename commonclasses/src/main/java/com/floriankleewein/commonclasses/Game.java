package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.cheatfunction.CheatService;
import com.floriankleewein.commonclasses.user.User;
import java.util.ArrayList;
import java.util.List;

public class Game {

    /**
     * FKDoc: the playerlist, which holds all registered players.
     */
    private List<User> playerList = new ArrayList<>();

    /**
     * FKDoc: hidden class variable for Singleton pattern.
     */
    private static Game game;
    private User activePlayer;

    private static CheatService cheatService;

    /**
     * FKDoc: usually we should overwrite the standardconstructor for singleton patterns. kryonet wants to have one,
     *        so in this case he is only private.
     */
    private Game() {
    }

    /**
     * FKDoc: getter for the singleton game. it instances a new game if its still null, otherwise
     *        the instance is returned.
     * @return
     */
    public static synchronized Game getGame() {
        if (Game.game == null) {
            Game.game = new Game();
            cheatService = new CheatService(game);
        }
        return Game.game;
    }

    /**
     * FKDoc: setter for the game. At some points the other coders needed the possibilty to update it.
     * @param game
     */
    public static void setGame(Game game) {
        Game.game = game;
    }

    /**
     * FKDoc: with this method you can add a user to the game, after the checks passed.
     * @param user
     * @return
     */
    public boolean addPlayer(User user) {
        if (checkName(user.getUserName()) && checkSize()) {
            playerList.add(user);
            return true;
        }
        return false;
    }

    /**
     * FKDoc: iterates over all users, checking if the wanted name is already in use.
     * @param name
     * @return
     */
    public boolean checkName(String name) {
        for (User user : playerList) {
            if (user.getUserName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * FKDoc: checks the size, to make sure there arent too many players in the game.
     * @return
     */
    public boolean checkSize() {
        boolean ret = false;
        if (getPlayerNumber() < 4) {
            ret = true;
        }
        return ret;
    }

    /**
     * FKDoc: with this function its possible to find a user by its name.
     * @param name
     * @return
     */
    public User findUser(String name) {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getUserName().equals(name)) {
                return playerList.get(i);
            }
        }
        return null;
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

    public int getPlayerNumber() {
        return game.playerList.size();
    }

    public CheatService getCheatService() {
        return cheatService;
    }
}
