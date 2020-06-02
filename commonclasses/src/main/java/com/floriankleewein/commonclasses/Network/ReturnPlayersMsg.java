package com.floriankleewein.commonclasses.Network;

import com.floriankleewein.commonclasses.Game;



public class ReturnPlayersMsg extends BaseMessage {

   private Game game;


    public Game getPlayerList() { return game;}

    public void setList (Game game) {
        this.game = game;
    }
}