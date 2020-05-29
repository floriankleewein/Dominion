package com.floriankleewein.commonclasses.Network;

import com.floriankleewein.commonclasses.GameLogic.GameHandler;

public class GetGameMsg extends BaseMessage {
    private GameHandler gm;


    public GameHandler getGm() {
        return gm;
    }

    public void setGm(GameHandler gm) {
        this.gm = gm;
    }


}
