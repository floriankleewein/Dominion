package com.floriankleewein.commonclasses.CheatFunction;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.User.User;

import java.util.List;




public class CheatService {


    private List<User> PlayerList;


    public static synchronized CheatService getCheatService() {
        if (CheatService.cheatService == null) {
            cheatService = new CheatService(Game.getGame());
        }
        return CheatService.cheatService;
    }

    public User findUser(String name) {

        for (int i = 0; i < Game.getGame().getPlayerList().size(); i++) {
            if (Game.getGame().getPlayerList().get(i).equals(name)) {
                return Game.getGame().getPlayerList().get(i);
            }
        }
        return null;
    }

    public void addCardtoUser(String name) {
        User CheatUser = findUser(name);
        if (CheatUser != null && (!CheatUser.isCheater())) {
            CheatUser.getUserCards().addDeckCardtoHandCard();
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
            SuspectedUser.getGamePoints().decreaseWinningPoints(5);
        } else {
            user.getGamePoints().decreaseWinningPoints(5);
        }
    }

    /*
    Getter Setter
     */


}