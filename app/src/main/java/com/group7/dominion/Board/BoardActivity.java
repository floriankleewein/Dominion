package com.group7.dominion.Board;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.cardemulation.CardEmulation;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.Cards.ActionType;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Cards.EstateCard;
import com.floriankleewein.commonclasses.Cards.EstateType;
import com.floriankleewein.commonclasses.Cards.MoneyCard;
import com.floriankleewein.commonclasses.Cards.MoneyType;
import com.group7.dominion.Card.CardManager;
import com.group7.dominion.Card.CardUI;
import com.group7.dominion.R;

public class BoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        LinearLayout linearLayout = findViewById(R.id.rootContainer);

        // Kartentypen von server
        Card actionCard = new ActionCard(1, ActionType.BURGGRABEN);
        Card estateCard = new EstateCard(1,1, EstateType.PROVINZ);
        Card moneyCard = new MoneyCard(1, 1, MoneyType.GOLD);
        //ToDo: get all cards from server

        // UI Karten Darstellung
        CardUI cardUI = new CardUI(1, actionCard, this, linearLayout, 0.5f);
        cardUI.init();
        cardUI.showImage();

        CardUI cardUI1 = new CardUI(2, estateCard, this, linearLayout, 0.5f);
        cardUI1.init();
        cardUI1.showImage();

        CardUI cardUI2 = new CardUI(3, moneyCard, this, linearLayout, 0.5f);
        cardUI2.init();
        cardUI2.showImage();
    }
}
