package com.group7.dominion.Chat;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.floriankleewein.commonclasses.Chat.ChatMessage;
import com.floriankleewein.commonclasses.Network.ClientConnector;
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

    // gibt an, ob Views mittels ButterKnife injected worden sind
    private boolean viewsInjected;

    // Adapter für die Chatansicht
    private ChatListAdapter chatListAdapter;

    private OnChatMessageArrivedListener mListener;

    private Button backButton;

    // zum Unbinden von Views aus dem Fragment
    private Unbinder unbinder;

    private ClientConnector client;

    private String responseMessage;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @BindView(R.id.user_input_edit_text) EditText userInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View chatFragmentView = inflater.inflate(R.layout.fragment_chat, container, false);

        unbinder = ButterKnife.bind(this, chatFragmentView);
        viewsInjected = true;

        this.client = ClientConnector.getClientConnector();

        this.backButton = chatFragmentView.findViewById(R.id.back_Button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onChatMessageArrived(responseMessage);
                }
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

        if (this.client.isConnected()) {
            this.client.registerClass(ChatMessage.class);

            Thread clientThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    client.getClient().addListener(new Listener() {
                        @Override
                        public void received(Connection connection, Object object) {
                            if (object instanceof ChatMessage) {
                                ChatMessage response = (ChatMessage) object;
                                Log.d("ChatFragment", "Nachricht des anderen Spielers: " + response.getMessage());

                                responseMessage = response.getMessage();

                                if (isAdded() & !response.isSentByMe()) {
                                    getActivity().runOnUiThread(new Runnable() {
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
                }
            });
            clientThread.start();
        } else {
            Toast.makeText(getActivity(), "Not connected", Toast.LENGTH_LONG).show();
        }
    }


    @OnClick(R.id.send_Button)
    public void sendMessage() {

        if(client.isConnected()) {
            ChatMessage sendToOthers = new ChatMessage();
            sendToOthers.setMessage(this.getMessagetoBeSent());
            sendToOthers.setSentByMe(true);

            new SendMessage(sendToOthers).execute();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    // hiermit wird die Nachricht in die View eingebunden und angezeigt
                    chatListAdapter.add(sendToOthers);
                    chatListAdapter.notifyDataSetChanged();
                    getListView().setSelection(chatListAdapter.getCount() - 1);
                }
            });

        } else {
            Toast.makeText(getActivity(), "Message can't be send. No connection.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String getMessagetoBeSent() {
        return userInput.getText().toString();
    }

    @Override
    public void clearInput() {
        userInput.setText("");
    }


    private class SendMessage extends AsyncTask<Void, Void, Boolean> {
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
            }catch (Exception e) {
                e.printStackTrace();
                messageSent = false;
            }
            return messageSent;
        }

        @Override
        protected void onPostExecute(Boolean messageSent) {

            if(messageSent) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        clearInput();

                        System.out.println("CLIENT: Succesfully sent message to others.");
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Message not sent.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public interface OnChatMessageArrivedListener{
        void onChatMessageArrived(String msg);
    }
}