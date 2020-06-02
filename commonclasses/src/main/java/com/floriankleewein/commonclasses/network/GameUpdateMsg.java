package com.floriankleewein.commonclasses.network;

import com.floriankleewein.commonclasses.board.Board;
import com.floriankleewein.commonclasses.cards.ActionType;
import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.cards.EstateType;
import com.floriankleewein.commonclasses.cards.MoneyType;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.gamelogic.GameHandler;
import com.floriankleewein.commonclasses.gamelogic.PlayStatus;
import com.floriankleewein.commonclasses.user.User;

import java.util.HashMap;
import java.util.Map;


public class GameUpdateMsg extends BaseMessage {
    private Game game;
    private Board board;
    private User activeUser;
    private Card playedCard;
    private Card clickedCard;
    private Card boughtCard;
    private GameHandler gameHandler;
    private PlayStatus turnStatus;
    private Map<User, Integer> victoryPointsChange = new HashMap<>();

    private MoneyType moneyTypeClicked;
    private ActionType actionTypeClicked;
    private EstateType estateTypeClicked;


    private boolean newHandCards;

    public GameUpdateMsg() {
        /*
        for (User u : game.getPlayerList()) {
            victoryPointsChange.put(u, 0);
        }
        */
        newHandCards = false;
    }

    public PlayStatus getTurnStatus() {
        return turnStatus;
    }

    public void setTurnStatus(PlayStatus turnStatus) {
        this.turnStatus = turnStatus;
    }

    public Card getClickedCard() {
        return clickedCard;
    }

    public void setClickedCard(Card clickedCard) {
        this.clickedCard = clickedCard;
    }

    public int getVictoryPointsChange(User user) {
        for (Map.Entry<User, Integer> entry : victoryPointsChange.entrySet()) {
            if (entry.getKey().getUserName().equals(user.getUserName())) {
                return entry.getValue();
            }
        }
        return 0;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void addVictoryPointsChange(User user, int points) {
        int pts = victoryPointsChange.get(user);
        victoryPointsChange.replace(user, pts, pts + points);
    }

    public Card getPlayedCard() {
        return playedCard;
    }

    public void setPlayedCard(Card playedCard) {
        this.playedCard = playedCard;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public MoneyType getMoneyTypeClicked() {
        return moneyTypeClicked;
    }

    public void setMoneyTypeClicked(MoneyType moneyTypeClicked) {
        this.moneyTypeClicked = moneyTypeClicked;
    }

    public ActionType getActionTypeClicked() {
        return actionTypeClicked;
    }

    public void setActionTypeClicked(ActionType actionTypeClicked) {
        this.actionTypeClicked = actionTypeClicked;
    }

    public EstateType getEstateTypeClicked() {
        return estateTypeClicked;
    }

    public void setEstateTypeClicked(EstateType estateTypeClicked) {
        this.estateTypeClicked = estateTypeClicked;
    }

    public Card getBoughtCard() {
        return boughtCard;
    }

    public void setBoughtCard(Card boughtCard) {
        this.boughtCard = boughtCard;
    }

    public boolean isNewHandCards() {
        return newHandCards;
    }

    public void setNewHandCards(boolean newHandCards) {
        this.newHandCards = newHandCards;
    }

}

