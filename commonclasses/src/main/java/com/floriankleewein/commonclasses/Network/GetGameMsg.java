package com.floriankleewein.commonclasses.Network;

import com.floriankleewein.commonclasses.Game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GetGameMsg extends BaseMessage {
    private Game game;
    List<String> nameList = new ArrayList<>();

    public Game getGame(){
        return this.game;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }
}
