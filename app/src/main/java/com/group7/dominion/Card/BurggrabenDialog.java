package com.group7.dominion.Card;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.floriankleewein.commonclasses.Board.Board;
import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.Cards.ActionType;
import com.floriankleewein.commonclasses.Cards.Card;
import com.group7.dominion.R;

import androidx.appcompat.app.AppCompatDialogFragment;


public class BurggrabenDialog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    private Board board;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Buy Action Card")
                .setMessage("Do you want to buy this card?")
                .setPositiveButton("Buy", (dialog, which) -> {
                    // Buy the Card
                    Card card = this.board.getActionField().pickCard(ActionType.BURGGRABEN);
                    if(card == null) {
                        //Error => Karten Type existiert nicht mehr im Stapel
                    } else {
                        ActionCard actionCard = (ActionCard) card;
                        Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                ", Card Count: "+ actionCard.getAction().getCardCount());
                    }
                })
                .setNegativeButton("Close", (dialog, which) -> {
                    dialog.cancel();
                });

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

        View view = layoutInflater.inflate(R.layout.burggraben_info_dialog, null);

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}