package com.floriankleewein.commonclasses;

import com.floriankleewein.commonclasses.User.User;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<User> playerList = new ArrayList<>();

    //hidden class variable for Singleton pattern.
    private static Game game;
    //overwriting constructor so it cannot be instanced.
    private Game(){}

    public static synchronized Game getGame(){
        if(Game.game == null){
            Game.game = new Game();
        }
        return Game.game;
    }

    public List<User> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<User> playerList) {
        this.playerList = playerList;
    }

    public void addPlayer(User user){
        if(!playerList.contains(user)){
            playerList.add(user);
        }else{
            //TODO: what else when user is already there?
        }

    }
}
