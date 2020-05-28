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
import com.floriankleewein.commonclasses.User.GamePoints;
import com.floriankleewein.commonclasses.User.User;
import com.floriankleewein.commonclasses.User.UserCards;

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
    private final int MONEY_CARDS = 7;
    private final int ANWESEN_CARDS = 3;
    private Board board;
    private Card playedCard;
    private Card buyCard;
    private PlayStatus turnState;
    private CardLogic cardLogic;

    /**
     * Logic for the Game, uses the singleton game to handle game logic. Creates Board, Cards for Players
     * and handles played and bought cards.
     *
     * @param game
     */
    public GameHandler(Game game) {
        this.game = game;
        cardLogic = new CardLogic(this);
    }

    public GameHandler() {
    }

    public void prepareGame() {
        if (cardLogic == null) {
            cardLogic = new CardLogic(this);
        }
        List<User> playerList = game.getPlayerList();
        if (playerList.size() >= 1) {
            for (User user : playerList) {
                GamePoints gp = new GamePoints();
                gp.setWinningPoints(3);
                user.setGamePoints(gp);
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
            setNewActivePlayer();
            setTurnState(PlayStatus.ACTION_PHASE);
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
        setTurnState(PlayStatus.ACTION_PHASE);
    }

    /**
     * Discards Hand of activeUser, draws new Cards and sets new Active User.
     * TODO Check if players draw new cards if < 5.
     */
    public void newTurn() {
        getActiveUser().getUserCards().drawNewCards();
        setNewActivePlayer();
        for (User user : game.getPlayerList()) {
            user.getGamePoints().setPointsDefault();
        }
    }

    /**
     * Change Turn Phase, if end of turn setPlay Status to noPlayPhase
     * Can be checked everytime since only if turn phase status is set will it change to the next phase.
     *
     * @return
     */
    public PlayStatus changeTurnStatus() {
        if (getTurnState() == null) {
            return null;
        }
        else if (getTurnState().equals(PlayStatus.ACTION_PHASE)) {
            setTurnState(PlayStatus.BUY_PHASE);
            return PlayStatus.BUY_PHASE;
        }
        else if (getTurnState().equals(PlayStatus.BUY_PHASE)) {
            setTurnState(PlayStatus.NO_PLAY_PHASE);
            return PlayStatus.NO_PLAY_PHASE;
        }
        else {
            setTurnState(PlayStatus.NO_PLAY_PHASE);
            return PlayStatus.NO_PLAY_PHASE;
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
                return;
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
        }
    }

    public void playCard(MoneyCard card) {
        setPlayedCard(card);
        if (isNoPlayPhase()) return;
        else {
            setTurnState(PlayStatus.BUY_PHASE);
            cardLogic.doCardLogic(card);
            getActiveUser().getGamePoints().modifyPlayAmounts(-1);
        }
    }


    private boolean canPlayActionCard() {
        if (getActiveUser().getGamePoints().getPlaysAmount() > 0 && turnState.equals(PlayStatus.ACTION_PHASE)) {
            return true;
        }
        return false;
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

    public void buyCard(ActionCard card) {
        canBuyCard(card);
        Card boughtCard;
        boughtCard = getBoard().getActionField().pickCard(card.getActionType());
        getActiveUser().getUserCards().addCardtoDeck(boughtCard);
        int oldCoins = getActiveUser().getGamePoints().getCoins();
        getActiveUser().getGamePoints().setCoins(oldCoins - boughtCard.getPrice());
        getActiveUser().getGamePoints().modifyBuyAmounts(-1);
    }

    public void buyCard(EstateCard card) {
        canBuyCard(card);
        Card boughtCard;
        boughtCard = getBoard().getBuyField().pickCard(card.getEstateType());
        getActiveUser().getUserCards().addCardtoDeck(boughtCard);
        getActiveUser().getGamePoints().modifyWinningPoints(((EstateCard) boughtCard).getEstateValue());
        int oldCoins = getActiveUser().getGamePoints().getCoins();
        getActiveUser().getGamePoints().setCoins(oldCoins - boughtCard.getPrice());
        getActiveUser().getGamePoints().modifyBuyAmounts(-1);
    }

    public void buyCard(MoneyCard card) {
        canBuyCard(card);
        Card boughtCard;
        boughtCard = getBoard().getBuyField().pickCard(card.getMoneyType());
        getActiveUser().getUserCards().addCardtoDeck(boughtCard);
        int oldCoins = getActiveUser().getGamePoints().getCoins();
        getActiveUser().getGamePoints().setCoins(oldCoins - boughtCard.getPrice());
        getActiveUser().getGamePoints().modifyBuyAmounts(-1);
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

    public GameUpdateMsg updateGameHandlerTwo(GameUpdateMsg msg) {
        setBoard(msg.getBoard());
        setPlayedCard(msg.getPlayedCard());
        Game.setGame(msg.getGame());
        setGame();

        Card boughtCard = null;
        if (canBuyCardTwo(msg)) {
            boughtCard = buyCardTwo(msg);
        }
        msg.setBoughtCard(boughtCard);

        updateVictoryPts(msg);
        return msg;
    }

    //LKDoc: new buy methods cause I can't cast on Cards - ActionType has only ActionCard and not Card
    public Card buyCard(ActionType actionType) {
        Card boughtCard = getBoard().getActionField().pickCard(actionType);
        getActiveUser().getUserCards().addCardtoDeck(boughtCard);
        calculateCoinsOnActiveUser(boughtCard);
        return boughtCard;
    }

    public Card buyCard(EstateType estateType) {
        Card boughtCard = getBoard().getBuyField().pickCard(estateType);
        getActiveUser().getUserCards().addCardtoDeck(boughtCard);
        getActiveUser().getGamePoints().modifyWinningPoints(((EstateCard) boughtCard).getEstateValue());
        calculateCoinsOnActiveUser(boughtCard);
        return boughtCard;
    }

    public Card buyCard(MoneyType moneyType) {
        Card boughtCard = getBoard().getBuyField().pickCard(moneyType);
        getActiveUser().getUserCards().addCardtoDeck(boughtCard);
        calculateCoinsOnActiveUser(boughtCard);
        return boughtCard;
    }

    private void calculateCoinsOnActiveUser(Card boughtCard) {
        int oldCoins = getActiveUser().getGamePoints().getCoins();
        getActiveUser().getGamePoints().modifyCoins(oldCoins - boughtCard.getPrice());
    }

    public Card buyCardTwo(GameUpdateMsg gameUpdateMsg) {
        if (gameUpdateMsg.getActionTypeClicked() != null) {
            System.out.println("Bought card Type: " + gameUpdateMsg.getActionTypeClicked());
            return buyCard(gameUpdateMsg.getActionTypeClicked());
        } else if (gameUpdateMsg.getEstateTypeClicked() != null) {
            System.out.println("Bought card Type: " + gameUpdateMsg.getEstateTypeClicked());
            return buyCard(gameUpdateMsg.getEstateTypeClicked());
        } else if (gameUpdateMsg.getMoneyTypeClicked() != null) {
            System.out.println("Bought card Type: " + gameUpdateMsg.getMoneyTypeClicked());
            return buyCard(gameUpdateMsg.getMoneyTypeClicked());
        } else {
            return null;
        }
    }


    private boolean isActionPhase() {
        if (turnState.equals(PlayStatus.ACTION_PHASE)) return true;
        return false;
    }

    private boolean isBuyPhase() {
        if (turnState.equals(PlayStatus.BUY_PHASE)) return true;
        return false;
    }

    private boolean isNoPlayPhase() {
        if (turnState.equals(PlayStatus.NO_PLAY_PHASE)) return true;
        return false;
    }


    private boolean canBuyCard(Card card) {
        if (card == null) {
            return false;
        }
        if (getActiveUser().getGamePoints().getCoins() >= card.getPrice() && getActiveUser().getGamePoints().getBuyAmounts() > 0 && !getTurnState().equals(PlayStatus.NO_PLAY_PHASE)) {
            setTurnState(PlayStatus.BUY_PHASE);
            return true;
        }
        return false;
    }

    private boolean canBuyCardTwo(GameUpdateMsg gameUpdateMsg) {
        boolean noCard = false;
        if (gameUpdateMsg.getActionTypeClicked() != null) {
            noCard = true;
        } else if (gameUpdateMsg.getEstateTypeClicked() != null) {
            noCard = true;
        } else if (gameUpdateMsg.getMoneyTypeClicked() != null) {
            noCard = true;
        } else {
            return noCard;
        }

        if (noCard) {
            /*
            if (getActiveUser().getGamePoints().getCoins() >= card.getPrice()) {
                return true;
            }
            */
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
