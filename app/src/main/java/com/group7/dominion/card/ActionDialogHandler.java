package com.group7.dominion.card;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.floriankleewein.commonclasses.cards.ActionType;
import com.floriankleewein.commonclasses.network.ClientConnector;
import com.floriankleewein.commonclasses.network.messages.gamelogicmsg.BuyCardMsg;
import com.group7.dominion.R;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;


public class ActionDialogHandler extends AppCompatDialogFragment {

    private int drawableDialog;
    private ActionType actionType;
    private FragmentManager fragmentManager;
    private ClientConnector clientConnector;

    //Pop-up Info Image Buttons
    private ImageButton buttonHexe;
    private ImageButton buttonBurggraben;
    private ImageButton buttonDorf;
    private ImageButton buttonHolzfaeller;
    private ImageButton buttonKeller;
    private ImageButton buttonMarkt;
    private ImageButton buttonMiliz;
    private ImageButton buttonMine;
    private ImageButton buttonSchmiede;
    private ImageButton buttonWerkstatt;

    private static final String ACTION_CONST = "Action";
    private static final String ACTIONTYPE_CONST = "ActionType: ";
    private static final String CARDCOUNT_CONST = ", Card Count: ";
    private static final String MONEYVALUE_CONST = ", Money Value: " ;
    private static final String ACTIONCOUNT_CONST = ", Action Count: ";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Buy Action Card")
                .setMessage("Do you want to buy this card?")
                .setPositiveButton("Buy", (dialog, which) -> {
                    // Buy the Card
                    //Card card = this.board.getActionField().pickCard(actionType);
                    BuyCardMsg buyCardMsg =new BuyCardMsg();
                    buyCardMsg.setActionTypeClicked(actionType);
                    sendUpdate(buyCardMsg);
/*
                    clientConnector.registerCallback(BuyCardMsg.class, (msg -> {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BuyCardMsg buyCardMsg1= (BuyCardMsg) msg;
                                Card card = buyCardMsg1.getBoughtCard();
                                if(card == null) {
                                    ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                                    errorDialogHandler.show(fragmentManager, "errorDialog");
                                } else {
                                    ActionCard actionCard = (ActionCard) card;
                                    switch (actionCard.getActionType()) {

                                        case HEXE:
                                            Log.i(actionConst, actiontypeConst + actionCard.getActionType() +
                                                    cardcountConst + actionCard.getAction().getCardCount() +
                                                    ", Curse Count: " + actionCard.getAction().getCurseCount());
                                            break;
                                        case WERKSTATT:
                                            Log.i(actionConst, actiontypeConst + actionCard.getActionType() +
                                                    cardcountConst + actionCard.getAction().getCardCount() +
                                                    ", Max Money Value: " + actionCard.getAction().getMaxMoneyValue());
                                            break;
                                        case SCHMIEDE:
                                            Log.i(actionConst, actiontypeConst + actionCard.getActionType() +
                                                    cardcountConst + actionCard.getAction().getCardCount());
                                            break;
                                        case MINE:
                                            Log.i(actionConst, actiontypeConst + actionCard.getActionType() +
                                                    cardcountConst + actionCard.getAction().getCardCount() +
                                                    ", Take MoneyCard That Cost Three More Than Old: " + actionCard.getAction().isTakeMoneyCardThatCostThreeMoreThanOld() +
                                                    ", Take Card On Hand: " + actionCard.getAction().isTakeCardOnHand());
                                            break;
                                        case MILIZ:
                                            Log.i(actionConst, actiontypeConst + actionCard.getActionType() +
                                                    moneyvalueConst + actionCard.getAction().getMoneyValue() +
                                                    ", Throw Every UserCards Until Three Left: " + actionCard.getAction().isThrowEveryUserCardsUntilThreeLeft());
                                            break;
                                        case MARKT:
                                            Log.i(actionConst, actiontypeConst + actionCard.getActionType() +
                                                    cardcountConst + actionCard.getAction().getCardCount() +
                                                    actioncountConst + actionCard.getAction().getActionCount() +
                                                    moneyvalueConst + actionCard.getAction().getMoneyValue() +
                                                    ", Buy Count: " + actionCard.getAction().getBuyCount());
                                            break;
                                        case KELLER:
                                            Log.i(actionConst, actiontypeConst + actionCard.getActionType() +
                                                    actioncountConst + actionCard.getAction().getActionCount() +
                                                    ", Throw Any Amount Cards: " + actionCard.getAction().isThrowAnyAmountCards());
                                            break;
                                        case HOLZFAELLER:
                                            Log.i(actionConst, actiontypeConst + actionCard.getActionType() +
                                                    ", Buy Count: " + actionCard.getAction().getBuyCount() +
                                                    moneyvalueConst + actionCard.getAction().getMoneyValue());
                                            break;
                                        case DORF:
                                            Log.i(actionConst, actiontypeConst + actionCard.getActionType() +
                                                    cardcountConst + actionCard.getAction().getCardCount() +
                                                    actioncountConst + actionCard.getAction().getActionCount());
                                            break;
                                        case BURGGRABEN:
                                            Log.i(actionConst, actiontypeConst + actionCard.getActionType() +
                                                    cardcountConst + actionCard.getAction().getCardCount() +
                                                    ", Throw Every UserCards Until Three Left: " + actionCard.getAction().isThrowEveryUserCardsUntilThreeLeft());
                                            break;
                                        default:
                                            Log.e("ERROR", "critical error @ actionDialogHandler. I am the default case!");
                                    }
                                }
                            }
                        });
                    })); */
                })
                .setNegativeButton("Close", (dialog, which) -> {
                    dialog.cancel();
                });

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

        View view = layoutInflater.inflate(drawableDialog, null);

        builder.setView(view);
        return builder.create();
    }

    public void init(Activity activity, FragmentManager fragmentManager) {
        // Hexe
        buttonHexe = activity.findViewById(R.id.btn_hexe);
        buttonHexe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHexe(fragmentManager);
            }
        });

        // Burggraben
        buttonBurggraben = activity.findViewById(R.id.btn_burggraben);
        buttonBurggraben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBurggraben(fragmentManager);
            }
        });

        //Dorf
        buttonDorf = activity.findViewById(R.id.btn_dorf);
        buttonDorf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDorf(fragmentManager);
            }
        });
        //Holzfaeller
        buttonHolzfaeller = activity.findViewById(R.id.btn_holzfaeller);
        buttonHolzfaeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHolzfaeller(fragmentManager);
            }
        });
        //Keller
        buttonKeller = activity.findViewById(R.id.btn_keller);
        buttonKeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickKeller(fragmentManager);
            }
        });

        //Markt
        buttonMarkt = activity.findViewById(R.id.btn_markt);
        buttonMarkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMarkt(fragmentManager);
            }
        });

        //Miliz
        buttonMiliz = activity.findViewById(R.id.btn_miliz);
        buttonMiliz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMiliz(fragmentManager);
            }
        });
        //Mine
        buttonMine = activity.findViewById(R.id.btn_mine);
        buttonMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMine(fragmentManager);
            }
        });

        //Schmiede
        buttonSchmiede = activity.findViewById(R.id.btn_schmiede);
        buttonSchmiede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSchmiede(fragmentManager);
            }
        });
        //Werkstatt
        buttonWerkstatt = activity.findViewById(R.id.btn_werkstatt);
        buttonWerkstatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickWerkstatt(fragmentManager);
            }
        });
    }

    private void onClickHexe(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.actionType = ActionType.HEXE;
        this.drawableDialog = R.layout.hexe_info_dialog;
        this.show(fragmentManager, "hexeDialog");
    }

    private void onClickHolzfaeller(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.actionType = ActionType.HOLZFAELLER;
        this.drawableDialog = R.layout.holzfaeller_info_dialog;
        this.show(fragmentManager, "holzfaellerDialog");
    }

    private void onClickBurggraben(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.actionType = ActionType.BURGGRABEN;
        this.drawableDialog = R.layout.burggraben_info_dialog;
        this.show(fragmentManager, "burggrabenDialog");
    }

    private void onClickDorf(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.actionType = ActionType.DORF;
        this.drawableDialog = R.layout.dorf_info_dialog;
        this.show(fragmentManager, "dorfDialog");
    }

    private void onClickKeller(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.actionType = ActionType.KELLER;
        this.drawableDialog = R.layout.keller_info_dialog;
        this.show(fragmentManager, "kellerDialog");
    }

    private void onClickMarkt(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.actionType = ActionType.MARKT;
        this.drawableDialog = R.layout.markt_info_dialog;
        this.show(fragmentManager, "marktDialog");
    }

    private void onClickMiliz(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.actionType = ActionType.MILIZ;
        this.drawableDialog = R.layout.miliz_info_dialog;
        this.show(fragmentManager, "milizDialog");
    }

    private void onClickMine(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.actionType = ActionType.MINE;
        this.drawableDialog = R.layout.mine_info_dialog;
        this.show(fragmentManager, "mineDialog");
    }
    private void onClickSchmiede(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.actionType = ActionType.SCHMIEDE;
        this.drawableDialog = R.layout.schmiede_info_dialog;
        this.show(fragmentManager, "schmiedeDialog");
    }
    private void onClickWerkstatt(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.actionType = ActionType.WERKSTATT;
        this.drawableDialog = R.layout.werkstatt_info_dialog;
        this.show(fragmentManager, "werkstattDialog");
    }

    private void sendUpdate(BuyCardMsg buyCardMsg){
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                clientConnector.sendbuyCard(buyCardMsg);
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