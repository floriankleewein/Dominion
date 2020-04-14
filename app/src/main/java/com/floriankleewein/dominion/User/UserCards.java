package com.floriankleewein.dominion.User;

import java.util.Collections;
import java.util.LinkedList;

public class UserCards {

    private LinkedList<Object> Deck;
    private LinkedList<Object> HandCards;
    private LinkedList<Object> DiscardCards;


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

    public void getCard(String cardName) {
        /**
         * For specific ActionCards
         */
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

