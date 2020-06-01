package com.floriankleewein.commonclasses.Network;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.GameLogic.GameHandler;

public class StartGameMsg implements BaseMessage{

    private int feedbackUI;
    private Game game;
    private GameHandler gameHandler;

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

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
