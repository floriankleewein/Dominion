package com.group7.dominion.Card;

import android.os.AsyncTask;
import android.util.Log;

import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Network.GameUpdateMsg;

import androidx.fragment.app.FragmentManager;

public class ActionUITask extends AsyncTask<String, String, String> {

    private FragmentManager fragmentManager;
    private ErrorDialogHandler errorDialogHandler;
    private GameUpdateMsg gameUpdateMsg;

    private final static String actionConst = "Action";
    private final static String actiontypeConst = "ActionType: ";
    private final static String cardcountConst = ", Card Count: ";
    private final static String moneyvalueConst = ", Money Value: " ;
    private final static String actioncountConst = ", Action Count: ";

    public ActionUITask(ErrorDialogHandler errorDialogHandler, FragmentManager fragmentManager, GameUpdateMsg gameUpdateMsg) {
        this.errorDialogHandler = errorDialogHandler;
        this.fragmentManager = fragmentManager;
        this.gameUpdateMsg = gameUpdateMsg;
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        GameUpdateMsg gameUpdateMsg1 = (GameUpdateMsg) this.gameUpdateMsg;
        Card card = gameUpdateMsg1.getBoughtCard();

        if(card == null) {
            this.errorDialogHandler.show(this.fragmentManager, "errorDialog");
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
}
