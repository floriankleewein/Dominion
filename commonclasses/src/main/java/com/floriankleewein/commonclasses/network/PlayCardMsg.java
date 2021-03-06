package com.floriankleewein.commonclasses.network;

import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.gamelogic.PlayStatus;

public class PlayCardMsg extends BaseMessage {

    Card playedCard;
    Game game;
    PlayStatus playStatus;

    public Card getPlayedCard() {
        return playedCard;
    }

    public void setPlayedCard(Card playedCard) {
        this.playedCard = playedCard;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public PlayStatus getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(PlayStatus playStatus) {
        this.playStatus = playStatus;
    }
}
