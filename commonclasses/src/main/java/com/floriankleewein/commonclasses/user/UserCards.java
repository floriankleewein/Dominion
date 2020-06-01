package com.floriankleewein.commonclasses.user;


import com.floriankleewein.commonclasses.cards.Card;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Class User
 * The Lists should have the params Card
 */

public class UserCards {

    private LinkedList<Card> Deck;
    private LinkedList<Card> HandCards;
    private LinkedList<Card> DiscardCards;

    public UserCards() {
    }

    public void getFirstCards(LinkedList<Card> GivenCards) {
        this.Deck = new LinkedList<Card>();
        this.HandCards = new LinkedList<Card>();
        this.DiscardCards = new LinkedList<Card>();


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

    public void addCardToDiscardPile(Card card) {
        this.DiscardCards.add(card);
    }

    public void playCard(Card PlayedCard) {

        for (int i = 0; i < getHandCards().size(); i++) {
            if (PlayedCard.getId() == getHandCards().get(i).getId()) {
                getHandCards().remove(i);
                break;
            }
        }

        this.DiscardCards.add(PlayedCard);
    }

    public void addCardtoDeck(Card newCard) {
        this.Deck.add(newCard);
    }

    public void putCardsinTheDeck(LinkedList<Card> GivenCards) {
        this.Deck.addAll(GivenCards);
    }


    public void addDeckCardtoHandCard(int amountCards) {

        if (Deck.size() < 5) {
            Deck.addAll(DiscardCards);
            DiscardCards.clear();
            Collections.shuffle(Deck);
        }
        for (int i = 0; i < amountCards; i++) {
            this.HandCards.add(this.Deck.getLast());
            this.Deck.removeLast();
        }
    }

    public boolean hasMoat() {
        boolean ret = false;
        if (getHandCards().stream().anyMatch(card -> "0".matches(String.valueOf(card.getId())))) {
            ret = true;
        }
        return ret;
    }


    public LinkedList<Card> getDeck() {
        return Deck;
    }

    public void setDeck(LinkedList<Card> deck) {
        Deck = deck;
    }

    public LinkedList<Card> getHandCards() {
        return HandCards;
    }

    public void setHandCards(LinkedList<Card> handCards) {
        HandCards = handCards;
    }

    public LinkedList<Card> getDiscardCards() {
        return DiscardCards;
    }

    public void setDiscardCards(LinkedList<Card> discardCards) {
        DiscardCards = discardCards;
    }

}
