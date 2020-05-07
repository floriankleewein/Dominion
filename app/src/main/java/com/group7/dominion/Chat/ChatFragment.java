package com.group7.dominion.Chat;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.floriankleewein.commonclasses.Chat.ChatMessage;
import com.group7.dominion.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends ListFragment implements UserInputHandler {

    public ChatFragment() {
        // Required empty public constructor
    }


    private boolean viewsInjected;
    // gibt an, ob Views mittels ButterKnife injected worden sind

    private ChatListAdapter chatListAdapter;
    // Adapter für die Chatansicht

    private Unbinder unbinder;
    // zum Unbinden von Views aus dem Fragment

    @BindView(R.id.user_input_edit_text) EditText userInput;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View chatFragmentView = inflater.inflate(R.layout.fragment_chat, container, false);

        unbinder = ButterKnife.bind(this, chatFragmentView);
        viewsInjected = true;

        return chatFragmentView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.chatListAdapter = new ChatListAdapter(getActivity(), R.layout.chat_fragment_row);
        setListAdapter(this.chatListAdapter);
    }


    @OnClick(R.id.send_Button)
    public void sendMessage() {
        ChatMessage msg = new ChatMessage();
        msg.setMessage(this.getMessagetoBeSent());
        msg.setSentByMe(true);
        // zurzeit default-mäßig zum Testen eine ChatMessage erstellen, die von mir gesendet wurde

        chatListAdapter.add(msg);
        chatListAdapter.notifyDataSetChanged();
        getListView().setSelection(chatListAdapter.getCount() -1);
        // hiermit wird die Nachricht in die View eingebunden und angezeigt

        this.clearInput();
        // lösche jetzigen Input
    }

    @Override
    public String getMessagetoBeSent() {
        return userInput.getText().toString();
    }

    @Override
    public void clearInput() {
        userInput.setText("");
    }
}