package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.User.User;

import java.util.List;
import java.util.Random;

public class Game {

    private List<User> playerList;
    private static Game game;
    private int id;

    private Game(){}

    public static synchronized Game getGame(){
        if(Game.game == null){
            Game.game = new Game();
            game.setRandomId();
        }
        //System.out.println("ID: " + game.id);
        //TODO: check correct position for the setRandomId() call.
        //game.setRandomId();
        return Game.game;
    }

    public List<User> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<User> playerList) {
        this.playerList = playerList;
    }

    public void addPlayer(User user){
        playerList.add(user);
    }

    public void setRandomId(){
        id = (int) (Math.random() * 10000);
        System.out.println("ID: " + game.id);
    }

}
