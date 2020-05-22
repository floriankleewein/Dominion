package com.group7.dominion.Card;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Cards.EstateCard;
import com.floriankleewein.commonclasses.Cards.EstateType;
import com.floriankleewein.commonclasses.Cards.MoneyCard;
import com.floriankleewein.commonclasses.Cards.MoneyType;
import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.floriankleewein.commonclasses.User.User;
import com.group7.dominion.DominionActivity;
import com.group7.dominion.R;

import java.util.LinkedList;
import java.util.List;

import static com.floriankleewein.commonclasses.Cards.ActionType.HEXE;


public class HandCardsHandler {
    User user;
    LinearLayout linearLayout;
    LinearLayout.LayoutParams lparams;
    private List<ImageButton> ImageButtons;
    private List<Card> TestList;
    Context context;
    int img_id;

    public HandCardsHandler(Context context) {
        this.context = context;
        TestList = testList();
        linearLayout = ((DominionActivity) context).findViewById(R.id.LinearCards);
        lparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageButtons = new LinkedList<>();
        lparams.weight = 1;
    }

    public void initCards(String Username) {
        try {
            //List<Card> UserCards = findUserCards(Username);
            for (int i = 0; i < 7; i++) {
                addCard(TestList.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    List<Card> findUserCards(String Username) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                user = ClientConnector.getClientConnector().getGame().findUser(Username);
            }
        });
        thread.start();

        return user.getUserCards().getHandCards();

    }


    private int setRessource(Card card) {

        switch (card.getId()) {
            case 0:
                return R.drawable.burggraben_info;
            case 1:
                return R.drawable.dorf_info;
            case 2:
                return R.drawable.holzfaeller_info;
            case 3:
                return R.drawable.keller_info;
            case 4:
                return R.drawable.werkstatt_info;
            case 5:
                return R.drawable.schmiede_info;
            case 6:
                return R.drawable.markt_info;
            case 7:
                return R.drawable.hexe_info;
            case 8:
                return R.drawable.mine_info;
            case 10:
                return R.drawable.provinz;
            case 11:
                return R.drawable.herzogtum;
            case 12:
                return R.drawable.anwesen;
            case 13:
                return R.drawable.fluch;
            case 14:
                return R.drawable.kupfer;
            case 15:
                return R.drawable.silber;
            case 16:
                return R.drawable.gold;

        }
        return R.drawable.backofcard;
    }


    private List<Card> testList() {
        /**
         * Test list for UserCards
         */
        int amountCards = 5;
        Card one = new ActionCard(2, HEXE);
        Card two = new EstateCard(3, 3, EstateType.PROVINZ);
        Card three = new MoneyCard(2, 3, MoneyType.GOLD);

        LinkedList<Card> TestList = new LinkedList<>();
        for (int i = 0; i < amountCards; i++) {
            switch (i) {
                case 0:
                    TestList.add(one);
                case 1:
                    TestList.add(two);
                case 2:
                    TestList.add(three);
                case 3:
                    TestList.add(new MoneyCard(3, 3, MoneyType.SILBER));
                case 4:
                    TestList.add(new EstateCard(3, 5, EstateType.ANWESEN));
                case 5:
                    TestList.add(new EstateCard(5, 3, EstateType.FLUCH));
            }

        }
        return TestList;
    }

    public void onClickListener() {
        for (int i = 0; i < ImageButtons.size(); i++) {
            int finalI = i;
            ImageButtons.get(i).setOnClickListener(v -> {
                System.out.println("Card with the ID is played: " + TestList.get(finalI).getId());
                ImageButtons.get(finalI).setVisibility(View.INVISIBLE);
            });
        }
    }

    private void addCard(Card card) {
        ImageButton umg = new ImageButton(context);
        umg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        umg.setId(img_id);
        umg.setLayoutParams(lparams);
        umg.setImageResource(setRessource(card));
        linearLayout.addView(umg);
        ImageButtons.add(umg);
        img_id++;
    }
    private void setImageButtonsNull () {
        ImageButtons.clear();
    }
}

