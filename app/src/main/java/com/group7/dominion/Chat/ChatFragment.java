package com.group7.dominion.Chat;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.group7.dominion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    public ChatFragment() {
        // Required empty public constructor
    }

    private EditText userInput;
    private ImageButton send;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View chatFragmentView = inflater.inflate(R.layout.fragment_chat, container, false);

        userInput = chatFragmentView.findViewById(R.id.userInput);
        send = chatFragmentView.findViewById(R.id.sendButton);

        return chatFragmentView;
    }

}
