package com.group7.dominion.Cards;

import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.group7.dominion.CardActivity;

public class Card {
    private int price;
    private ImageButton imageButton;
    private CardActivity cardActivity;

    public Card(CardActivity cardActivity, int price) {
        this.price = price;
        this.cardActivity = cardActivity;
    }

    public void initImageButton() {
        imageButton = new ImageButton(cardActivity);
        imageButton.setTag(1);
        //imageButton.setId(1);
        imageButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ImageButton getImageButton() {
        return imageButton;
    }

    public void setImageButton(ImageButton imageButton) {
        this.imageButton = imageButton;
    }
}
