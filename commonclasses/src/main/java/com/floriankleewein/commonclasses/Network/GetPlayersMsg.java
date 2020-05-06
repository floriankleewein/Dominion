package com.floriankleewein.commonclasses.Network;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.User.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GetPlayersMsg extends BaseMessage {

   List <User> PlayerList;


    public List getPlayerList() { return PlayerList;}

    public void setList (List PlayerList) {
        this.PlayerList = PlayerList;
    }
}