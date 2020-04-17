package com.group7.dominion.Cards;

import android.widget.ImageButton;

public class Card {
    private int price;
    //private ImageButton imageButton;

    public Card(int price) {
        this.price = price;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
