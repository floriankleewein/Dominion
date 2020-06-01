package com.group7.dominion.Card;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Cards.EstateCard;
import com.floriankleewein.commonclasses.Cards.EstateType;
import com.floriankleewein.commonclasses.Cards.MoneyCard;
import com.floriankleewein.commonclasses.Cards.MoneyType;
import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.floriankleewein.commonclasses.Network.Messages.GameLogicMsg.BuyCardMsg;
import com.group7.dominion.R;

import androidx.fragment.app.FragmentManager;

public class ImageButtonHandler {
    //private Board board;
    private ClientConnector clientConnector;

    private ImageButton buttonGold;
    private ImageButton buttonSilber;
    private ImageButton buttonKupfer;
    private ImageButton buttonProvinz;
    private ImageButton buttonAnwesen;
    private ImageButton buttonHerzogturm;
    private ImageButton buttonFluch;

    private static final String errorDialogConst = "errorDialog";
    private static final String moneyCardConst = "MoneyCard";
    private static final String moneyTypeConst = "MoneyType: ";
    private static final String estateCardConst = "EstateCard";
    private static final String estateTypeConst = "EstateType: ";

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
        //Card card = board.getBuyField().pickCard(MoneyType.GOLD);
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
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, errorDialogConst);
                    } else {
                        MoneyCard moneyCard = (MoneyCard) card;
                        Log.i(moneyCardConst, moneyTypeConst + moneyCard.getMoneyType());
                    }
                }
            });
        }));
    }

    private void onClickSilber(Activity activity, FragmentManager fragmentManager) {
        //Card card = board.getBuyField().pickCard(MoneyType.SILBER);
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
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, errorDialogConst);
                    } else {
                        MoneyCard moneyCard = (MoneyCard) card;
                        Log.i(moneyCardConst, moneyTypeConst + moneyCard.getMoneyType());
                    }
                }
            });
        }));

    }

    private void onClickKupfer(Activity activity, FragmentManager fragmentManager) {
        //Card card = board.getBuyField().pickCard(MoneyType.KUPFER);
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setMoneyTypeClicked(MoneyType.KUPFER);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if (card == null) {
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, errorDialogConst);
                    } else {
                        MoneyCard moneyCard = (MoneyCard) card;
                        Log.i(moneyCardConst, moneyTypeConst + moneyCard.getMoneyType());
                    }
                }
            });
        }));
    }

    private void onClickAnwesen(Activity activity, FragmentManager fragmentManager) {
        //Card card = board.getBuyField().pickCard(EstateType.ANWESEN);
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setEstateTypeClicked(EstateType.ANWESEN);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if (card == null) {
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, errorDialogConst);
                    } else {
                        EstateCard estateCard = (EstateCard) card;
                        Log.i(estateCardConst, estateTypeConst + estateCard.getEstateType());
                    }
                }
            });
        }));
    }

    private void onClickProvinz(Activity activity, FragmentManager fragmentManager) {
        //Card card = board.getBuyField().pickCard(EstateType.PROVINZ);
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setEstateTypeClicked(EstateType.PROVINZ);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if (card == null) {
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, errorDialogConst);
                    } else {
                        EstateCard estateCard = (EstateCard) card;
                        Log.i(estateCardConst, estateTypeConst + estateCard.getEstateType());
                    }
                }
            });
        }));
    }

    private void onClickHerzogturm(Activity activity, FragmentManager fragmentManager) {
        //Card card = board.getBuyField().pickCard(EstateType.HERZOGTUM);
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
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, errorDialogConst);
                    } else {
                        EstateCard estateCard = (EstateCard) card;
                        Log.i(estateCardConst, estateTypeConst + estateCard.getEstateType());
                    }
                }
            });
        }));
    }

    private void onClickFluch(Activity activity, FragmentManager fragmentManager) {
        //Card card = board.getBuyField().pickCard(EstateType.FLUCH);
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
                        ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                        errorDialogHandler.show(fragmentManager, errorDialogConst);
                    } else {
                        EstateCard estateCard = (EstateCard) card;
                        Log.i(estateCardConst, estateTypeConst + estateCard.getEstateType());
                    }
                }
            });
        }));
    }

    private void sendUpdate(BuyCardMsg msg) {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                clientConnector.sendbuyCard(msg);
            }
        });
        th.start();
    }

    /*
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    */

    public ClientConnector getClientConnector() {
        return clientConnector;
    }

    public void setClientConnector(ClientConnector clientConnector) {
        this.clientConnector = clientConnector;
    }
}
