package com.floriankleewein.commonclasses.GameLogic;

import com.floriankleewein.commonclasses.Board.Board;
import com.floriankleewein.commonclasses.Cards.Action;
import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.Cards.ActionType;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Cards.EstateCard;
import com.floriankleewein.commonclasses.Cards.EstateType;
import com.floriankleewein.commonclasses.Cards.MoneyCard;
import com.floriankleewein.commonclasses.Cards.MoneyType;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.GameUpdateMsg;
import com.floriankleewein.commonclasses.User.User;
import com.floriankleewein.commonclasses.User.UserCards;

import java.util.LinkedList;
import java.util.List;


public class GameHandler {
    private Game game;
    private final int MONEY_CARDS = 7;
    private final int ANWESEN_CARDS = 3;
    private Board board;
    private Card playedCard;
    private Card buyCard;

    public GameHandler(Game game) {
        this.game = game;
    }

    public void prepareGame() {
        List<User> playerList = game.getPlayerList();
        if (playerList.size() > 1) {
            for (User user : playerList) {
                LinkedList<Card> generatedCards = new LinkedList<>();
                UserCards ucards = new UserCards();
                for (int i = 0; i < MONEY_CARDS; i++) {
                    Card copper = new MoneyCard(0, 0, MoneyType.KUPFER);
                    generatedCards.add(copper);
                }
                for (int i = 0; i < ANWESEN_CARDS; i++) {
                    Card anwesen = new EstateCard(2, 1, EstateType.ANWESEN);
                    generatedCards.add(anwesen);
                }
                ucards.getFirstCards(generatedCards);
                user.setUserCards(ucards);
            }
            setBoard(new Board());
            game.setPlayerList(playerList);
            game.setActivePlayer(playerList.get(0));
        }
    }

    public void setNewActivePlayer() {

    }

    public void newTurn() {
        //TODO reset MoneyPts etc.
    }

    private void updateVictoryPts(GameUpdateMsg msg) {
        int pts = 0;
        for (User u : game.getPlayerList()) {
            pts = msg.getVictoryPointsChange(u);
            if (pts != 0) {
                changeVictoryPoints(u, pts);
            }
        }
    }

    public Card getBuyCard() {
        return buyCard;
    }

    public void setBuyCard(Card buyCard) {
        this.buyCard = buyCard;
    }

    public void updateGameHandler(GameUpdateMsg msg) {
        setBoard(msg.getBoard());
        setPlayedCard(msg.getPlayedCard());
        setBuyCard(msg.getClickedCard());
        Game.setGame(msg.getGame());
        setGame();
        if (canBuyCard(getBuyCard())) {
            buyCard(getBuyCard());
        }
        updateVictoryPts(msg);
    }

    public void buyCard(Card card) {
        Card boughtCard;
        if (card instanceof ActionCard) {
            boughtCard = getBoard().getActionField().pickCard(((ActionCard) card).getActionType());
            getActiveUser().getUserCards().addCardtoDeck(boughtCard);
        } else if (card instanceof EstateCard) {
            boughtCard = getBoard().getBuyField().pickCard(((EstateCard) card).getEstateType());
            getActiveUser().getUserCards().addCardtoDeck(boughtCard);
            getActiveUser().getGamePoints().modifyWinningPoints(((EstateCard) boughtCard).getEstateValue());
        } else {
            boughtCard = getBoard().getBuyField().pickCard(((MoneyCard) card).getMoneyType());
            getActiveUser().getUserCards().addCardtoDeck(boughtCard);
        }
        int oldCoins = getActiveUser().getGamePoints().getCoins();
        getActiveUser().getGamePoints().modifyCoins(oldCoins - boughtCard.getPrice());
    }


    public void playCard() {
        // TODO logic for card played
        if (playedCard instanceof ActionCard) {
            ActionCard card = (ActionCard) playedCard;
            Action action = card.getAction();
            switch (card.getActionType()) {
                case BURGGRABEN:
                    getActiveUser().getUserCards().addDeckCardtoHandCard(); //TODO change method
                    getActiveUser().getUserCards().addDeckCardtoHandCard();
                    break;
                case DORF:
                    getActiveUser().getGamePoints().modifyPlayAmounts(action.getActionCount());
                    getActiveUser().getUserCards().addDeckCardtoHandCard(); //TODO if method was changed change this method to draw a number of cards
                    getActiveUser().getUserCards().addDeckCardtoHandCard();
                    break;
                case HEXE:
                    getActiveUser().getUserCards().addDeckCardtoHandCard();
                    getActiveUser().getUserCards().addDeckCardtoHandCard();
                    for (User user : game.getPlayerList()) {
                        if (!user.getUserName().equals(getActiveUser().getUserName())) {
                            user.getUserCards().addCardtoDeck(getBoard().getBuyField().pickCard(EstateType.FLUCH));
                            user.getGamePoints().modifyWinningPoints(-1);
                        }
                    }
                    break;
                case HOLZFAELLER:
                    getActiveUser().getGamePoints().modifyBuyAmounts(action.getBuyCount());
                    getActiveUser().getGamePoints().modifyCoins(action.getMoneyValue());
                    break;
                case KELLER:
                    //TODO discard/draw card other time
                    getActiveUser().getGamePoints().modifyPlayAmounts(action.getActionCount());
                    break;
                case MARKT:
                    getActiveUser().getUserCards().addDeckCardtoHandCard();
                    getActiveUser().getGamePoints().modifyBuyAmounts(action.getBuyCount());
                    getActiveUser().getGamePoints().modifyPlayAmounts(action.getActionCount());
                    getActiveUser().getGamePoints().modifyCoins(action.getMoneyValue());
                    break;
                case MILIZ:
                    getActiveUser().getGamePoints().modifyCoins(action.getMoneyValue());
                    outer:
                    for (User user : game.getPlayerList()) {
                        if (!user.getUserName().equals(getActiveUser().getUserName()) && user.getUserCards().getHandCards().size() >= 3) {
                            LinkedList<Card> handCards = user.getUserCards().getHandCards();
                            for (Card userCard : handCards) {
                                if (userCard instanceof ActionCard) {
                                    if (((ActionCard) userCard).getActionType().equals(ActionType.BURGGRABEN)) { // Beim Burggraben ist man geschützt vor miliz
                                        break outer;
                                    }
                                }
                            }
                            for (int i = 0; i < 3; i++) {
                                user.getUserCards().playCard(handCards.getLast());
                            }
                        }
                    }
                    break;
                case MINE:
                    LinkedList<Card> userCards = getActiveUser().getUserCards().getHandCards();
                    boolean hasSilver = false;
                    int index = -1;
                    for (Card usercard : userCards) {
                        if (usercard instanceof MoneyCard) {
                            if (((MoneyCard) usercard).getMoneyType().equals(MoneyType.SILBER)) {
                                hasSilver = true;
                                index = userCards.indexOf(usercard);
                            } else if (hasSilver == false && usercard instanceof MoneyCard && ((MoneyCard) usercard).getMoneyType().equals(MoneyType.KUPFER)) {
                                index = userCards.indexOf(usercard);
                            }
                        }
                    }
                    if (index >= 0) {
                        userCards.remove(index);
                        if (hasSilver) {
                            userCards.add(getBoard().getBuyField().pickCard(MoneyType.GOLD));
                        } else {
                            userCards.add(getBoard().getBuyField().pickCard(MoneyType.SILBER));
                        }
                        getActiveUser().getUserCards().setHandCards(userCards);
                    }
                    break;
                case SCHMIEDE:
                    getActiveUser().getUserCards().addDeckCardtoHandCard();
                    getActiveUser().getUserCards().addDeckCardtoHandCard();
                    getActiveUser().getUserCards().addDeckCardtoHandCard();
                    break;
                case WERKSTATT:
                    getActiveUser().getGamePoints().modifyCoins(action.getMoneyValue());
                    break;
            }
        } else {
            MoneyCard card = (MoneyCard) playedCard;
            getActiveUser().getGamePoints().modifyCoins(card.getWorth());
        }
    }

    private boolean canBuyCard(Card card) {
        if (card == null) {
            return false;
        }
        if (getActiveUser().getGamePoints().getCoins() >= card.getPrice()) {
            return true;
        }
        return false;
    }


    public User getActiveUser() {
        return game.getActivePlayer();
    }

    private void changeVictoryPoints(User user, int points) {
        List<User> users = game.getPlayerList();
        for (User u : users) {
            if (u.getUserName().equals(user.getUserName())) {
                u.getGamePoints().modifyWinningPoints(points);
            }
        }
        game.setPlayerList(users);
    }

    private void setGame() {
        this.game = Game.getGame();
    }

    public Card getPlayedCard() {
        return playedCard;
    }

    public void setPlayedCard(Card playedCard) {
        this.playedCard = playedCard;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
