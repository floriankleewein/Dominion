package com.group7.dominion.card;


import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.network.ClientConnector;
import com.floriankleewein.commonclasses.network.PlayCardMsg;
import com.floriankleewein.commonclasses.user.User;
import com.group7.dominion.DominionActivity;
import com.group7.dominion.R;

import java.util.LinkedList;
import java.util.List;

/**@Author Maurer Florian
 * This class handles the hand cards. It's implemented with dynamic lists, because you can have a different amount of hand cards.
 * ImageButtonList stores the Image Buttons which are the cards in the UI.
 * The CardList stores the cards objects, which are necessary for sending to the server, who needs to know which card object is played.
 *
 */

public class HandCardsHandler {
    LinearLayout linearLayout;
    LinearLayout.LayoutParams lparams;
    private List<ImageButton> imageButtonList;
    private List<Card> cardList;
    Context context;
    boolean canGetCards;

    public HandCardsHandler(Context context) {
        this.context = context;
        linearLayout = ((DominionActivity) context).findViewById(R.id.LinearCards);
        lparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageButtonList = new LinkedList<>();
        lparams.weight = 1;
        canGetCards = true;
        cardList = new LinkedList<>();
    }

    public void initCards(User user) {
        if (canGetCards) {
            canGetCards = false;
            cardList = user.getUserCards().getHandCards();
            Log.i("intit", "INIT CARDS");
            for (int i = 0; i < cardList.size(); i++) {
                addCard(cardList.get(i));
            }
        }
    }

    public void sendMessage() {
        Thread thread = new Thread(() -> ClientConnector.getClientConnector().sendGameUpdate());
        thread.start();

    }

    /**
     * @param card
     * @return id from the drawable.
     * @Author Maurer Florian
     * This Methods gets the right picture id and returns the int value
     */

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
            default:
                return R.drawable.backofcard;
        }
    }

    /**
     * @Author Maurer Florian
     * set a click listener on the hand cards which are in the views.
     * in this on click listener method only cards where id is less than or equal 10. (Those are all Action Cards!!!)
     * If a card is clicked it will be removed from the View and the played card will be sended to the Server.
     */

    public void onClickListenerActionPhase() {
        for (int i = 0; i < imageButtonList.size(); i++) {
            int finalI = i;

            imageButtonList.get(i).setOnClickListener(v -> {
                if ((cardList.get(finalI).getId() <= 10)) {
                    linearLayout.removeView(imageButtonList.get(finalI));
                    Thread thread = new Thread(() -> {
                        PlayCardMsg msg = new PlayCardMsg();
                        msg.setPlayedCard(cardList.get(finalI));
                        ClientConnector.getClientConnector().sendPlayCard(msg);
                    });
                    thread.start();
                }
            });
        }
    }

    /**
     * @Author Maurer Florian
     * Similar to the method beyond. Different here is that you can only click the cards where the id is greater or equal 14. (Those are all Money Cards!!!)
     */

    public void onClickListenerBuyPhase() {
        for (int i = 0; i < imageButtonList.size(); i++) {
            int finalI = i;

            imageButtonList.get(i).setOnClickListener(v -> {
                if ((cardList.get(finalI).getId() >= 14)) {
                    linearLayout.removeView(imageButtonList.get(finalI));
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

    /**
     * @param card, the Card which is played
     * @Author Maurer Florian
     * Adds a card to the to the linearLayout and stores the view in the List, so it can be removed when it will be played
     */

    private void addCard(Card card) {
        ImageButton umg = new ImageButton(context);
        umg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        umg.setLayoutParams(lparams);
        umg.setImageResource(setRessource(card));
        linearLayout.addView(umg);
        imageButtonList.add(umg);
    }

    /**
     * @Author Maurer Florian
     * Sets back the linearLayout and clears the ImageButtonList.
     */
    public void setImageButtonsNull() {
        Log.i("SEt", "SET IMAGE BUTTON NULL IS CALLED ");
        linearLayout.removeAllViewsInLayout();
        imageButtonList.clear();
        canGetCards = true;
    }

}


