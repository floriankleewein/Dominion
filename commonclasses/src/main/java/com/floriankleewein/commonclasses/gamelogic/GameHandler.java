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

    /**
     * Empty Kontruktor required for KryoNet.
     */
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

    /**
     * Private helper method to determine the new player. Uses modulo to
     * set the new active player.
     */
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
     * Discards Hand of activeUser, draw new Cards and set new Active User.
     * Also Resets points of all users to the default value.
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
     * Set actual turn state.
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

    /**
     * Will call the correct method to handle cardlogic.
     * @param card
     */
    public void playCard(Card card) {
        setPlayedCard(card);
        if (card instanceof ActionCard) playCard((ActionCard) card);
        else if (card instanceof MoneyCard) playCard((MoneyCard) card);
    }

    /**
     * Will check if the action card can be played and use the class CardLogic to handle card effects.
     * @param card
     */
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

    /**
     * Will check if a Money Card can be played and use the class CardLogic to handle card effects.
     * @param card
     */
    public void playCard(MoneyCard card) {
        if (!isNoPlayPhase()) {
            cardLogic.doCardLogic(card);
        }
    }

    /**
     * Checks turn state and Game Points in order to assert that the card can be played.
     * @return
     */
    private boolean canPlayActionCard() {
        return (getActiveUser().getGamePoints().getPlaysAmount() > 0 && isActionPhase());
    }

    /**
     * Get the card that is set to be bought.
     * @return
     */
    public Card getBuyCard() {
        return buyCard;
    }

    /**
     * Set Card that needs to be bought.
     * @param buyCard
     */
    public void setBuyCard(Card buyCard) {
        this.buyCard = buyCard;
    }

    /**
     * Uses the GameUpdateMessage to set all changed values in gamehandler. Multiple changes can be handled
     * in one message if required.
     * @param msg
     */
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

    /**
     * Checks if the Action card can be bought and adds it to the players discard deck.
     * @param card
     */
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

    /**
     * Checks if the Estate card can be bought and adds it to the players discard deck.
     * @param card
     */
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

    /**
     * Checks if the Money card can be bought and adds it to the players discard deck.
     * @param card
     */
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

    /**
     * Used to call the correct method.
     * @param card
     */
    public void buyCard(Card card) {
        if (card instanceof MoneyCard) {
            buyCard((MoneyCard) card);
        } else if (card instanceof ActionCard) {
            buyCard((ActionCard) card);
        } else {
            buyCard((EstateCard) card);
        }
    }



    /**
     * Uses enum Type ActionType to buy Action cards.
     * @param actionType
     * @return
     */
    public Card buyCard(ActionType actionType) {
        //LKDoc: new buy methods cause I can't cast on Cards - ActionType has only ActionCard and not Card
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

    /**
     * Uses the Enum Type EstateType to buy Estate cards.
     * @param estateType
     * @return
     */
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

    /**
     * Uses enum Type MoneyType to buy Money cards.
     * @param moneyType
     * @return
     */
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

    /**
     * Called after buying cards. Modifies coin and Buy values.
     * @param boughtCard
     */
    private void calculateCoinsOnActiveUser(Card boughtCard) {
        int oldCoins = getActiveUser().getGamePoints().getCoins();
        getActiveUser().getGamePoints().setCoins(oldCoins - boughtCard.getPrice());
        getActiveUser().getGamePoints().modifyBuyAmounts(-1);
    }

    /**
     * Return true if turn is in Action Phase.
     * @return
     */
    private boolean isActionPhase() {
        return turnState.equals(PlayStatus.ACTION_PHASE);
    }

    /**
     * Return true if turn is in Buy Phase.
     * @return
     */
    private boolean isBuyPhase() {
        return turnState.equals(PlayStatus.BUY_PHASE);
    }

    /**
     * Return true if game is in No Play Phase.
     * @return
     */
    private boolean isNoPlayPhase() {
        return turnState.equals(PlayStatus.NO_PLAY_PHASE);
    }

    /**
     * Private helper method to check if card can be bought.
     * Checks coin value and Buy value of active user.
     * @param card
     * @return
     */
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

    /**
     * Return current turn state.
     * @return
     */
    public PlayStatus getTurnState() {
        return turnState;
    }

    /**
     * Set TurnState with the Enum PlayStatus.
     * @param turnState
     */
    public void setTurnState(PlayStatus turnState) {
        this.turnState = turnState;
    }

    /**
     * Return active user of the Game.
     * @return
     */
    public User getActiveUser() {
        return game.getActivePlayer();
    }

    /**
     * Checks if the active user has an Action Card on hand.
     * @return
     */
    public boolean checkHandCards() {
        for (int i = 0; i < getActiveUser().getUserCards().getHandCards().size(); i++) {
            if (getActiveUser().getUserCards().getHandCards().get(i) instanceof ActionCard) {
                return true;
            }
        }
        return false;
    }

    /**
     * Set Game. Used for updating.
     */
    private void setGame() {
        this.game = Game.getGame();
    }

    /**
     * Return the card that is played.
     * @return
     */
    public Card getPlayedCard() {
        return playedCard;
    }

    /**
     * Set the card that is to be played.
     * @param playedCard
     */
    public void setPlayedCard(Card playedCard) {
        this.playedCard = playedCard;
    }

    /**
     * Return the current board.
     * @return
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Set the actual Board.
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Used to check if a new turn was started.
     * @return
     */
    public boolean isNewTurn() {
        return newTurn;
    }

    /**
     * Set that a new turn should be started.
     * @param newTurn
     */
    public void setNewTurn(boolean newTurn) {
        this.newTurn = newTurn;
    }

}
