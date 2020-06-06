package com.floriankleewein.commonclasses.gamelogic;

import com.esotericsoftware.minlog.Log;
import com.floriankleewein.commonclasses.board.Board;
import com.floriankleewein.commonclasses.cards.ActionCard;
import com.floriankleewein.commonclasses.cards.ActionType;
import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.cards.EstateCard;
import com.floriankleewein.commonclasses.cards.EstateType;
import com.floriankleewein.commonclasses.cards.MoneyCard;
import com.floriankleewein.commonclasses.cards.MoneyType;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.network.GameUpdateMsg;
import com.floriankleewein.commonclasses.user.User;

import java.util.LinkedList;
import java.util.List;


public class GameHandler {
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private Game game;
    private static final  int CON_MONEY_CARDS = 7;
    private static final int CON_ANWESEN_CARDS = 3;
    private Board board;
    private Card playedCard;
    private Card buyCard;
    private PlayStatus turnState;
    private CardLogic cardLogic;



    private boolean newTurn;

    /**
     * Logic for the Game, uses the singleton game to handle game logic. Creates Board, Cards for Players
     * and handles played and bought cards.
     *
     * @param game
     */
    public GameHandler(Game game) {
        this.game = game;
    }

    public GameHandler() {
    }

    /**
     * Must be called first before any other methods can be used. Initialises starter cards for players
     * and Gamepoints as well as starting cardlogic.
     */
    public void prepareGame() {
        cardLogic = new CardLogic(this);
        List<User> playerList = game.getPlayerList();
        if (!playerList.isEmpty()) {
            for (User user : playerList) {
                user.setUpforGame();
                LinkedList<Card> generatedCards = new LinkedList<>();
                for (int i = 0; i < CON_MONEY_CARDS; i++) {
                    Card copper = new MoneyCard(0, 1, MoneyType.KUPFER);
                    generatedCards.add(copper);
                }
                for (int i = 0; i < CON_ANWESEN_CARDS; i++) {
                    Card anwesen = new EstateCard(2, 1, EstateType.ANWESEN);
                    user.getGamePoints().modifyWinningPoints(1);
                    generatedCards.add(anwesen);
                }
                user.getUserCards().getFirstCards(generatedCards);
            }
            setBoard(new Board());
            game.setPlayerList(playerList);
            setNewActivePlayer();
        }
    }

    private void setNewActivePlayer() {
        if (getActiveUser() == null) {
            game.setActivePlayer(game.getPlayerList().get(0));
        } else {
            int players = game.getPlayerList().size();
            int active = game.getPlayerList().indexOf(getActiveUser());
            game.setActivePlayer(game.getPlayerList().get((active + 1) % players));
        }
        if (checkHandCards()) {
            turnState = PlayStatus.ACTION_PHASE;
        } else {
            turnState = PlayStatus.PLAY_COINS;
        }
    }

    /**
     * Discards Hand of activeUser, draws new Cards and sets new Active User.
     */
    public void newTurn() {
        newTurn = true;
        getActiveUser().getUserCards().drawNewCards();
        setNewActivePlayer();
        for (User user : game.getPlayerList()) {
            user.getGamePoints().setPointsDefault();
        }
    }

    /**
     * In case enums are not working over KryoNet
     *
     * @param i
     */
    public void setTurnState(int i) {
        switch (i) {
            case 1:
                setTurnState(PlayStatus.ACTION_PHASE);
                break;
            case 2:
                setTurnState(PlayStatus.BUY_PHASE);
                break;
            case 3:
                setTurnState(PlayStatus.NO_PLAY_PHASE);
                break;
            default:
                break;
        }
    }


    public void playCard(Card card) {
        setPlayedCard(card);
        if (card instanceof ActionCard) playCard((ActionCard) card);
        else if (card instanceof MoneyCard) playCard((MoneyCard) card);
    }

    public void playCard(ActionCard card) {
        setPlayedCard(card);
        if (canPlayActionCard()) {
            cardLogic.doCardLogic(card);
            getActiveUser().getGamePoints().modifyPlayAmounts(-1);
            if ((getActiveUser().getGamePoints().getPlaysAmount() <= 1) || (!checkHandCards())) {
                turnState = PlayStatus.PLAY_COINS;
            }
        }
    }

    public void playCard(MoneyCard card) {
        if (!isNoPlayPhase()) {
            cardLogic.doCardLogic(card);
        }
    }


    private boolean canPlayActionCard() {
        return (getActiveUser().getGamePoints().getPlaysAmount() > 0 && isActionPhase());
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
    }

    public void buyCard(ActionCard card) {
        if (canBuyCard(card)) {
            Card boughtCard;
            boughtCard = getBoard().getActionField().pickCard(card.getActionType());
            getActiveUser().getUserCards().addCardToDiscardPile(boughtCard);
            int oldCoins = getActiveUser().getGamePoints().getCoins();
            getActiveUser().getGamePoints().setCoins(oldCoins - boughtCard.getPrice());
            getActiveUser().getGamePoints().modifyBuyAmounts(-1);
        }

    }

    public void buyCard(EstateCard card) {
        if (canBuyCard(card)) {
            Card boughtCard;
            boughtCard = getBoard().getBuyField().pickCard(card.getEstateType());
            getActiveUser().getUserCards().addCardToDiscardPile(boughtCard);
            getActiveUser().getGamePoints().modifyWinningPoints(((EstateCard) boughtCard).getEstateValue());
            int oldCoins = getActiveUser().getGamePoints().getCoins();
            getActiveUser().getGamePoints().setCoins(oldCoins - boughtCard.getPrice());
            getActiveUser().getGamePoints().modifyBuyAmounts(-1);
        }
    }

    public void buyCard(MoneyCard card) {
        if (canBuyCard(card)) {
            Card boughtCard;
            boughtCard = getBoard().getBuyField().pickCard(card.getMoneyType());
            getActiveUser().getUserCards().addCardToDiscardPile(boughtCard);
            int oldCoins = getActiveUser().getGamePoints().getCoins();
            getActiveUser().getGamePoints().setCoins(oldCoins - boughtCard.getPrice());
            getActiveUser().getGamePoints().modifyBuyAmounts(-1);
        }
    }

    public void buyCard(Card card) {
        if (card instanceof MoneyCard) {
            buyCard((MoneyCard) card);
        } else if (card instanceof ActionCard) {
            buyCard((ActionCard) card);
        } else {
            buyCard((EstateCard) card);
        }
    }

    //LKDoc: new buy methods cause I can't cast on Cards - ActionType has only ActionCard and not Card
    public Card buyCard(ActionType actionType) {
        Card checkCard = getBoard().getActionField().getActionCard(actionType);
        if (canBuyCard(checkCard)) {
            Card boughtCard = getBoard().getActionField().pickCard(actionType);
            getActiveUser().getUserCards().addCardToDiscardPile(boughtCard);
            calculateCoinsOnActiveUser(boughtCard);
            if (getActiveUser().getGamePoints().getBuyAmounts() <= 0) {
                newTurn();
            }
            return boughtCard;
        }


        return null;
    }

    public Card buyCard(EstateType estateType) {
        Card checkCard = getBoard().getBuyField().getEstateCard(estateType);
        if (canBuyCard(checkCard)) {
            Card boughtCard = getBoard().getBuyField().pickCard(estateType);
            getActiveUser().getUserCards().addCardToDiscardPile(boughtCard);
            getActiveUser().getGamePoints().modifyWinningPoints(((EstateCard) boughtCard).getEstateValue());
            calculateCoinsOnActiveUser(boughtCard);
            if (getActiveUser().getGamePoints().getBuyAmounts() <= 0) {
                newTurn();
            }
            return boughtCard;
        }
        return null;
    }

    public Card buyCard(MoneyType moneyType) {
        Card checkCard = getBoard().getBuyField().getMoneyCard(moneyType);
        if (canBuyCard(checkCard)) {
            Card boughtCard = getBoard().getBuyField().pickCard(moneyType);
            getActiveUser().getUserCards().addCardToDiscardPile(boughtCard);
            calculateCoinsOnActiveUser(boughtCard);
            if (getActiveUser().getGamePoints().getBuyAmounts() <= 0) {
                newTurn();
            }
            return boughtCard;
        }
        return null;
    }

    private void calculateCoinsOnActiveUser(Card boughtCard) {
        int oldCoins = getActiveUser().getGamePoints().getCoins();
        getActiveUser().getGamePoints().setCoins(oldCoins - boughtCard.getPrice());
        getActiveUser().getGamePoints().modifyBuyAmounts(-1);
    }

    private boolean isActionPhase() {
        return turnState.equals(PlayStatus.ACTION_PHASE);
    }

    private boolean isBuyPhase() {
        return turnState.equals(PlayStatus.BUY_PHASE);
    }

    private boolean isNoPlayPhase() {
        return turnState.equals(PlayStatus.NO_PLAY_PHASE);
    }


    private boolean canBuyCard(Card card) {
        if (card == null) {
            Log.info("Card is null.");
            return false;
        }
        if (getActiveUser().getGamePoints().getCoins() >= card.getPrice() && getActiveUser().getGamePoints().getBuyAmounts() > 0) {
            setTurnState(PlayStatus.BUY_PHASE);
            return true;
        }
        return false;
    }

    public PlayStatus getTurnState() {
        return turnState;
    }

    public void setTurnState(PlayStatus turnState) {
        this.turnState = turnState;
    }

    public User getActiveUser() {
        return game.getActivePlayer();
    }

    public boolean checkHandCards() {
        for (int i = 0; i < getActiveUser().getUserCards().getHandCards().size(); i++) {
            if (getActiveUser().getUserCards().getHandCards().get(i) instanceof ActionCard) {
                return true;
            }
        }
        return false;
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

    public boolean isNewTurn() {
        return newTurn;
    }

    public void setNewTurn(boolean newTurn) {
        this.newTurn = newTurn;
    }

}
