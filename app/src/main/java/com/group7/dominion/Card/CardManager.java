package com.group7.dominion.Card;

import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class CardManager {
    private List<CardUI> cards;
    private LinearLayout linearLayout;

    private static CardManager cardManager;

    private CardManager(LinearLayout linearLayout) {
        this.cards = new ArrayList<>();
        this.linearLayout = linearLayout;
    }

    public static CardManager getCardManager(LinearLayout linearLayout) {
        if (CardManager.cardManager == null) {
            CardManager.cardManager = new CardManager(linearLayout);
        }
        return CardManager.cardManager;
    }

    public void showImage(CardUI card) {
        this.linearLayout.addView(card.getImageButton());
        this.cards.add(card);
    }

    public void showImageInfo(int imageButtonId) {
        // Lösche alle ImageButtons (hier sind ImageInfoButtons nicht berücksichtigt!!!)
        for(CardUI card: cards) {
            this.linearLayout.removeView(card.getImageButton());
        }

        // Ersetze bestehenden ImageButton und lade alle anderen ImageButton neu
        for(CardUI card: cards) {
            if(card.getImageButton().getId() == imageButtonId) {
                this.linearLayout.addView(card.getInfoImageButton());
            } else {
                this.linearLayout.addView(card.getImageButton());
            }
        }
    }
}
