package com.group7.dominion.Card;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Cards.EstateCard;
import com.floriankleewein.commonclasses.Cards.MoneyCard;
import com.group7.dominion.R;

public class CardUI {
    private Card card;
    private ImageButton imageButton;
    private ImageButton infoImageButton;
    private LinearLayout linearLayout;
    private int xPosition;
    private int yPosition;

    public CardUI(int id, int xPosition, int yPosition, Card card, Activity activity, LinearLayout linearLayout, float scale) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.linearLayout = linearLayout;
        this.card = card;
        this.imageButton = new ImageButton(activity);
        this.imageButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.imageButton.setPadding(0,0,0,0);
        this.imageButton.setBackground(null);
        this.imageButton.setScaleX(scale);
        this.imageButton.setScaleY(scale);
        this.imageButton.setId(id);

        if(this.card instanceof ActionCard) {
            this.infoImageButton = new ImageButton(activity);
            this.infoImageButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            this.infoImageButton.setPadding(0,0,0,0);
            this.infoImageButton.setBackground(null);
            this.infoImageButton.setScaleX(scale);
            this.infoImageButton.setScaleY(scale);
            this.infoImageButton.setId(id+123456789);

            this.imageButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    showImageInfo();
                }
            });
        }
    }

    public void init() {
        if(this.card instanceof ActionCard) {
            ActionCard card = (ActionCard) this.card;

            switch(card.getActionType()) {
                case BURGGRABEN:
                    this.imageButton.setImageResource(R.drawable.burggraben);
                    this.infoImageButton.setImageResource(R.drawable.burggraben_info);
                    break;
                case DORF:
                    this.imageButton.setImageResource(R.drawable.dorf);
                    this.infoImageButton.setImageResource(R.drawable.dorf_info);
                    break;
                case HOLZFAELLER:
                    this.imageButton.setImageResource(R.drawable.holzfaeller);
                    this.infoImageButton.setImageResource(R.drawable.holzfaeller_info);
                    break;
                case KELLER:
                    this.imageButton.setImageResource(R.drawable.keller);
                    this.infoImageButton.setImageResource(R.drawable.keller_info);
                    break;
                case WERKSTATT:
                    this.imageButton.setImageResource(R.drawable.werkstatt);
                    this.infoImageButton.setImageResource(R.drawable.werkstatt_info);
                    break;
                case SCHMIEDE:
                    this.imageButton.setImageResource(R.drawable.schmiede);
                    this.infoImageButton.setImageResource(R.drawable.schmiede_info);
                    break;
                case MARKT:
                    this.imageButton.setImageResource(R.drawable.markt);
                    this.infoImageButton.setImageResource(R.drawable.markt_info);
                    break;
                case HEXE:
                    this.imageButton.setImageResource(R.drawable.hexe);
                    this.infoImageButton.setImageResource(R.drawable.hexe_info);
                    break;
                case MINE:
                    this.imageButton.setImageResource(R.drawable.mine);
                    this.infoImageButton.setImageResource(R.drawable.mine_info);
                    break;
                case MILIZ:
                    this.imageButton.setImageResource(R.drawable.miliz);
                    this.infoImageButton.setImageResource(R.drawable.miliz_info);
                    break;
            }
        } else if(this.card instanceof EstateCard) {
            EstateCard card = (EstateCard) this.card;

            switch(card.getEstateType()) {
                case ANWESEN:
                    this.imageButton.setImageResource(R.drawable.anwesen);
                    break;
                case PROVINZ:
                    this.imageButton.setImageResource(R.drawable.provinz);
                    break;
                case HERZOGTUM:
                    this.imageButton.setImageResource(R.drawable.herzogtum);
                    break;
                case FLUCH:
                    this.imageButton.setImageResource(R.drawable.fluch);
                    break;
            }
        } else if(this.card instanceof MoneyCard) {
            MoneyCard card = (MoneyCard) this.card;

            switch(card.getMoneyType()) {
                case KUPFER:
                    this.imageButton.setImageResource(R.drawable.kupfer);
                    break;
                case SILBER:
                    this.imageButton.setImageResource(R.drawable.silber);
                    break;
                case GOLD:
                    this.imageButton.setImageResource(R.drawable.gold);
                    break;
            }
        }
    }

    public ImageButton getImageButton() {
        return imageButton;
    }

    public void setImageButton(ImageButton imageButton) {
        this.imageButton = imageButton;
    }

    public ImageButton getInfoImageButton() {
        return infoImageButton;
    }

    public void setInfoImageButton(ImageButton infoImageButton) {
        this.infoImageButton = infoImageButton;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public void showImage() {
        CardManager.getCardManager(linearLayout).showImage(this);
    }

    public void showImageInfo() {
        CardManager.getCardManager(linearLayout).showImageInfo(this.imageButton.getId());
    }
}
