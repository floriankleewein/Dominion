package com.group7.dominion.card;


import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.gamelogic.PlayStatus;
import com.floriankleewein.commonclasses.network.ClientConnector;
import com.floriankleewein.commonclasses.network.messages.gamelogicmsg.PlayCardMsg;
import com.floriankleewein.commonclasses.network.StartGameMsg;
import com.floriankleewein.commonclasses.user.User;
import com.group7.dominion.DominionActivity;
import com.group7.dominion.R;

import java.util.LinkedList;
import java.util.List;


public class HandCardsHandler {
    PlayStatus playStatus;
    LinearLayout linearLayout;
    LinearLayout.LayoutParams lparams;
    private List<ImageButton> ImageButtons;
    private List<Card> cardList;
    Context context;
    int img_id;
    boolean canGetCards;

    public HandCardsHandler(Context context) {
        this.context = context;
        linearLayout = ((DominionActivity) context).findViewById(R.id.LinearCards);
        lparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageButtons = new LinkedList<>();
        lparams.weight = 1;
        playStatus = PlayStatus.NO_PLAY_PHASE;
        canGetCards = true;
        cardList = new LinkedList<>();
    }

    public void initCards(User user) {
        if (canGetCards) {
            canGetCards = false;
            System.out.println(user.getUserCards().getHandCards().size() + "in initcards");
            cardList = user.getUserCards().getHandCards();
            System.out.println(cardList.size());
            Log.i("intit", "INIT CARDS");
            for (int i = 0; i < cardList.size(); i++) {
                addCard(cardList.get(i));
            }
        }
    }

    public void sendMessage() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ClientConnector.getClientConnector().sendGameUpdate();
            }
        });
        thread.start();

    }

    private int setRessource(Card card) {
        switch (card.getId()) {
            default:
                return R.drawable.backofcard;
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
            case 9:
                return R.drawable.miliz_info;
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
    }


    public void onClickListenerActionPhase() {
        for (int i = 0; i < ImageButtons.size(); i++) {
            int finalI = i;

            ImageButtons.get(i).setOnClickListener(v -> {
                if ((cardList.get(finalI).getId() <= 10)) {
                    System.out.println("Card with the ID is played: " + cardList.get(finalI).getId());
                    linearLayout.removeView(ImageButtons.get(finalI));
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            PlayCardMsg msg = new PlayCardMsg();
                            msg.setPlayedCard(cardList.get(finalI));
                            ClientConnector.getClientConnector().sendPlayCard(msg);
                        }
                    });
                    thread.start();
                }
            });
        }
    }

    public void onClickListenerBuyPhase() {
        for (int i = 0; i < ImageButtons.size(); i++) {
            int finalI = i;

            ImageButtons.get(i).setOnClickListener(v -> {
                if ((cardList.get(finalI).getId() >= 14)) {
                    System.out.println("Card with the ID is played: " + cardList.get(finalI).getId());
                    linearLayout.removeView(ImageButtons.get(finalI));
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            PlayCardMsg msg = new PlayCardMsg();
                            msg.setPlayedCard(cardList.get(finalI));
                            ClientConnector.getClientConnector().sendPlayCard(msg);
                        }
                    });
                    thread.start();
                }
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

    public void setImageButtonsNull() {
        Log.i("SEt", "SET IMAGE BUTTON NULL IS CALLED ");
        linearLayout.removeAllViewsInLayout();
        ImageButtons.clear();
        canGetCards = true;
    }


    public void registerListener(String Username) {
        ClientConnector.getClientConnector().registerCallback(StartGameMsg.class, msg -> {
            ((DominionActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Got the Callback");
                }
            });
        });
    }

}


