package com.group7.dominion.card;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.cards.EstateCard;
import com.floriankleewein.commonclasses.cards.EstateType;
import com.floriankleewein.commonclasses.cards.MoneyCard;
import com.floriankleewein.commonclasses.cards.MoneyType;
import com.floriankleewein.commonclasses.network.ClientConnector;
import com.floriankleewein.commonclasses.network.BuyCardMsg;
import com.group7.dominion.R;

import androidx.fragment.app.FragmentManager;

public class ImageButtonHandler {
    private ClientConnector clientConnector;

    private static final String ERRORDIALOG_CONST = "errorDialog";
    private static final String MONEYCARD_CONST = "MoneyCard";
    private static final String MONEYTYPE_CONST = "MoneyType: ";
    private static final String ESTATECARD_CONST = "EstateCard";
    private static final String ESTATETYPE_CONST = "EstateType: ";
    private static final String CANT_BUYCARD_CONST = "Du kannst diese Karte nicht kaufen";

    /**
     * LKDoc:   setOnClickListener für die einzelnen Aktionskarten
     * @param activity
     * @param fragmentManager wird benötigt wegen dem ErrorDialogHandler
     */
    public void init(Activity activity, FragmentManager fragmentManager) {
        //Gold
        ImageButton buttonGold;
        buttonGold = activity.findViewById(R.id.btn_gold);
        buttonGold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGold(activity, fragmentManager);
            }
        });

        //Silber
        ImageButton buttonSilber;
        buttonSilber = activity.findViewById(R.id.btn_silber);
        buttonSilber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSilber(activity, fragmentManager);
            }
        });

        //Kupfer
        ImageButton buttonKupfer;
        buttonKupfer = activity.findViewById(R.id.btn_kupfer);
        buttonKupfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickKupfer(activity, fragmentManager);
            }
        });

        //Provinz
        ImageButton buttonProvinz;
        buttonProvinz = activity.findViewById(R.id.btn_provinz);
        buttonProvinz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickProvinz(activity, fragmentManager);
            }
        });

        //Anwesen
        ImageButton buttonAnwesen;
        buttonAnwesen = activity.findViewById(R.id.btn_anwesen);
        buttonAnwesen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAnwesen(activity, fragmentManager);
            }
        });

        //Herzogturm
        ImageButton buttonHerzogturm;
        buttonHerzogturm = activity.findViewById(R.id.btn_herzogturm);
        buttonHerzogturm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHerzogturm(activity, fragmentManager);
            }
        });

        //Fluch
        ImageButton buttonFluch;
        buttonFluch = activity.findViewById(R.id.btn_fluch);
        buttonFluch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFluch(activity, fragmentManager);
            }
        });
    }

    /**
     * LKDoc:   onClick Methoden für die Geld-, Fluch-, & Provinzkarten
     *          wie im ActionDialogHandler:
     *              1) zuerst wird überprüft ob die Karte gekauft werden kann (genug Geld) = Toast
     *              2) ist die Karte zusätzlich null bedeutet dies es sind keine mehr im Stapel und der ErrorDialogHandler wird aufgerufen (braucht Fragment)
     *              3) ist alles ok kann die Karte gekauft werden
     * @param activity
     * @param fragmentManager
     */
    private void onClickGold(Activity activity, FragmentManager fragmentManager) {
        //LKDoc: Karte kaufen
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setMoneyTypeClicked(MoneyType.GOLD);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if (card == null) {
                        Toast.makeText(activity.getApplicationContext(), CANT_BUYCARD_CONST, Toast.LENGTH_SHORT).show();
                    } else if (card == null && gameUpdateMsg1.isCantBuyCard()) {
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);
                    } else if (card instanceof MoneyCard) {
                        MoneyCard moneyCard = (MoneyCard) card;
                        Log.i(MONEYCARD_CONST, MONEYTYPE_CONST + moneyCard.getMoneyType());
                    }
                }
            });
        }));
    }

    private void onClickSilber(Activity activity, FragmentManager fragmentManager) {
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setMoneyTypeClicked(MoneyType.SILBER);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if (card == null) {
                        Toast.makeText(activity.getApplicationContext(), CANT_BUYCARD_CONST, Toast.LENGTH_SHORT).show();
                    } else if (card == null && gameUpdateMsg1.isCantBuyCard()) {
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);
                    } else if (card instanceof MoneyCard) {
                        MoneyCard moneyCard = (MoneyCard) card;
                        Log.i(MONEYCARD_CONST, MONEYTYPE_CONST + moneyCard.getMoneyType());
                    }
                }
            });
        }));

    }

    private void onClickKupfer(Activity activity, FragmentManager fragmentManager) {
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setMoneyTypeClicked(MoneyType.KUPFER);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if (card == null && !gameUpdateMsg1.isCantBuyCard()) {
                        Toast.makeText(activity.getApplicationContext(), CANT_BUYCARD_CONST, Toast.LENGTH_SHORT).show();
                    } else if (card == null && gameUpdateMsg1.isCantBuyCard()) {
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);
                    } else if (card instanceof MoneyCard) {
                        MoneyCard moneyCard = (MoneyCard) card;
                        Log.i(MONEYCARD_CONST, MONEYTYPE_CONST + moneyCard.getMoneyType());
                    }
                }
            });
        }));
    }

    private void onClickAnwesen(Activity activity, FragmentManager fragmentManager) {
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setEstateTypeClicked(EstateType.ANWESEN);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if (card == null && !gameUpdateMsg1.isCantBuyCard()) {
                        Toast.makeText(activity.getApplicationContext(), CANT_BUYCARD_CONST, Toast.LENGTH_SHORT).show();
                    } else if (card == null && gameUpdateMsg1.isCantBuyCard()) {
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);
                    } else if (card instanceof EstateCard) {
                        EstateCard estateCard = (EstateCard) card;
                        Log.i(ESTATECARD_CONST, ESTATETYPE_CONST + estateCard.getEstateType());
                    }
                }
            });
        }));
    }

    private void onClickProvinz(Activity activity, FragmentManager fragmentManager) {
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setEstateTypeClicked(EstateType.PROVINZ);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if (card == null && !gameUpdateMsg1.isCantBuyCard()) {
                        Toast.makeText(activity.getApplicationContext(), CANT_BUYCARD_CONST, Toast.LENGTH_SHORT).show();
                    } else if (card == null && gameUpdateMsg1.isCantBuyCard()) {
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);
                    } else if (card instanceof EstateCard) {
                        EstateCard estateCard = (EstateCard) card;
                        Log.i(ESTATECARD_CONST, ESTATETYPE_CONST + estateCard.getEstateType());
                    }
                }
            });
        }));
    }

    private void onClickHerzogturm(Activity activity, FragmentManager fragmentManager) {
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setEstateTypeClicked(EstateType.HERZOGTUM);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if (card == null) {
                        Toast.makeText(activity.getApplicationContext(), CANT_BUYCARD_CONST, Toast.LENGTH_SHORT).show();
                    } else if (card == null && gameUpdateMsg1.isCantBuyCard()) {
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);
                    } else if (card instanceof EstateCard) {
                        EstateCard estateCard = (EstateCard) card;
                        Log.i(ESTATECARD_CONST, ESTATETYPE_CONST + estateCard.getEstateType());

                    }
                }
            });
        }));
    }

    private void onClickFluch(Activity activity, FragmentManager fragmentManager) {
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setEstateTypeClicked(EstateType.FLUCH);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if (card == null) {
                        Toast.makeText(activity.getApplicationContext(), CANT_BUYCARD_CONST, Toast.LENGTH_SHORT).show();
                    } else if (card == null && gameUpdateMsg1.isCantBuyCard()) {
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);
                    } else if (card instanceof EstateCard) {
                        EstateCard estateCard = (EstateCard) card;
                        Log.i(ESTATECARD_CONST, ESTATETYPE_CONST + estateCard.getEstateType());
                    }
                }
            });
        }));
    }

    /**
     * LKDoc: sende Update der msg an clientConnector --> ClientConnector.class
     * @param msg
     */
    private void sendUpdate(BuyCardMsg msg) {
        //LKDoc: Sonarcloud wanted a lambda
        Thread th = new Thread(() -> clientConnector.sendbuyCard(msg));
        th.start();
    }

    public ClientConnector getClientConnector() {
        return clientConnector;
    }

    public void setClientConnector(ClientConnector clientConnector) {
        this.clientConnector = clientConnector;
    }
}
