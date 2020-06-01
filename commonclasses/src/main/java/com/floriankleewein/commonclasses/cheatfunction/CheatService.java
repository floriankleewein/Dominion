package com.floriankleewein.commonclasses.cheatfunction;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.user.User;

import java.util.List;

public class CheatService {


    private List<User> PlayerList;


    public CheatService(Game game) {
        PlayerList = game.getPlayerList();
    }

    public User findUser(String name) {

        for (int i = 0; i < this.PlayerList.size(); i++) {
            if (PlayerList.get(i).getUserName().equals(name)) {
                return PlayerList.get(i);
            }
        }
        return null;
    }

    public void addCardtoUser(String name) {
        User CheatUser = findUser(name);
        if (CheatUser != null && (!CheatUser.isCheater())) {
            CheatUser.getUserCards().addDeckCardtoHandCard(1);
            CheatUser.setCheater(true);
        }
    }

    public User findCheater(String name) {
        User CheatUser = findUser(name);

        if (CheatUser.isCheater()) {
            return CheatUser;
        } else {
            return null;
        }
    }

    public void suspectUser(String SuspectedUserName, String UserName) {
        User SuspectedUser = findCheater(SuspectedUserName);
        User user = findUser(UserName);

        if (SuspectedUser != null) {
            SuspectedUser.getGamePoints().modifyWinningPoints(-5);
        } else {
            user.getGamePoints().modifyWinningPoints(-5);
        }
    }

    /*
    Getter Setter
     */
}