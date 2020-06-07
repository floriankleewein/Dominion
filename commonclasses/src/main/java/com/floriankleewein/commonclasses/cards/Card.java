package com.floriankleewein.commonclasses.cards;

public class Card {

    private int id;
    private int price;

    public Card () {}

    public Card(int price) {
        this.price = price;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
