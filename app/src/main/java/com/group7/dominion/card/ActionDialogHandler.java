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
import com.floriankleewein.commonclasses.network.BuyCardMsg;
import com.group7.dominion.R;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;


public class ActionDialogHandler extends AppCompatDialogFragment {

    private int drawableDialog;
    private ActionType actionType;
    private FragmentManager fragmentManager;
    private ClientConnector clientConnector;

    private static final String ACTION_CONST = "Action";
    private static final String ACTIONTYPE_CONST = "ActionType: ";
    private static final String CARDCOUNT_CONST = ", Card Count: ";
    private static final String MONEYVALUE_CONST = ", Money Value: " ;
    private static final String ACTIONCOUNT_CONST = ", Action Count: ";

    /**
     * LKDoc:   AlertDialogBuilder für die Info-Karte die dem Spieler mehr Informationen über die Aktionskarte
     *          liefern soll. Der Spieler kann anschließend die Karte kaufen ("Buy") oder aber das Fenster wieder schließen ("Close")
     *          und eine andere Karte wählen
     * @param savedInstanceState
     * @return builder (es wird alles wieder zum Builder returnt)
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Buy Action Card")
                .setMessage("Do you want to buy this card?")
                .setPositiveButton("Buy", (dialog, which) -> {
                    //LKDoc: Karte kaufen
                    BuyCardMsg buyCardMsg =new BuyCardMsg();
                    buyCardMsg.setActionTypeClicked(actionType);
                    sendUpdate(buyCardMsg);
                    //LKDoc: UIThread ist in der Dominion Activity
                })
                .setNegativeButton("Close", (dialog, which) -> {
                    dialog.cancel();
                });

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

        View view = layoutInflater.inflate(drawableDialog, null);

        builder.setView(view);
        return builder.create();
    }

    /**
     * LKDoc: setOnClickListener für die einzelnen Aktionskarten
     * @param activity
     * @param fragmentManager wird benötigt wegen dem ErrorDialogHandler
     */
    public void init(Activity activity, FragmentManager fragmentManager) {
        // Hexe
        ImageButton buttonHexe;
        buttonHexe = activity.findViewById(R.id.btn_hexe);
        buttonHexe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LKDoc: fragmentManager muss mitgegeben werden
                onClickHexe(fragmentManager);
            }
        });

        //Burggraben
        ImageButton buttonBurggraben;
        buttonBurggraben = activity.findViewById(R.id.btn_burggraben);
        buttonBurggraben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBurggraben(fragmentManager);
            }
        });

        //Dorf
        ImageButton buttonDorf;
        buttonDorf = activity.findViewById(R.id.btn_dorf);
        buttonDorf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDorf(fragmentManager);
            }
        });

        //Holzfaeller
        ImageButton buttonHolzfaeller;
        buttonHolzfaeller = activity.findViewById(R.id.btn_holzfaeller);
        buttonHolzfaeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHolzfaeller(fragmentManager);
            }
        });
        //Keller
        ImageButton buttonKeller;
        buttonKeller = activity.findViewById(R.id.btn_keller);
        buttonKeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickKeller(fragmentManager);
            }
        });

        //Markt
        ImageButton buttonMarkt;
        buttonMarkt = activity.findViewById(R.id.btn_markt);
        buttonMarkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMarkt(fragmentManager);
            }
        });

        //Miliz
        ImageButton buttonMiliz;
        buttonMiliz = activity.findViewById(R.id.btn_miliz);
        buttonMiliz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMiliz(fragmentManager);
            }
        });

        //Mine
        ImageButton buttonMine;
        buttonMine = activity.findViewById(R.id.btn_mine);
        buttonMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMine(fragmentManager);
            }
        });

        //Schmiede
        ImageButton buttonSchmiede;
        buttonSchmiede = activity.findViewById(R.id.btn_schmiede);
        buttonSchmiede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSchmiede(fragmentManager);
            }
        });
        //Werkstatt
        ImageButton buttonWerkstatt;
        buttonWerkstatt = activity.findViewById(R.id.btn_werkstatt);
        buttonWerkstatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickWerkstatt(fragmentManager);
            }
        });
    }

    /**
     * LKDoc: OnClick Methoden. .show --> wird vom Fragmentmanager gebraucht (Lifecycle)
     * show ruft daher den entsprechenden Dialog für die entsprechende Karte auf
     * @param fragmentManager muss ebenfalls übergeben werden, wegen dem OnClick
     */
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

    /**
     * LKDoc: sende Update der msg an clientConnector --> ClientConnector.class
     * @param buyCardMsg
     */
    private void sendUpdate(BuyCardMsg buyCardMsg){
        //LKDoc: Sonarcloud wants to have lambda
        Thread th = new Thread(() -> clientConnector.sendbuyCard(buyCardMsg));
        th.start();
    }

    public ClientConnector getClientConnector() {
        return clientConnector;
    }

    public void setClientConnector(ClientConnector clientConnector) {
        this.clientConnector = clientConnector;
    }
}