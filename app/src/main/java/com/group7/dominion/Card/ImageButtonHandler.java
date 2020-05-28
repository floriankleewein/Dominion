package com.group7.dominion.Card;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Cards.EstateCard;
import com.floriankleewein.commonclasses.Cards.EstateType;
import com.floriankleewein.commonclasses.Cards.MoneyCard;
import com.floriankleewein.commonclasses.Cards.MoneyType;
import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.floriankleewein.commonclasses.Network.GameUpdateMsg;
import com.group7.dominion.R;

import androidx.fragment.app.FragmentManager;

public class ImageButtonHandler {
    private ClientConnector clientConnector;

    private ImageButton buttonGold;
    private ImageButton buttonSilber;
    private ImageButton buttonKupfer;
    private ImageButton buttonProvinz;
    private ImageButton buttonAnwesen;
    private ImageButton buttonHerzogturm;
    private ImageButton buttonFluch;

    public void init(Activity activity, FragmentManager fragmentManager) {
        //Gold
        buttonGold = activity.findViewById(R.id.btn_gold);
        buttonGold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGold(activity, fragmentManager);
            }
        });

        //Silber
        buttonSilber = activity.findViewById(R.id.btn_silber);
        buttonSilber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSilber(activity, fragmentManager);
            }
        });
        //Kupfer
        buttonKupfer = activity.findViewById(R.id.btn_kupfer);
        buttonKupfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickKupfer(activity, fragmentManager);
            }
        });

        //Provinz
        buttonProvinz = activity.findViewById(R.id.btn_provinz);
        buttonProvinz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickProvinz(activity, fragmentManager);
            }
        });

        //Anwesen
        buttonAnwesen = activity.findViewById(R.id.btn_anwesen);
        buttonAnwesen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAnwesen(activity, fragmentManager);
            }
        });

        //Herzogturm
        buttonHerzogturm = activity.findViewById(R.id.btn_herzogturm);
        buttonHerzogturm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHerzogturm(activity, fragmentManager);
            }
        });

        //Fluch
        buttonFluch = activity.findViewById(R.id.btn_fluch);
        buttonFluch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFluch(activity, fragmentManager);
            }
        });
    }

    private void onClickGold(Activity activity, FragmentManager fragmentManager) {
        GameUpdateMsg gameUpdateMsg = new GameUpdateMsg();
        gameUpdateMsg.setMoneyTypeClicked(MoneyType.GOLD);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(GameUpdateMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GameUpdateMsg gameUpdateMsg1 = (GameUpdateMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if(card == null){
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, "errorDialog");
                    } else {
                        MoneyCard moneyCard = (MoneyCard) card;
                        Log.i("MoneyCard", "MoneyType: " + moneyCard.getMoneyType());
                    }
                }
            });
        }));
    }

    private void onClickSilber(Activity activity, FragmentManager fragmentManager) {
        GameUpdateMsg gameUpdateMsg = new GameUpdateMsg();
        gameUpdateMsg.setMoneyTypeClicked(MoneyType.SILBER);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(GameUpdateMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GameUpdateMsg gameUpdateMsg1 = (GameUpdateMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if(card == null){
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, "errorDialog");
                    } else {
                        MoneyCard moneyCard = (MoneyCard) card;
                        Log.i("MoneyCard", "MoneyType: " + moneyCard.getMoneyType());
                    }
                }
            });
        }));

    }

    private void onClickKupfer(Activity activity, FragmentManager fragmentManager) {
        GameUpdateMsg gameUpdateMsg = new GameUpdateMsg();
        gameUpdateMsg.setMoneyTypeClicked(MoneyType.KUPFER);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(GameUpdateMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GameUpdateMsg gameUpdateMsg1 = (GameUpdateMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if(card == null){
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, "errorDialog");
                    } else {
                        MoneyCard moneyCard = (MoneyCard) card;
                        Log.i("MoneyCard", "MoneyType: " + moneyCard.getMoneyType());
                    }
                }
            });
        }));
    }

    private void onClickAnwesen(Activity activity, FragmentManager fragmentManager) {
        GameUpdateMsg gameUpdateMsg = new GameUpdateMsg();
        gameUpdateMsg.setEstateTypeClicked(EstateType.ANWESEN);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(GameUpdateMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GameUpdateMsg gameUpdateMsg1 = (GameUpdateMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if(card == null){
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, "errorDialog");
                    } else {
                        EstateCard estateCard = (EstateCard) card;
                        Log.i("EstateCard", "EstateType: " + estateCard.getEstateType());
                    }
                }
            });
        }));
    }

    private void onClickProvinz(Activity activity, FragmentManager fragmentManager) {
        GameUpdateMsg gameUpdateMsg = new GameUpdateMsg();
        gameUpdateMsg.setEstateTypeClicked(EstateType.PROVINZ);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(GameUpdateMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GameUpdateMsg gameUpdateMsg1 = (GameUpdateMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if(card == null){
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, "errorDialog");
                    } else {
                        EstateCard estateCard = (EstateCard) card;
                        Log.i("EstateCard", "EstateType: " + estateCard.getEstateType());
                    }
                }
            });
        }));
    }

    private void onClickHerzogturm(Activity activity, FragmentManager fragmentManager) {
        GameUpdateMsg gameUpdateMsg = new GameUpdateMsg();
        gameUpdateMsg.setEstateTypeClicked(EstateType.HERZOGTUM);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(GameUpdateMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GameUpdateMsg gameUpdateMsg1 = (GameUpdateMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if(card == null){
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, "errorDialog");
                    } else {
                        EstateCard estateCard = (EstateCard) card;
                        Log.i("EstateCard", "EstateType: " + estateCard.getEstateType());
                    }
                }
            });
        }));
    }

    private void onClickFluch(Activity activity, FragmentManager fragmentManager) {
        GameUpdateMsg gameUpdateMsg = new GameUpdateMsg();
        gameUpdateMsg.setEstateTypeClicked(EstateType.FLUCH);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(GameUpdateMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GameUpdateMsg gameUpdateMsg1 = (GameUpdateMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if(card == null){
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, "errorDialog");
                    } else {
                        EstateCard estateCard = (EstateCard) card;
                        Log.i("EstateCard", "EstateType: " + estateCard.getEstateType());
                    }
                }
            });
        }));
    }

    private void sendUpdate(GameUpdateMsg gameUpdateMsg) {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                clientConnector.sendUpdate(gameUpdateMsg);
            }
        });
        th.start();
    }

    public ClientConnector getClientConnector() {
        return clientConnector;
    }

    public void setClientConnector(ClientConnector clientConnector) {
        this.clientConnector = clientConnector;
    }
}
