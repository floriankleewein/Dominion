package com.floriankleewein.commonclasses.CheatFunction;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.User.User;

import java.util.List;


public class CheatService {


    private List<User> PlayerList;
    private static CheatService cheatService;


    public static synchronized CheatService getGame() {
        if (CheatService.cheatService == null) {
            cheatService = new CheatService(Game.getGame());
        }
        return CheatService.cheatService;
    }

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


    public List<User> getPlayerList() {
        return PlayerList;
    }

    public void setPlayerList(List<User> playerList) {
        PlayerList = playerList;
    }

}