package com.group7.dominion;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.floriankleewein.commonclasses.Cards.ActionCard;
import com.floriankleewein.commonclasses.Cards.ActionType;
import com.floriankleewein.commonclasses.Cards.Card;
import com.floriankleewein.commonclasses.Cards.EstateCard;
import com.floriankleewein.commonclasses.Cards.EstateType;
import com.floriankleewein.commonclasses.Cards.MoneyCard;
import com.floriankleewein.commonclasses.Cards.MoneyType;
import com.group7.dominion.Card.CardUI;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    private LinearLayout linearLayout;
    private boolean isCreateBoard;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View gameFragmentView = inflater.inflate(R.layout.fragment_game, container, false);

        if(linearLayout != null) {
            getActivity().setContentView(linearLayout);
        } else {
            linearLayout = gameFragmentView.findViewById(R.id.rootContainer);
        }

        return gameFragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(!isCreateBoard) {
            showBoard();
            isCreateBoard = true;
        }
    }

    private void showBoard() {
        // Kartentypen von server
        Card actionCard = new ActionCard(1, ActionType.BURGGRABEN);
        //Card estateCard = new EstateCard(1,1, EstateType.PROVINZ);
        //Card moneyCard = new MoneyCard(1, 1, MoneyType.GOLD);
        //ToDo: get all cards from server

        // UI Karten Darstellung
        /*
        CardUI cardUI1 = new CardUI(2, estateCard, getActivity(), linearLayout, 1.0f);
        cardUI1.init();
        cardUI1.showImage();

        CardUI cardUI2 = new CardUI(3, moneyCard, getActivity(), linearLayout, 1.0f);
        cardUI2.init();
        cardUI2.showImage();
        */

        int idCounter = 0;
        List<CardUI> cards = new ArrayList<>();
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (int y = 0; y < 3; y++) {
            LinearLayout row = new LinearLayout(getActivity());
            row.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int x = 0; x < 4; x++) {
                CardUI cardUI = new CardUI(idCounter, x, y, actionCard, getActivity(), row, 0.5f);
                cardUI.init();
                cardUI.showImage();
                cards.add(cardUI);
                idCounter++;
            }
            linearLayout.addView(row);
        }

        // for
        // if cards.get(0).getImageButton().getId() == id
        // if cards.get(0).getxPosition() == x && cards.get(0).getyPosition() == y
        // getActivity().findViewById()
    }
}
