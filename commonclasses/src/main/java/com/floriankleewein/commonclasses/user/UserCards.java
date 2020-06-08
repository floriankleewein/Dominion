package com.floriankleewein.commonclasses.user;


import com.floriankleewein.commonclasses.cards.Card;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class UserCards {

    private LinkedList<Card> deck;
    private LinkedList<Card> handCards;
    private LinkedList<Card> discardCards;

    public UserCards() {
    }

    /**@Author Maurer Florian
     * Get the first cards for the game
     * @param givenCards
     */

    public void getFirstCards(LinkedList<Card> givenCards) {
        this.deck = new LinkedList<>();
        this.handCards = new LinkedList<>();
        this.discardCards = new LinkedList<>();


        this.deck = givenCards;
        shufflesCards();
        drawNewCards();
    }

    /**@Author Maurer Florian
     * Shuffles the deck cards.
     */

    public void shufflesCards() {

        Collections.shuffle(this.deck);
    }

    /**@Author Maurer Florian
     * Draw new Hand Cards from the Deck.
     */

    public void drawNewCards() {

        int decksize = deck.size();

        this.discardCards.addAll(handCards);

        this.handCards.clear();

        if (decksize < 5) {
            this.deck.addAll(discardCards);
            shufflesCards();
            this.discardCards.clear();
        }
        for (int i = 4; i >= 0; i--) {
            this.handCards.add(this.deck.get(i));
        }
        for (int i = 4; i >= 0; i--) {
            this.deck.remove(i);
        }
    }

    /**@Author Maurer Florian
     * Add the Card to the Discard Pile
     * @param card
     */

    public void addCardToDiscardPile(Card card) {
        this.discardCards.add(card);
    }

    /**@Author Maurer Florian
     * Removes the played Card from the handcards and added it to the discard pile.
     * @param playedCard
     */
    public void playCard(Card playedCard) {

        for (int i = 0; i < getHandCards().size(); i++) {
            if (playedCard.getId() == getHandCards().get(i).getId()) {
                getHandCards().remove(i);
                break;
            }
        }

        this.discardCards.add(playedCard);
    }

    /**@Author Maurer Florian
     * adds a new card to the deck
     * @param newCard
     */

    public void addCardtoDeck(Card newCard) {
        this.deck.add(newCard);
    }

    /**@Author Maurer Florian
     * adds cards to the deck
     * @param givenCards
     */
    public void putCardsinTheDeck(List<Card> givenCards) {
        this.deck.addAll(givenCards);
    }

    /**@Author Maurer Florian
     * adds deckcards to the the handcards. If the deck size is to low, a new deck will be generated from the
     * discard pile.
     * @param amountCards
     */

    public void addDeckCardtoHandCard(int amountCards) {

        if (deck.size() < 5) {
            deck.addAll(discardCards);
            discardCards.clear();
            Collections.shuffle(deck);
        }
        for (int i = 0; i < amountCards; i++) {
            this.handCards.add(this.deck.getLast());
            this.deck.removeLast();
        }
    }

    /**@Author Maurer Florian
     * Checks if a "Burggraben" card is in the HandCards
     * @return
     */


    public boolean hasMoat() {
        boolean ret = false;
        if (getHandCards().stream().anyMatch(card -> "0".matches(String.valueOf(card.getId())))) {
            ret = true;
        }
        return ret;
    }


    public LinkedList<Card> getDeck() {
        return deck;
    }

    public void setDeck(LinkedList<Card> deck) {
        this.deck = deck;
    }

    public LinkedList<Card> getHandCards() {
        return handCards;
    }

    public void setHandCards(LinkedList<Card> handCards) {
        this.handCards = handCards;
    }

    public LinkedList<Card> getDiscardCards() {
        return discardCards;
    }

    public void setDiscardCards(LinkedList<Card> discardCards) {
        this.discardCards = discardCards;
    }

}

