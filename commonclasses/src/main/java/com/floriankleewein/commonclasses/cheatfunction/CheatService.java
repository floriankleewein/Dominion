package com.floriankleewein.commonclasses.cheatfunction;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.user.User;

import java.util.List;

public class CheatService {


    private List<User> playerList;


    public CheatService(Game game) {
        playerList = game.getPlayerList();
    }

    public User findUser(String name) {

        for (int i = 0; i < this.playerList.size(); i++) {
            if (playerList.get(i).getUserName().equals(name)) {
                return playerList.get(i);
            }
        }
        return null;
    }

    public void addCardtoUser(String name) {
        User cheatUser = findUser(name);
        if (cheatUser != null && (!cheatUser.isCheater())) {
            cheatUser.getUserCards().addDeckCardtoHandCard(1);
            cheatUser.setCheater(true);
        }
    }

    public User findCheater(String name) {
        User cheatUser = findUser(name);

        if (cheatUser.isCheater()) {
            return cheatUser;
        } else {
            return null;
        }
    }

    public void suspectUser(String suspectedUserName, String userName) {
        User suspectedUser = findCheater(suspectedUserName);
        User user = findUser(userName);

        if (suspectedUser != null) {
            suspectedUser.getGamePoints().modifyWinningPoints(-5);
        } else {
            user.getGamePoints().modifyWinningPoints(-5);
        }
    }

    /*
    Getter Setter
     */
}