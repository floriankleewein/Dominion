package com.floriankleewein.commonclasses.GameLogic;

import com.floriankleewein.commonclasses.Board.Board;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Cards.EstateCard;
import com.floriankleewein.commonclasses.Cards.EstateType;
import com.floriankleewein.commonclasses.Cards.MoneyCard;
import com.floriankleewein.commonclasses.Cards.MoneyType;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.User.User;
import com.floriankleewein.commonclasses.User.UserCards;

import java.util.ArrayList;
import java.util.List;


public class GameHandler {
    private Game game;
    private List<User> playerList = new ArrayList<>();
    private final int MONEY_CARDS = 7;
    private final int ANWESEN_CARDS = 3;

    public GameHandler(Game game) {
        this.game = game;
        playerList.addAll(game.getPlayerList());
    }

    public void prepareGame() {
        if(playerList.size() > 1) {
            for (User user: playerList) {
                UserCards ucards = new UserCards();
                for (int i = 0; i < MONEY_CARDS; i++) {
                    Card copper = new MoneyCard(0,0, MoneyType.KUPFER);
                    ucards.addCardtoDeck(copper);
                }
                for (int i = 0; i < ANWESEN_CARDS; i++) {
                    Card anwesen = new EstateCard(2,1,EstateType.ANWESEN);
                    ucards.addCardtoDeck(anwesen);
                }
                user.setUserCards(ucards);
            }
        }
        game.setBoard(new Board());
        game.setPlayerList(playerList);
        game.setActivePlayer(playerList.get(0));
    }
}
