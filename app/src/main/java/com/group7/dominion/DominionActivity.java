package com.group7.dominion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.board.Board;
import com.floriankleewein.commonclasses.cards.ActionCard;
import com.floriankleewein.commonclasses.cards.Card;
import com.floriankleewein.commonclasses.chat.ChatMessage;
import com.floriankleewein.commonclasses.gamelogic.PlayStatus;
import com.floriankleewein.commonclasses.network.ClientConnector;
import com.floriankleewein.commonclasses.network.GetGameMsg;
import com.floriankleewein.commonclasses.network.HasCheatedMessage;
import com.floriankleewein.commonclasses.network.BuyCardMsg;
import com.floriankleewein.commonclasses.network.PlayCardMsg;
import com.floriankleewein.commonclasses.network.messages.EndGameMsg;
import com.floriankleewein.commonclasses.network.messages.NewTurnMessage;
import com.floriankleewein.commonclasses.network.SuspectMessage;
import com.floriankleewein.commonclasses.network.UpdatePlayerNamesMsg;
import com.floriankleewein.commonclasses.user.User;
import com.group7.dominion.card.ActionDialogHandler;
import com.group7.dominion.card.ErrorDialogHandler;
import com.group7.dominion.card.HandCardsHandler;
import com.group7.dominion.card.ImageButtonHandler;
import com.group7.dominion.chat.ChatFragment;
import com.group7.dominion.cheatfunction.ShakeListener;

import java.util.ArrayList;
import java.util.Iterator;

public class DominionActivity extends AppCompatActivity implements ChatFragment.OnChatMessageArrivedListener {

    private ChatFragment chatFragment;
    private ClientConnector clientConnector;
    private HandCardsHandler cardsHandler;
    private TextView buyAmounts;
    private TextView playAmounts;
    private TextView coinAmounts;
    private TextView[] playerNames = new TextView[4];
    private TextView[] playerScores = new TextView[4];
    private static final int nameMaxLength = 10;

    private FragmentManager fragmentManager;

    //Image Buttons
    private ImageButtonHandler imageButtonHandler;

    //Pop-up Info Dialogs
    private ActionDialogHandler actionDialogHandler;

    private Board board;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dominion);
        cardsHandler = new HandCardsHandler(this);


        playerNames[0] = findViewById(R.id.txtVPlayername1);
        playerNames[1] = findViewById(R.id.txtVPlayername2);
        playerNames[2] = findViewById(R.id.txtVPlayername3);
        playerNames[3] = findViewById(R.id.txtVPlayername4);

        playerScores[0] = findViewById(R.id.txtVPlayerScore1);
        playerScores[1] = findViewById(R.id.txtVPlayerScore2);
        playerScores[2] = findViewById(R.id.txtVPlayerScore3);
        playerScores[3] = findViewById(R.id.txtVPlayerScore4);


        Button chatButton;
        chatButton = findViewById(R.id.chat_Button);

        FrameLayout fragmentContainer = findViewById(R.id.chatFragmentContainer);

        this.clientConnector = ClientConnector.getClientConnector();


        chatButton.setOnClickListener(view -> openFragment());

        sendUpdateMessage();
        ArrayList<String> names = new ArrayList<>();
        ClientConnector.getClientConnector().registerCallback(UpdatePlayerNamesMsg.class, (msg -> runOnUiThread(() -> {
            names.clear();
            names.addAll(((UpdatePlayerNamesMsg) msg).getNameList());
        })));

        ShakeListener shakeListener = new ShakeListener(getSupportFragmentManager(), getUsername(), names);
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(shakeListener.newSensorListener(), sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);


        fragmentManager = getSupportFragmentManager();

        /**
         * LKDoc: ActionCards
         */
        actionDialogHandler = new ActionDialogHandler();
        actionDialogHandler.init(this, fragmentManager);

        /**
         * LKDoc: Estate- and MoneyCards
         */
        imageButtonHandler = new ImageButtonHandler();
        imageButtonHandler.init(this, fragmentManager);


        //erstelle ein Bundle mit dem der Spielername an das ChatFragment weitergegeben wird
        Bundle bundle = new Bundle();
        bundle.putString("playerName", getUsername());

        this.chatFragment = new ChatFragment();
        //hinzufügen des Spielernamens als Argument an den Chat
        chatFragment.setArguments(bundle);

        //hiermit wird die transition von game zum chat durchgeführt
        chatButton.setOnClickListener(view -> openFragment());

        buyAmounts = findViewById(R.id.textViewBuyAmounts);
        playAmounts = findViewById(R.id.textViewActionAmount);
        coinAmounts = findViewById(R.id.textViewCoinsAmount);
    }

    @Override
    protected void onStart() {
        super.onStart();

        clientConnector = ClientConnector.getClientConnector();

        actionDialogHandler.setClientConnector(clientConnector);
        imageButtonHandler.setClientConnector(clientConnector);

        /*
        Sending a Message to get the First Cards
         */
        cardsHandler.sendMessage();

        /**
         * @Author Maurer Florian
         * Two Callbacks shows who cheated and who is suspecting someone.
         */

        clientConnector.registerCallback(HasCheatedMessage.class, (msg -> {
            runOnUiThread(() -> {
                String cheaterName = ((HasCheatedMessage) msg).getName();
                Toast.makeText(getApplicationContext(), cheaterName + " hat eine zusätzliche Karte gezogen...", Toast.LENGTH_SHORT).show();
            });
        }));

        clientConnector.registerCallback(SuspectMessage.class, (msg -> {
            runOnUiThread(() -> {
                String suspectedUser = ((SuspectMessage) msg).getSuspectedUserName();
                String userName = ((SuspectMessage) msg).getUserName();
                Toast.makeText(getApplicationContext(), userName + " glaubt, dass " + suspectedUser + " geschummelt hat", Toast.LENGTH_SHORT).show();
            });
        }));

        clientConnector.registerCallback(ChatMessage.class, (msg -> {
            runOnUiThread(() -> {
                String chatMessages = ((ChatMessage) msg).getMessage();
                Toast.makeText(getApplicationContext(), "Nachricht: " + chatMessages, Toast.LENGTH_SHORT).show();
            });
        }));

        ClientConnector.getClientConnector().registerCallback(GetGameMsg.class, msg -> {
            runOnUiThread(() -> {
                User user = ((GetGameMsg) msg).getGame().findUser(getUsername());
                cardsHandler.initCards(user);
                if (user.getUserName().equals(((GetGameMsg) msg).getGame().getActivePlayer().getUserName())) {
                    if (((GetGameMsg) msg).getPlayStatus() == PlayStatus.ACTION_PHASE) {
                        cardsHandler.onClickListenerActionPhase();
                    } else if (((GetGameMsg) msg).getPlayStatus() == PlayStatus.PLAY_COINS) {
                        cardsHandler.onClickListenerBuyPhase();
                    }
                }
            });
        });

        ClientConnector.getClientConnector().registerCallback(PlayCardMsg.class, msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    handlePlayCardMsg((PlayCardMsg) msg);
                }
            });
        });

        ClientConnector.getClientConnector().registerCallback(NewTurnMessage.class, msg -> {
            runOnUiThread(() -> {
                Log.i("Turn", "NEW TURN IN GAME");
                handNewTurnMsg((NewTurnMessage) msg);
            });
        });

        final String ERRORDIALOG_CONST = "errorDialog";

        /**
         * LKDoc:   großer UIThread für den ActionDialogHandler. Hier wird die msg vom Server abgefangen
         *              1) zuerst wird überprüft ob die Karte gekauft werden kann (genug Geld) = Toast
         *              2) ist die Karte zusätzlich null bedeutet dies es sind keine mehr im Stapel und der ErrorDialogHandler wird aufgerufen (braucht Fragment)
         *              3) ist alles ok kann die Karte gekauft werden
         */
        clientConnector.registerCallback(BuyCardMsg.class, (msg -> {
            runOnUiThread(() -> {
                        BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
                        User user = gameUpdateMsg1.getGame().findUser(getUsername());
                        buyAmounts.setText(Integer.toString(user.getGamePoints().getBuyAmounts()));
                        coinAmounts.setText(Integer.toString(user.getGamePoints().getCoins()));
                        playAmounts.setText(Integer.toString(user.getGamePoints().getPlaysAmount()));
                        Card card = gameUpdateMsg1.getBoughtCard();
                        if (card == null && !gameUpdateMsg1.isCantBuyCard()) {
                            Toast.makeText(getApplicationContext(), "Du kannst diese Karte nicht kaufen", Toast.LENGTH_SHORT).show();
                        } else if (card == null && gameUpdateMsg1.isCantBuyCard()) {
                            ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                            errorDialogHandler.show(fragmentManager, ERRORDIALOG_CONST);
                        } else if (card instanceof ActionCard) {
                            ActionCard actionCard = (ActionCard) card;
                            switch (actionCard.getActionType()) {
                                case HEXE:
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Curse Count: " + actionCard.getAction().getCurseCount());
                                    break;
                                case WERKSTATT:
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Max Money Value: " + actionCard.getAction().getMaxMoneyValue());
                                    break;
                                case SCHMIEDE:
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount());
                                    break;
                                case MINE:
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Take MoneyCard That Cost Three More Than Old: " + actionCard.getAction().isTakeMoneyCardThatCostThreeMoreThanOld() +
                                            ", Take Card On Hand: " + actionCard.getAction().isTakeCardOnHand());
                                    break;
                                case MILIZ:
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Money Value: " + actionCard.getAction().getMoneyValue() +
                                            ", Throw Every UserCards Until Three Left: " + actionCard.getAction().isThrowEveryUserCardsUntilThreeLeft());
                                    break;
                                case MARKT:
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Action Count: " + actionCard.getAction().getActionCount() +
                                            ", Money Value: " + actionCard.getAction().getMoneyValue() +
                                            ", Buy Count: " + actionCard.getAction().getBuyCount());
                                    break;
                                case KELLER:
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Action Count: " + actionCard.getAction().getActionCount() +
                                            ", Throw Any Amount Cards: " + actionCard.getAction().isThrowAnyAmountCards());
                                    break;
                                case HOLZFAELLER:
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Buy Count: " + actionCard.getAction().getBuyCount() +
                                            ", Money Value: " + actionCard.getAction().getMoneyValue());
                                    break;
                                case DORF:
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Action Count: " + actionCard.getAction().getActionCount());
                                    break;
                                case BURGGRABEN:
                                    Log.i("Action", "ActionType: " + actionCard.getActionType() +
                                            ", Card Count: " + actionCard.getAction().getCardCount() +
                                            ", Throw Every UserCards Until Three Left: " + actionCard.getAction().isThrowEveryUserCardsUntilThreeLeft());
                                    break;
                                default:
                                    //LKDoc: do nothing
                                    break;
                            }
                            int iterator = 0;
                            for (User u : gameUpdateMsg1.getGame().getPlayerList()) {
                                if(u.getUserName().length() > nameMaxLength) {
                                    playerNames[iterator].setText((u.getUserName()).substring(0, nameMaxLength));
                                } else {
                                    playerNames[iterator].setText(u.getUserName());
                                }
                            }
                        }
                    }
            );
        }));

        clientConnector.registerCallback(EndGameMsg.class, (msg -> {
            runOnUiThread(() -> {
                Log.i("ENDGAMEMSG", "Game Has ended");
                Intent i = new Intent(this, GameEndActivity.class);
                User user = ((EndGameMsg) msg).getWinningUser();
                Game game = ((EndGameMsg) msg).getGame();
                i.putExtra("winner", user.getUserName());
                startActivity(i);
            });
        }));
    }

    /**
     * @param msg This Method decide how to handle the Play Card Message.
     *            It checks if the Player is the active Player and if he is in the Action Phase or
     *            the Play Coins Phase
     * @Author Maurer Florian
     */

    private void handlePlayCardMsg(PlayCardMsg msg) {
        User user = msg.getGame().findUser(getUsername());
        buyAmounts.setText(Integer.toString(user.getGamePoints().getBuyAmounts()));
        coinAmounts.setText(Integer.toString(user.getGamePoints().getCoins()));
        playAmounts.setText(Integer.toString(user.getGamePoints().getPlaysAmount()));
        if (user.getUserName().equals(msg.getGame().getActivePlayer().getUserName())) {
            cardsHandler.setImageButtonsNull();
            cardsHandler.initCards(user);
            if (msg.getPlayStatus() == PlayStatus.ACTION_PHASE) {
                cardsHandler.onClickListenerActionPhase();
            } else if (msg.getPlayStatus() == PlayStatus.PLAY_COINS) {
                cardsHandler.onClickListenerBuyPhase();
            }
        }
        int iterator = 0;
        for (User u : msg.getGame().getPlayerList()) {
            if(u.getUserName().length() > nameMaxLength) {
                playerNames[iterator].setText((u.getUserName()).substring(0, nameMaxLength));
            } else {
                playerNames[iterator].setText(u.getUserName());
            }
            playerScores[iterator++].setText(Integer.toString(u.getGamePoints().getWinningPoints()));
        }
    }

    /**
     * @param msg Method is similarly to the handlePlayCardMsg Method. It's only programmed twice because of casting the messages
     * @Author Maurer Florian
     */
    public void handNewTurnMsg(NewTurnMessage msg) {
        cardsHandler.setImageButtonsNull();
        User user = msg.getGame().findUser(getUsername());
        buyAmounts.setText(Integer.toString(user.getGamePoints().getBuyAmounts()));
        coinAmounts.setText(Integer.toString(user.getGamePoints().getCoins()));
        playAmounts.setText(Integer.toString(user.getGamePoints().getPlaysAmount()));
        if (user.getUserName().equals(msg.getGame().getActivePlayer().getUserName())) {
            cardsHandler.initCards(user);
            if (msg.getPlayStatus() == PlayStatus.ACTION_PHASE) {
                cardsHandler.onClickListenerActionPhase();
            } else if (msg.getPlayStatus() == PlayStatus.PLAY_COINS) {
                cardsHandler.onClickListenerBuyPhase();
            }
        }
        int iterator = 0;
        for (User u : msg.getGame().getPlayerList()) {
            if(u.getUserName().length() > nameMaxLength) {
                playerNames[iterator].setText((u.getUserName()).substring(0, nameMaxLength));
            } else {
                playerNames[iterator].setText(u.getUserName());
            }
        }
    }

    public void sendUpdateMessage() {
        Thread th = new Thread(() -> ClientConnector.getClientConnector().updatePlayerNames());
        th.start();
    }

    public String getUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("USERNAME", Context.MODE_PRIVATE);
        return sharedPreferences.getString("us", null);
    }

    //führt den Wechsel von Spiel zu Chat durch
    public void openFragment() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right);

        //Hiermit wird gewährleistet, dass man mit dem BackButton auch wirklich zur Activity zurückkehren kann.
        //Mittels addToBackStack wird jedes neu hinzugefügte Fragment auf einem Stack zwischengespeichert
        trans.addToBackStack(null);

        trans.add(R.id.chatFragmentContainer, chatFragment, "CHAT_FRAGMENT").commit();

    }

    @Override
    public void onChatMessageArrived(String msg) {
        Toast.makeText(getApplicationContext(), "Nachricht: " + msg, Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}
