package com.group7.dominion.Card;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
/*import android.widget.Spinner;

import com.floriankleewein.commonclasses.Board.Board;
import com.floriankleewein.commonclasses.Cards.Action;
import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.Cards.ActionType;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.group7.dominion.R;

import java.util.List;

import androidx.appcompat.app.AppCompatDialogFragment;


public class MarktDialog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    private Board board;
    private Card card;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder;
        LayoutInflater layoutInflater;
        View view;

        if (this.card instanceof ActionCard) {
            ActionCard card = (ActionCard) this.card;

            switch (card.getActionType()) {
                case DORF:
                    builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Buy Action Card")
                            .setMessage("Do you want to buy this card?")
                            .setPositiveButton("Buy", (dialog, which) -> {
                                // Buy the Card
                                Card cards = this.board.getActionField().pickCard(ActionType.BURGGRABEN);
                                if (cards == null) {
                                    //Error => Karten Type existiert nicht mehr im Stapel
                                } else {
                                    ActionCard actionCard = (ActionCard) cards;
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Curse Count: " + actionCard.getAction().getCurseCount());
                                }
                            })
                            .setNegativeButton("Close", (dialog, which) -> {
                                dialog.cancel();
                            });
                    layoutInflater = LayoutInflater.from(getActivity());

                    view = layoutInflater.inflate(R.layout.hexe_info_dialog, null);

                    builder.setView(view);
                    return builder.create();
                break;
                case BURGGRABEN:
                    builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Buy Action Card")
                            .setMessage("Do you want to buy this card?")
                            .setPositiveButton("Buy", (dialog, which) -> {
                                // Buy the Card
                                Card cards = this.board.getActionField().pickCard(ActionType.DORF);
                                if (cards == null) {
                                    //Error => Karten Type existiert nicht mehr im Stapel
                                } else {
                                    ActionCard actionCard = (ActionCard) cards;
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Curse Count: " + actionCard.getAction().getCurseCount());
                                }
                            })
                            .setNegativeButton("Close", (dialog, which) -> {
                                dialog.cancel();
                            });
                    layoutInflater = LayoutInflater.from(getActivity());

                    view = layoutInflater.inflate(R.layout.hexe_info_dialog, null);

                    builder.setView(view);
                    return builder.create();
                break;

                case HOLZFAELLER:
                    builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Buy Action Card")
                            .setMessage("Do you want to buy this card?")
                            .setPositiveButton("Buy", (dialog, which) -> {
                                // Buy the Card
                                Card cards = this.board.getActionField().pickCard(ActionType.HOLZFAELLER);
                                if (cards == null) {
                                    //Error => Karten Type existiert nicht mehr im Stapel
                                } else {
                                    ActionCard actionCard = (ActionCard) cards;
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Curse Count: " + actionCard.getAction().getCurseCount());
                                }
                            })
                            .setNegativeButton("Close", (dialog, which) -> {
                                dialog.cancel();
                            });
                    layoutInflater = LayoutInflater.from(getActivity());

                    view = layoutInflater.inflate(R.layout.hexe_info_dialog, null);

                    builder.setView(view);
                    return builder.create();
                break;
                case KELLER:
                    builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Buy Action Card")
                            .setMessage("Do you want to buy this card?")
                            .setPositiveButton("Buy", (dialog, which) -> {
                                // Buy the Card
                                Card cards = this.board.getActionField().pickCard(ActionType.KELLER);
                                if (cards == null) {
                                    //Error => Karten Type existiert nicht mehr im Stapel
                                } else {
                                    ActionCard actionCard = (ActionCard) cards;
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Curse Count: " + actionCard.getAction().getCurseCount());
                                }
                            })
                            .setNegativeButton("Close", (dialog, which) -> {
                                dialog.cancel();
                            });
                    layoutInflater = LayoutInflater.from(getActivity());

                    view = layoutInflater.inflate(R.layout.hexe_info_dialog, null);

                    builder.setView(view);
                    return builder.create();
                break;
                case WERKSTATT:
                    builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Buy Action Card")
                            .setMessage("Do you want to buy this card?")
                            .setPositiveButton("Buy", (dialog, which) -> {
                                // Buy the Card
                                Card cards = this.board.getActionField().pickCard(ActionType.WERKSTATT);
                                if (cards == null) {
                                    //Error => Karten Type existiert nicht mehr im Stapel
                                } else {
                                    ActionCard actionCard = (ActionCard) cards;
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Curse Count: " + actionCard.getAction().getCurseCount());
                                }
                            })
                            .setNegativeButton("Close", (dialog, which) -> {
                                dialog.cancel();
                            });
                    layoutInflater = LayoutInflater.from(getActivity());

                    view = layoutInflater.inflate(R.layout.hexe_info_dialog, null);

                    builder.setView(view);
                    return builder.create();
                break;
                case SCHMIEDE:
                    builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Buy Action Card")
                            .setMessage("Do you want to buy this card?")
                            .setPositiveButton("Buy", (dialog, which) -> {
                                // Buy the Card
                                Card cards = this.board.getActionField().pickCard(ActionType.SCHMIEDE);
                                if (cards == null) {
                                    //Error => Karten Type existiert nicht mehr im Stapel
                                } else {
                                    ActionCard actionCard = (ActionCard) cards;
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Curse Count: " + actionCard.getAction().getCurseCount());
                                }
                            })
                            .setNegativeButton("Close", (dialog, which) -> {
                                dialog.cancel();
                            });
                    layoutInflater = LayoutInflater.from(getActivity());

                    view = layoutInflater.inflate(R.layout.hexe_info_dialog, null);

                    builder.setView(view);
                    return builder.create();
                break;
                case MARKT:
                    builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Buy Action Card")
                            .setMessage("Do you want to buy this card?")
                            .setPositiveButton("Buy", (dialog, which) -> {
                                // Buy the Card
                                Card cards = this.board.getActionField().pickCard(ActionType.MARKT);
                                if (cards == null) {
                                    //Error => Karten Type existiert nicht mehr im Stapel
                                } else {
                                    ActionCard actionCard = (ActionCard) cards;
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Curse Count: " + actionCard.getAction().getCurseCount());
                                }
                            })
                            .setNegativeButton("Close", (dialog, which) -> {
                                dialog.cancel();
                            });
                    layoutInflater = LayoutInflater.from(getActivity());

                    view = layoutInflater.inflate(R.layout.hexe_info_dialog, null);

                    builder.setView(view);
                    return builder.create();
                break;
                case HEXE:
                    builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Buy Action Card")
                            .setMessage("Do you want to buy this card?")
                            .setPositiveButton("Buy", (dialog, which) -> {
                                // Buy the Card
                                Card cards = this.board.getActionField().pickCard(ActionType.HEXE);
                                if (cards == null) {
                                    //Error => Karten Type existiert nicht mehr im Stapel
                                } else {
                                    ActionCard actionCard = (ActionCard) cards;
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Curse Count: " + actionCard.getAction().getCurseCount());
                                }
                            })
                            .setNegativeButton("Close", (dialog, which) -> {
                                dialog.cancel();
                            });
                    layoutInflater = LayoutInflater.from(getActivity());

                    view = layoutInflater.inflate(R.layout.hexe_info_dialog, null);

                    builder.setView(view);
                    return builder.create();
                break;
                case MINE:
                    builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Buy Action Card")
                            .setMessage("Do you want to buy this card?")
                            .setPositiveButton("Buy", (dialog, which) -> {
                                // Buy the Card
                                Card cards = this.board.getActionField().pickCard(ActionType.MINE);
                                if (cards == null) {
                                    //Error => Karten Type existiert nicht mehr im Stapel
                                } else {
                                    ActionCard actionCard = (ActionCard) cards;
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Curse Count: " + actionCard.getAction().getCurseCount());
                                }
                            })
                            .setNegativeButton("Close", (dialog, which) -> {
                                dialog.cancel();
                            });
                    layoutInflater = LayoutInflater.from(getActivity());

                    view = layoutInflater.inflate(R.layout.hexe_info_dialog, null);

                    builder.setView(view);
                    return builder.create();
                break;
                case MILIZ:
                    builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Buy Action Card")
                            .setMessage("Do you want to buy this card?")
                            .setPositiveButton("Buy", (dialog, which) -> {
                                // Buy the Card
                                Card cards = this.board.getActionField().pickCard(ActionType.MILIZ);
                                if (cards == null) {
                                    //Error => Karten Type existiert nicht mehr im Stapel
                                } else {
                                    ActionCard actionCard = (ActionCard) cards;
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Curse Count: " + actionCard.getAction().getCurseCount());
                                }
                            })
                            .setNegativeButton("Close", (dialog, which) -> {
                                dialog.cancel();
                            });
                    layoutInflater = LayoutInflater.from(getActivity());

                    view = layoutInflater.inflate(R.layout.hexe_info_dialog, null);

                    builder.setView(view);
                    return builder.create();
                break;
                default:
                    throw new IllegalStateException("Unexpected value: " + card.getActionType());
            }
        }
        return super.onCreateDialog(null);
    }

    @Override
    public void onItemSelected (AdapterView < ? > adapterView, View view,int i, long l){

    }

    @Override
    public void onNothingSelected (AdapterView < ? > adapterView){

    }

    public Board getBoard () {
        return board;
    }

    public void setBoard (Board board){
        this.board = board;
    }
}*/