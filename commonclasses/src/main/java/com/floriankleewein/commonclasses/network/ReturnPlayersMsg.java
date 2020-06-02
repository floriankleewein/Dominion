package com.floriankleewein.commonclasses.network;

import com.floriankleewein.commonclasses.Game;



public class ReturnPlayersMsg extends BaseMessage {

   private Game game;


    public Game getPlayerList() { return game;}

    public void setList (Game game) {
        this.game = game;
    }
}