package com.floriankleewein.commonclasses.Network;

import com.floriankleewein.commonclasses.Game;

public class StartGameMsg extends BaseMessage {

    private int feedbackUI;
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getFeedbackUI() {
        return feedbackUI;
    }

    public void setFeedbackUI(int feedbackUI) {
        this.feedbackUI = feedbackUI;
    }

}
