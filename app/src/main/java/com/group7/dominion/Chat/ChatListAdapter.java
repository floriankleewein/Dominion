package com.group7.dominion.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.floriankleewein.commonclasses.Chat.ChatMessage;

public class ChatListAdapter<convertView> extends ArrayAdapter<ChatMessage> {

    private Context context;
    // context in dem sich die Chatliste befindet
    private int layoutResourceId;

    public ChatListAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = provideConvertView(convertView);
        ChatListAdapterViewHolder chatListAdapterViewHolder = provideChatListViewHolder(convertView);

        ChatMessage chatMsg = getItem(position);
        // Temporäres Testen, ob eine Nachricht in der ChatListView angezeigt wird
        // ChatMessage ist hierbei die Message, die zwischen den Spielern ausgetauscht wird
        // getItem(position) liefert dabei die Message, welche sich an der gegeben Position
        // an der View befindet

        if(chatMsg.isSentByMe()) {
            chatListAdapterViewHolder.messageReceivedLayout.setVisibility(View.GONE);
            chatListAdapterViewHolder.messageSentLayout.setVisibility(View.VISIBLE);
            chatListAdapterViewHolder.messageSentTextView.setText(chatMsg.getMessage());
        } else {
            chatListAdapterViewHolder.messageReceivedLayout.setVisibility(View.VISIBLE);
            chatListAdapterViewHolder.messageSentLayout.setVisibility(View.GONE);
            chatListAdapterViewHolder.messageSentTextView.setText(chatMsg.getMessage());
        }
        return convertView;
    }



    private LayoutInflater getLayoutInflaterService() {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    private ChatListAdapterViewHolder provideChatListViewHolder(View conView) {
        if(conView.getTag() == null) {
            conView.setTag(new ChatListAdapterViewHolder(conView));
        }
        return  (ChatListAdapterViewHolder) conView.getTag();
    }



    private View provideConvertView(View convertView) {
        if (convertView == null) {
            convertView = getLayoutInflaterService().inflate(layoutResourceId, null);
        }
        return convertView;
    }
}
