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
import com.floriankleewein.commonclasses.network.messages.gamelogicmsg.BuyCardMsg;
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

    private static final String ERRORDIALOG_CONST = "errorDialog";
    private static final String MONEYCARD_CONST = "MoneyCard";
    private static final String MONEYTYPE_CONST = "MoneyType: ";
    private static final String ESTATECARD_CONST = "EstateCard";
    private static final String ESTATETYPE_CONST = "EstateType: ";
    private static final String YOU_CANT_BUY_THIS_CARD = "Du kannst diese Karte nicht kaufen";

    public void init(Activity activity, FragmentManager fragmentManager) {
        //Gold
        buttonGold = activity.findViewById(R.id.btn_gold);
        buttonGold.setOnClickListener(view -> onClickGold(activity, fragmentManager));

        //Silber
        buttonSilber = activity.findViewById(R.id.btn_silber);
        buttonSilber.setOnClickListener(view -> onClickSilber(activity, fragmentManager));
        //Kupfer
        buttonKupfer = activity.findViewById(R.id.btn_kupfer);
        buttonKupfer.setOnClickListener(view -> onClickKupfer(activity, fragmentManager));

        //Provinz
        buttonProvinz = activity.findViewById(R.id.btn_provinz);
        buttonProvinz.setOnClickListener(view -> onClickProvinz(activity, fragmentManager));

        //Anwesen
        buttonAnwesen = activity.findViewById(R.id.btn_anwesen);
        buttonAnwesen.setOnClickListener(view -> onClickAnwesen(activity, fragmentManager));

        //Herzogturm
        buttonHerzogturm = activity.findViewById(R.id.btn_herzogturm);
        buttonHerzogturm.setOnClickListener(view -> onClickHerzogturm(activity, fragmentManager));

        //Fluch
        buttonFluch = activity.findViewById(R.id.btn_fluch);
        buttonFluch.setOnClickListener(view -> onClickFluch(activity, fragmentManager));
    }

    private void onClickGold(Activity activity, FragmentManager fragmentManager) {
        //Card card = board.getBuyField().pickCard(MoneyType.GOLD);
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setMoneyTypeClicked(MoneyType.GOLD);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> activity.runOnUiThread(() -> {
            BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
            Card card = gameUpdateMsg1.getBoughtCard();
            if (card == null) {
                /*
                ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);

                 */
                Toast.makeText(activity.getApplicationContext(), YOU_CANT_BUY_THIS_CARD, Toast.LENGTH_SHORT).show();
            } else if (card instanceof MoneyCard) {
                MoneyCard moneyCard = (MoneyCard) card;
                Log.i(MONEYCARD_CONST, MONEYTYPE_CONST + moneyCard.getMoneyType());
            }
        })));
    }

    private void onClickSilber(Activity activity, FragmentManager fragmentManager) {
        //Card card = board.getBuyField().pickCard(MoneyType.SILBER);
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setMoneyTypeClicked(MoneyType.SILBER);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> activity.runOnUiThread(() -> {
            BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
            Card card = gameUpdateMsg1.getBoughtCard();
            if (card == null) {
                /*
                ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);

                 */
                Toast.makeText(activity.getApplicationContext(), YOU_CANT_BUY_THIS_CARD, Toast.LENGTH_SHORT).show();
            } else if (card instanceof MoneyCard) {
                MoneyCard moneyCard = (MoneyCard) card;
                Log.i(MONEYCARD_CONST, MONEYTYPE_CONST + moneyCard.getMoneyType());
            }
        })));

    }

    private void onClickKupfer(Activity activity, FragmentManager fragmentManager) {
        //Card card = board.getBuyField().pickCard(MoneyType.KUPFER);
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setMoneyTypeClicked(MoneyType.KUPFER);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> activity.runOnUiThread(() -> {
            BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
            Card card = gameUpdateMsg1.getBoughtCard();
            if (card == null) {
                /*
                ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);

                 */
                Toast.makeText(activity.getApplicationContext(), YOU_CANT_BUY_THIS_CARD, Toast.LENGTH_SHORT).show();
            } else if (card instanceof MoneyCard) {
                MoneyCard moneyCard = (MoneyCard) card;
                Log.i(MONEYCARD_CONST, MONEYTYPE_CONST + moneyCard.getMoneyType());
            }
        })));
    }

    private void onClickAnwesen(Activity activity, FragmentManager fragmentManager) {
        //Card card = board.getBuyField().pickCard(EstateType.ANWESEN);
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setEstateTypeClicked(EstateType.ANWESEN);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> activity.runOnUiThread(() -> {
            BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
            Card card = gameUpdateMsg1.getBoughtCard();
            if (card == null) {
                /*
                ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);

                 */
                Toast.makeText(activity.getApplicationContext(), YOU_CANT_BUY_THIS_CARD, Toast.LENGTH_SHORT).show();
            } else if (card instanceof EstateCard) {
                EstateCard estateCard = (EstateCard) card;
                Log.i(ESTATECARD_CONST, ESTATETYPE_CONST + estateCard.getEstateType());
            }
        })));
    }

    private void onClickProvinz(Activity activity, FragmentManager fragmentManager) {
        //Card card = board.getBuyField().pickCard(EstateType.PROVINZ);
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setEstateTypeClicked(EstateType.PROVINZ);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> activity.runOnUiThread(() -> {
            BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
            Card card = gameUpdateMsg1.getBoughtCard();
            if (card == null) {
                /*
                ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);

                 */
                Toast.makeText(activity.getApplicationContext(), YOU_CANT_BUY_THIS_CARD, Toast.LENGTH_SHORT).show();
            } else if (card instanceof EstateCard) {
                EstateCard estateCard = (EstateCard) card;
                Log.i(ESTATECARD_CONST, ESTATETYPE_CONST + estateCard.getEstateType());
            }
        })));
    }

    private void onClickHerzogturm(Activity activity, FragmentManager fragmentManager) {
        //Card card = board.getBuyField().pickCard(EstateType.HERZOGTUM);
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setEstateTypeClicked(EstateType.HERZOGTUM);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> activity.runOnUiThread(() -> {
            BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
            Card card = gameUpdateMsg1.getBoughtCard();
            if (card == null) {
                /*
                ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);

                 */
                Toast.makeText(activity.getApplicationContext(), YOU_CANT_BUY_THIS_CARD, Toast.LENGTH_SHORT).show();
            } else if (card instanceof EstateCard) {
                EstateCard estateCard = (EstateCard) card;
                Log.i(ESTATECARD_CONST, ESTATETYPE_CONST + estateCard.getEstateType());

            }
        })));
    }

    private void onClickFluch(Activity activity, FragmentManager fragmentManager) {
        //Card card = board.getBuyField().pickCard(EstateType.FLUCH);
        BuyCardMsg gameUpdateMsg = new BuyCardMsg();
        gameUpdateMsg.setEstateTypeClicked(EstateType.FLUCH);
        sendUpdate(gameUpdateMsg);

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> activity.runOnUiThread(() -> {
            BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
            Card card = gameUpdateMsg1.getBoughtCard();
            if (card == null) {
                /*
                ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);

                 */
                Toast.makeText(activity.getApplicationContext(), YOU_CANT_BUY_THIS_CARD, Toast.LENGTH_SHORT).show();
            } else if (card instanceof EstateCard) {
                EstateCard estateCard = (EstateCard) card;
                Log.i(ESTATECARD_CONST, ESTATETYPE_CONST + estateCard.getEstateType());
            }
        })));
    }

    private void sendUpdate(BuyCardMsg msg) {
        Thread th = new Thread(() -> clientConnector.sendbuyCard(msg));
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
