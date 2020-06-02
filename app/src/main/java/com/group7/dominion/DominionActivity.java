package com.group7.dominion;

import android.content.Context;
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
import com.floriankleewein.commonclasses.network.Messages.NewTurnMessage;
import com.floriankleewein.commonclasses.network.SuspectMessage;
import com.floriankleewein.commonclasses.network.UpdatePlayerNamesMsg;
import com.floriankleewein.commonclasses.user.User;
import com.group7.dominion.card.ActionDialogHandler;
import com.group7.dominion.card.HandCardsHandler;
import com.group7.dominion.card.ImageButtonHandler;
import com.group7.dominion.chat.ChatFragment;
import com.group7.dominion.cheatfunction.ShakeListener;

import java.util.ArrayList;

public class DominionActivity extends AppCompatActivity implements ChatFragment.OnChatMessageArrivedListener {

    private Button chatButton;
    private FrameLayout fragmentContainer;
    private ChatFragment chatFragment;
    private FragmentTransaction trans;
    private ClientConnector clientConnector;
    private HandCardsHandler cardsHandler;
    private User user;
    private SensorManager sm;
    private ShakeListener shakeListener;
    private TextView playerScores;
    private int CallbackCounter;


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


        chatButton = findViewById(R.id.chat_Button);

        fragmentContainer = findViewById(R.id.chatFragmentContainer);

        this.clientConnector = ClientConnector.getClientConnector();

        chatButton.setOnClickListener(view -> openFragment());

        sendUpdateMessage();
        ArrayList<String> names = new ArrayList<>();
        ClientConnector.getClientConnector().registerCallback(UpdatePlayerNamesMsg.class, (msg -> {
            runOnUiThread(() -> {
                names.clear();
                names.addAll(((UpdatePlayerNamesMsg) msg).getNameList());
            });
        }));

        shakeListener = new ShakeListener(getSupportFragmentManager(), getUsername(), names);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(shakeListener.newSensorListener(), sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);


        fragmentManager = getSupportFragmentManager();

        //Hexe

        actionDialogHandler = new ActionDialogHandler();
        actionDialogHandler.init(this, fragmentManager);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        playerScores = findViewById(R.id.txtVPlayerScore);
        playerScores.setText("Scores of Players.");

        ClientConnector clientConnector = ClientConnector.getClientConnector();

        actionDialogHandler.setClientConnector(clientConnector);
        imageButtonHandler.setClientConnector(clientConnector);

        clientConnector.registerCallback(HasCheatedMessage.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String CheaterName = ((HasCheatedMessage) msg).getName();
                    Toast.makeText(getApplicationContext(), CheaterName + " hat eine zusätzliche Karte gezogen...", Toast.LENGTH_SHORT).show();
                }
            });
        }));

        clientConnector.registerCallback(SuspectMessage.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String SuspectedUser = ((SuspectMessage) msg).getSuspectedUserName();
                    String Username = ((SuspectMessage) msg).getUserName();
                    Toast.makeText(getApplicationContext(), Username + " glaubt, dass " + SuspectedUser + " geschummelt hat", Toast.LENGTH_SHORT).show();
                }
            });
        }));

        clientConnector.registerCallback(ChatMessage.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String ChatMessages = ((ChatMessage) msg).getMessage();
                    Toast.makeText(getApplicationContext(), "Nachricht: " + ChatMessages, Toast.LENGTH_SHORT).show();
                }
            });
        }));

        ClientConnector.getClientConnector().registerCallback(GetGameMsg.class, msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    User user = ((GetGameMsg) msg).getGame().findUser(getUsername());
                    cardsHandler.initCards(user);
                    if (user.getUserName().equals(((GetGameMsg) msg).getGame().getActivePlayer().getUserName())) {
                        if (((GetGameMsg) msg).getPlayStatus() == PlayStatus.ACTION_PHASE) {
                            cardsHandler.onClickListenerActionPhase();
                        } else if (((GetGameMsg) msg).getPlayStatus() == PlayStatus.PLAY_COINS) {
                            cardsHandler.onClickListenerBuyPhase();
                        }
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("Turn", "NEW TURN IN GAME");
                    handNewTurnMsg((NewTurnMessage) msg);
                }
            });
        });


        cardsHandler.sendMessage();

        clientConnector.registerCallback(BuyCardMsg.class, (msg -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("BuyCardMsg is in Dominion Activity");
                    BuyCardMsg gameUpdateMsg1 = (BuyCardMsg) msg;
                    Card card = gameUpdateMsg1.getBoughtCard();
                    if (card == null) {
                        Toast.makeText(getApplicationContext(), "Du kannst diese Karte nicht kaufen", Toast.LENGTH_SHORT).show();
                    } else {
                        if (card instanceof ActionCard) {
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
                            }
                            String text = "";
                            for (User u : gameUpdateMsg1.getGame().getPlayerList()) {
                                text += u.getUserName() + ": " + u.getGamePoints().getWinningPoints() + "\n";
                            }
                            playerScores.setText(text);
                        }
                    }

                }
            });
        }));
    }

    private void handlePlayCardMsg(PlayCardMsg msg) {
        User user;
        user = msg.getGame().findUser(getUsername());
        if (user.getUserName().equals(msg.getGame().getActivePlayer().getUserName())) {
            cardsHandler.setImageButtonsNull();
            cardsHandler.initCards(user);
            if (msg.getPlayStatus() == PlayStatus.ACTION_PHASE) {
                cardsHandler.onClickListenerActionPhase();
            } else if (msg.getPlayStatus() == PlayStatus.PLAY_COINS) {
                cardsHandler.onClickListenerBuyPhase();
            }
        }
        String text = "";
        for (User u : msg.getGame().getPlayerList()) {
            text += u.getUserName() + ": " + u.getGamePoints().getWinningPoints() + "\n";
        }
        playerScores.setText(text);
    }

    public void handNewTurnMsg(NewTurnMessage msg) {
        cardsHandler.setImageButtonsNull();
        User user = msg.getGame().findUser(getUsername());
        if (user.getUserName().equals(msg.getGame().getActivePlayer().getUserName())) {
            cardsHandler.initCards(user);
            if (msg.getPlayStatus() == PlayStatus.ACTION_PHASE) {
                cardsHandler.onClickListenerActionPhase();
            } else if (msg.getPlayStatus() == PlayStatus.PLAY_COINS) {
                cardsHandler.onClickListenerBuyPhase();
            }
        }
        String text = "";
        for (User u : msg.getGame().getPlayerList()) {
            text += u.getUserName() + ": " + u.getGamePoints().getWinningPoints() + "\n";
        }
        playerScores.setText(text);
    }

    public void sendUpdateMessage() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                ClientConnector.getClientConnector().updatePlayerNames();
            }
        });
        th.start();
    }

    public String getUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("USERNAME", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("us", null);
        return str;
    }

    //führt den Wechsel von Spiel zu Chat durch
    public void openFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
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
