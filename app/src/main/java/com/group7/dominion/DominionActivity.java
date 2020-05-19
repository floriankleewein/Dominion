package com.group7.dominion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.floriankleewein.commonclasses.Board.Board;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Cards.EstateCard;
import com.floriankleewein.commonclasses.Cards.EstateType;
import com.floriankleewein.commonclasses.Cards.MoneyCard;
import com.floriankleewein.commonclasses.Cards.MoneyType;
import com.floriankleewein.commonclasses.Chat.ChatMessage;
import com.floriankleewein.commonclasses.Network.ClientConnector;
import com.group7.dominion.Card.BurggrabenDialog;
import com.group7.dominion.Card.DorfDialog;
import com.group7.dominion.Card.HexeDialog;
import com.group7.dominion.Card.HolzfaellerDialog;
import com.group7.dominion.Card.KellerDialog;
import com.group7.dominion.Card.MarktDialog;
import com.group7.dominion.Card.MilizDialog;
import com.group7.dominion.Card.MineDialog;
import com.group7.dominion.Card.SchmiedeDialog;
import com.group7.dominion.Card.WerkstattDialog;
import com.group7.dominion.Chat.ChatFragment;

import com.floriankleewein.commonclasses.Game;
import com.floriankleewein.commonclasses.Network.HasCheatedMessage;
import com.floriankleewein.commonclasses.Network.SuspectMessage;
import com.floriankleewein.commonclasses.Network.UpdatePlayerNamesMsg;
import com.group7.dominion.CheatFunction.ShakeListener;

import java.util.ArrayList;

public class DominionActivity extends AppCompatActivity implements ChatFragment.OnChatMessageArrivedListener {

    private Button chatButton;
    private FrameLayout fragmentContainer;
    private ChatFragment chatFragment;
    private FragmentTransaction trans;
    private ClientConnector clientConnector;

    private SensorManager sm;
    private ShakeListener shakeListener;

    private FragmentManager fragmentManager;

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
    private ImageButton buttonGold;
    private ImageButton buttonSilber;
    private ImageButton buttonKupfer;
    private ImageButton buttonProvinz;
    private ImageButton buttonAnwesen;
    private ImageButton buttonHerzogturm;
    private ImageButton buttonFluch;

    //Pop-up Info Dialogs
    private HexeDialog hexeDialog;
    private BurggrabenDialog burggrabenDialog;
    private DorfDialog dorfDialog;
    private HolzfaellerDialog holzfaellerDialog;
    private KellerDialog kellerDialog;
    private MarktDialog marktDialog;
    private MilizDialog milizDialog;
    private MineDialog mineDialog;
    private SchmiedeDialog schmiedeDialog;
    private WerkstattDialog werkstattDialog;




    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dominion);

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
        hexeDialog = new HexeDialog();
        buttonHexe = findViewById(R.id.btn_hexe);
        buttonHexe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHexe();
            }
        });

        //Burggraben
        burggrabenDialog = new BurggrabenDialog();
        buttonBurggraben = findViewById(R.id.btn_burggraben);
        buttonBurggraben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBurggraben();
            }
        });

        //Dorf
        dorfDialog = new DorfDialog();
        buttonDorf = findViewById(R.id.btn_dorf);
        buttonDorf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDorf();
            }
        });
        //Holzfaeller
        holzfaellerDialog = new HolzfaellerDialog();
        buttonHolzfaeller = findViewById(R.id.btn_holzfaeller);
        buttonHolzfaeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHolzfaeller();
            }
        });
        //Keller
        kellerDialog = new KellerDialog();
        buttonKeller = findViewById(R.id.btn_keller);
        buttonKeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickKeller();
            }
        });

        //Markt
        marktDialog = new MarktDialog();
        buttonMarkt = findViewById(R.id.btn_markt);
        buttonMarkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMarkt();
            }
        });

        //Miliz
        milizDialog = new MilizDialog();
        buttonMiliz = findViewById(R.id.btn_miliz);
        buttonMiliz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMiliz();
            }
        });
        //Mine
        mineDialog = new MineDialog();
        buttonMine = findViewById(R.id.btn_mine);
        buttonMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMine();
            }
        });

        //Schmiede
        schmiedeDialog = new SchmiedeDialog();
        buttonSchmiede = findViewById(R.id.btn_schmiede);
        buttonSchmiede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSchmiede();
            }
        });
        //Werkstatt
        werkstattDialog = new WerkstattDialog();
        buttonWerkstatt = findViewById(R.id.btn_werkstatt);
        buttonWerkstatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickWerkstatt();
            }
        });

        //Gold
        buttonGold = findViewById(R.id.btn_gold);
        buttonGold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGold();
            }
        });

        //Silber
        buttonSilber = findViewById(R.id.btn_silber);
        buttonSilber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSilber();
            }
        });
        //Kupfer
        buttonKupfer = findViewById(R.id.btn_kupfer);
        buttonKupfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickKupfer();
            }
        });

        //Provinz
        buttonProvinz = findViewById(R.id.btn_provinz);
        buttonAnwesen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickProvinz();
            }
        });

        //Anwesen
        buttonAnwesen = findViewById(R.id.btn_anwesen);
        buttonAnwesen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAnwesen();
            }
        });

        //Herzogturm
        buttonHerzogturm = findViewById(R.id.btn_herzogturm);
        buttonHerzogturm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHerzogturm();
            }
        });

        //Fluch
        buttonFluch = findViewById(R.id.btn_fluch);
        buttonFluch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFluch();
            }
        });

    }

    private void onClickGold() {
        Card card = board.getBuyField().pickCard(MoneyType.GOLD);
        if(card == null) {
            // Error => Stapel hat keine Karten mehr
        } else {
            MoneyCard moneyCard = (MoneyCard) card;
        }
    }

    private void onClickSilber() {
        Card card = board.getBuyField().pickCard(MoneyType.SILBER);
        if(card == null) {
            // Error => Stapel hat keine Karten mehr
        } else {
            MoneyCard moneyCard = (MoneyCard) card;
        }
    }

    private void onClickKupfer() {
        Card card = board.getBuyField().pickCard(MoneyType.KUPFER);
        if(card == null) {
            // Error => Stapel hat keine Karten mehr
        } else {
            MoneyCard moneyCard = (MoneyCard) card;
        }
    }

    private void onClickAnwesen() {
        Card card = board.getBuyField().pickCard(EstateType.ANWESEN);
        if(card == null) {
            // Error => Stapel hat keine Karten mehr
        } else {
            EstateCard estateCard = (EstateCard) card;
        }
    }

    private void onClickProvinz() {
        Card card = board.getBuyField().pickCard(EstateType.PROVINZ);
        if(card == null) {
            // Error => Stapel hat keine Karten mehr
        } else {
            EstateCard estateCard = (EstateCard) card;
        }
    }
    private void onClickHerzogturm() {
        Card card = board.getBuyField().pickCard(EstateType.HERZOGTUM);
        if(card == null) {
            // Error => Stapel hat keine Karten mehr
        } else {
            EstateCard estateCard = (EstateCard) card;
        }
    }
    private void onClickFluch() {
        Card card = board.getBuyField().pickCard(EstateType.FLUCH);
        if(card == null) {
            // Error => Stapel hat keine Karten mehr
        } else {
            EstateCard estateCard = (EstateCard) card;
        }
    }

    private void onClickHexe() {
        hexeDialog.show(fragmentManager, "hexeDialog");
    }

    private void onClickBurggraben() {
        burggrabenDialog.show(fragmentManager, "burggrabenDialog");
    }

    private void onClickDorf() {
        dorfDialog.show(fragmentManager, "dorfDialog");
    }

    private void onClickHolzfaeller() {
        holzfaellerDialog.show(fragmentManager, "holzfaellerDialog");
    }

    private void onClickKeller() {
        kellerDialog.show(fragmentManager, "kellerDialog");
    }

    private void onClickMarkt() {
        marktDialog.show(fragmentManager, "marktDialog");
    }

    private void onClickMiliz() {
        milizDialog.show(fragmentManager, "milizDialog");
    }

    private void onClickMine() {
        mineDialog.show(fragmentManager, "mineDialog");
    }
    private void onClickSchmiede() {
        schmiedeDialog.show(fragmentManager, "schmiedeDialog");
    }
    private void onClickWerkstatt() {
        werkstattDialog.show(fragmentManager, "werkstattDialog");
    }



    @Override
    protected void onStart() {
        super.onStart();

        // Handle communication with Server, only send updated to server whenever card is played etc.

        ClientConnector clientConnector = ClientConnector.getClientConnector();
        Game clientGame = clientConnector.getGame();
        //clientConnector.startGame(); // Send Server Message to start game logic
        // TODO display playerlist -> Check features
        // TODO create board and display cards

        // Take from GameHandler getBoard here instead of this
        //board = clientConnector.getGameHandler().getBoard();
        // Currently
        board = new Board();
        hexeDialog.setBoard(board);

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

        clientConnector.registerCallback(ChatMessage.class, msg -> {
            runOnUiThread(() -> {
                String chatMessage = ((ChatMessage) msg).getMessage();
                Toast.makeText(getApplicationContext(), "Nachricht: " + chatMessage, Toast.LENGTH_SHORT).show();
            });
        });
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



    public void openFragment() {
        if(this.chatFragment == null) {
            this.chatFragment = ChatFragment.newInstance();
        }
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                    R.anim.enter_from_right, R.anim.exit_to_right);

            trans.addToBackStack(null);

        trans.add(R.id.chatFragmentContainer, chatFragment, "CHAT_FRAGMENT").commit();

    }

    @Override
    public void onChatMessageArrived(String msg) {
        Toast.makeText(getApplicationContext(), "Nachricht: " + msg, Toast.LENGTH_SHORT).show();
        //this.trans.hide(chatFragment);
        onBackPressed();
    }


}
