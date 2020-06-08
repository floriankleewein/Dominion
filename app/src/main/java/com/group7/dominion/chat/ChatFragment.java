package com.group7.dominion.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.floriankleewein.commonclasses.chat.ChatMessage;
import com.floriankleewein.commonclasses.chat.Pair;
import com.floriankleewein.commonclasses.chat.RecChatListMsg;
import com.floriankleewein.commonclasses.network.ClientConnector;
import com.group7.dominion.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends ListFragment implements UserInputHandler {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    // Adapter für die Chatansicht
    private ChatListAdapter chatListAdapter;

    private OnChatMessageArrivedListener mListener;

    // zum Unbinden von Views aus dem Fragment
    private Unbinder unbinder;

    private ClientConnector client;

    private String responseMessage;

    private String playerName;

    private boolean isRestorable = false;

    private List<com.floriankleewein.commonclasses.chat.Pair> messageList;


    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @BindView(R.id.user_input_edit_text)
    EditText userInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("Chat", "CHATFRAGMENT ONCREATEVIEW");
        // Inflate the layout for this fragment
        View chatFragmentView = inflater.inflate(R.layout.fragment_chat, container, false);


        Log.i("Chat", "IS RESTORED: " + isRestorable);

        unbinder = ButterKnife.bind(this, chatFragmentView);


        this.playerName = getArguments().getString("playerName");

        this.client = ClientConnector.getClientConnector();

        Button backButton;
        backButton = chatFragmentView.findViewById(R.id.back_Button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onChatMessageArrived(responseMessage);
                }
            }
        });


        Thread chatMessageRecThread = new Thread(() -> {
            if (isRestorable) {

                client.getChatMessages();
                client.getClient().addListener(new Listener() {
                    @Override
                    public void received(Connection connection, Object object) {
                        Log.i("Chat", "RECEIVED MESSAGE THREAD");

                        if (object instanceof RecChatListMsg) {

                            Log.i("Chat", "RECEIVED CHAT MESSAGES FROM SERVER");
                            Log.i("Chat", "FROM CONNECTION: " + connection.getID());
                            RecChatListMsg msg = (RecChatListMsg) object;
                            messageList = msg.getMessages();
                            Log.i("Chat", "OBJECT: ");
                        }
                    }

                });
            }
        });

        return chatFragmentView;
    }

    @Override
    public void onAttach(@Nullable Context context) {
        super.onAttach(context);

        if (context instanceof OnChatMessageArrivedListener) {
            mListener = (OnChatMessageArrivedListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement OnChatMessageArrivedListener");
        }
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

        Log.i("Chat","CHATFRAGMENT ONACTIVITYCREATED");

        //Wiederherstellen der Chat Messages

        if (isRestorable) {

            if (isAdded() & messageList != null) {

               Log.i("Chat","restore chat messages");
               Log.i("Chat", "list size: " + messageList.size());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for (Pair chatMessagePair : messageList) {

                            //prüfe, ob die Nachricht von mir selbst gesendet wurde
                            if (chatMessagePair.getPlayerId() == client.getClient().getID()) {
                                chatMessagePair.getChatMessage().setSentByMe(true);
                                chatListAdapter.add(chatMessagePair.getChatMessage());
                            } else {
                                chatMessagePair.getChatMessage().setSentByMe(false);
                                chatListAdapter.add(chatMessagePair.getChatMessage());
                            }
                        }
                    }
                });
            }
        }

        if (this.client.isConnected()) {

            client.getClient().addListener(new Listener() {
                @Override
                public void received(Connection connection, Object object) {
                    if (object instanceof ChatMessage) {
                        ChatMessage response = (ChatMessage) object;
                        Log.d("ChatFragment", "Nachricht des anderen Spielers: " + response.getMessage());

                        responseMessage = response.getMessage();

                       Log.i("Cheat","CHAT LIST ADAPTER SIZE: " + chatListAdapter.getCount());

                        if (isAdded() & !response.isSentByMe()) {

                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // hiermit wird die Nachricht in die View eigebunden und angezeigt
                                    chatListAdapter.add(response);
                                    chatListAdapter.notifyDataSetChanged();
                                    getListView().setSelection(chatListAdapter.getCount() - 1);
                                }
                            });
                        }
                    }
                }
            });

        } else {
            Toast.makeText(getActivity(), "Not connected", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Chat","CHATFRAGMENT ONRESUME");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Chat","CHATFRAGMENT ONDESTROY");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Chat","CHATFRAGMENT ONPAUSE");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Chat","CHATFRAGMENT DETACHED");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(this.getClass().toString(), "In Callback-Methode: onSaveInstanceState()");
    }

    @OnClick(R.id.send_Button)
    public void sendMessage() {

        if (client.isConnected()) {
            ChatMessage sendToOthers = new ChatMessage();
            sendToOthers.setMessage(this.getMessageToBeSent());
            sendToOthers.setSentByMe(true);
            sendToOthers.setPlayerName(playerName);

            new SendMessage(sendToOthers).execute();

            getActivity().runOnUiThread(() -> {

                // hiermit wird die Nachricht in die View eingebunden und angezeigt
                chatListAdapter.add(sendToOthers);
                chatListAdapter.notifyDataSetChanged();
                getListView().setSelection(chatListAdapter.getCount() - 1);
            });

        } else {
            Toast.makeText(getActivity(), "Message can't be send. No connection.", Toast.LENGTH_SHORT).show();
        }

        this.isRestorable = true;

    }

    @Override
    public String getMessageToBeSent() {
        return userInput.getText().toString();
    }

    @Override
    public void clearInput() {
        userInput.setText("");
    }


    //realisiert das Senden einer ChatMessage mit Hilfe eines ASYNC TASK
    private class SendMessage extends AsyncTask<Void, Void, Boolean> {

        //Nachricht, die versendet wird
        private ChatMessage sendMessage;

        SendMessage(ChatMessage msg) {
            this.sendMessage = msg;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean messageSent;

            try {
                client.sendChatMessage(sendMessage);
                messageSent = true;
            } catch (Exception e) {
                e.printStackTrace();
                messageSent = false;
            }
            return messageSent;
        }

        @Override
        protected void onPostExecute(Boolean messageSent) {

            if (messageSent) {
                getActivity().runOnUiThread(() -> {
                    clearInput();

                    Log.i("Chat","CLIENT: Succesfully sent message to others.");
                });
            } else {
                Toast.makeText(getActivity(), "Message not sent.", Toast.LENGTH_LONG).show();
            }
        }
    }

    //Callback, um zum Spiel zurückkehren zu können
    public interface OnChatMessageArrivedListener {
        void onChatMessageArrived(String msg);
    }
}