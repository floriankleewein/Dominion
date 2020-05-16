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


        return gameFragmentView;


    }
}
