package com.floriankleewein.commonclasses.User;

import com.group7.dominion.Board.ActionField;
import com.group7.dominion.Cards.Action;
import com.group7.dominion.Cards.ActionCard;
import com.group7.dominion.Cards.ActionType;
import com.group7.dominion.Cards.Card;
import com.group7.dominion.Cards.EstateCard;
import com.group7.dominion.Cards.EstateType;
import com.group7.dominion.Cards.MoneyCard;
import com.group7.dominion.Cards.MoneyType;
import com.group7.dominion.MainActivity;
import com.group7.localtestserver.Main;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Class User
 * The Lists should have the params Card
 */

public class UserCards {

    private LinkedList<Object> Deck;
    private LinkedList<Object> HandCards;
    private LinkedList<Object> DiscardCards;

    private MainActivity mainActivity;
/*
    public UserCards(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
*/
    public void getFirstCards(LinkedList<Object> GivenCards) {
        this.Deck = new LinkedList<Object>();
        this.HandCards = new LinkedList<Object>();
        this.DiscardCards = new LinkedList<Object>();

        this.Deck = GivenCards;
        shufflesCards();
        drawNewCards();
    }


    public void shufflesCards() {

        Collections.shuffle(this.Deck);
    }


    public void drawNewCards() {

        int decksize = Deck.size();

        this.DiscardCards.addAll(HandCards);

        this.HandCards.clear();

        if (decksize < 5) {
            this.Deck.addAll(DiscardCards);
            shufflesCards();
            this.DiscardCards.clear();
        }
        for (int i = 4; i >= 0; i--) {
            this.HandCards.add(this.Deck.get(i));
        }
        for (int i = 4; i >= 0; i--) {
            this.Deck.remove(i);
        }
    }

    public void playCard(Object PlayedCard) {

        this.HandCards.remove(PlayedCard);
        this.DiscardCards.add(PlayedCard);

    }


    public void addCardtoDeck(Object newCard) {
        this.Deck.add(newCard);
    }

    public void putCardsinTheDeck(LinkedList<Object> GivenCards) {
        this.Deck.addAll(GivenCards);
    }

    // Als Beispiel
    public MoneyCard getMoneyCard(MoneyType moneyType) {
        MoneyCard card = (MoneyCard) this.mainActivity.getBoard().getBuyField().pickCard(moneyType);
        return card;
    }

    // Als Beispiel
    public EstateCard getEstateCard(EstateType estateType) {
        EstateCard card = (EstateCard) this.mainActivity.getBoard().getBuyField().pickCard(estateType);
        return card;
    }

    // Als Beispiel
    public ActionCard getActionCard(ActionType actionType) {
        ActionCard card = (ActionCard) this.mainActivity.getBoard().getActionField().pickCard(actionType);
        // Wo soll die Aktion der gepickten Karte passieren => Netzwerk berücksichtigen ???
        /*
        Action action = card.getAction();
        switch(card.getActionType()) {
            case BURGGRABEN:
                // TODO: Logik hier
                action.getCardCount();
                if(action.isThrowEveryUserCardsUntilThreeLeft()) {

                }

                break;
                //TODO: Andere Typen
        }
        */
        return card;
    }

    public LinkedList<Object> getDeck() {
        return Deck;
    }

    public void setDeck(LinkedList<Object> deck) {
        Deck = deck;
    }

    public LinkedList<Object> getHandCards() {
        return HandCards;
    }

    public void setHandCards(LinkedList<Object> handCards) {
        HandCards = handCards;
    }

    public LinkedList<Object> getDiscardCards() {
        return DiscardCards;
    }

    public void setDiscardCards(LinkedList<Object> discardCards) {
        DiscardCards = discardCards;
    }

}

