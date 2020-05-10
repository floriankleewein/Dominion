package com.floriankleewein.commonclasses.Network;

import com.floriankleewein.commonclasses.Game;

public class GetGameMsg extends BaseMessage {
    private Game game;


    public Game getGame(){
        return this.game;
    }

    public void setGame(Game game){
        this.game = game;
    }
}
