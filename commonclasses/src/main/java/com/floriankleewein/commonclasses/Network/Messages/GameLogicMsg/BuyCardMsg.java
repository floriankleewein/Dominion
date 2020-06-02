package com.floriankleewein.commonclasses.Network.Messages.GameLogicMsg;

import com.floriankleewein.commonclasses.cards.ActionType;
import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.cards.EstateType;
import com.floriankleewein.commonclasses.cards.MoneyType;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.BaseMessage;

public class BuyCardMsg extends BaseMessage {
    private Card boughtCard;
    private MoneyType moneyTypeClicked;
    private ActionType actionTypeClicked;
    private EstateType estateTypeClicked;
    private boolean cantBuyCard;
    private Game game;


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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

    public boolean isCantBuyCard() {
        return cantBuyCard;
    }

    public void setCantBuyCard(boolean cantBuyCard) {
        this.cantBuyCard = cantBuyCard;
    }

    public Card getBoughtCard() {
        return boughtCard;
    }

    public void setBoughtCard(Card boughtCard) {
        this.boughtCard = boughtCard;
    }
}
