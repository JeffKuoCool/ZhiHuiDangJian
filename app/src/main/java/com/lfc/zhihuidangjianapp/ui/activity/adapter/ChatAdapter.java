package com.lfc.zhihuidangjianapp.ui.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hyphenate.chat.EMMessage;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.ui.activity.items.ChatItemView;
import com.lfc.zhihuidangjianapp.ui.activity.model.Chat;

import java.util.ArrayList;
import java.util.List;

import cn.nekocode.items.view.RecyclerViewItemView;

/**
 * @date: 2018/8/7
 * @autror: guojian
 * @description:
 */

public class ChatAdapter extends BaseRecyclerViewAdapter {

    /**
     * 我的消息
     */
    public static final int MY_INFOMATION = 0;

    /**
     * 别人的消息
     */
    public static final int OTHER_INFOMATION = 1;
    /**
     * tip
     */
    public static final int TIP = 2;

    /**
     * 欢迎语
     */
    public static final int COME_IN = 3;

    private BaseActivity mContext;

    private List<Chat> mMassegeList = new ArrayList<>();

    private EMMessage.ChatType mChatType;

    private String mPosterChatId;

    public ChatAdapter(List<Chat> data, BaseActivity context) {
        this(data, context, EMMessage.ChatType.Chat, "");
    }

    public ChatAdapter(List<Chat> data, BaseActivity context, EMMessage.ChatType chatType, String posterChatId) {
        this.mMassegeList = data;
        this.mContext = context;
        this.mChatType = chatType;
        this.mPosterChatId = posterChatId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mChatType == EMMessage.ChatType.ChatRoom) {
//            return new ChatRoomItemView().setContext(mContext).setPosterChatId(mPosterChatId).onCreateViewHolder(this, null, parent);
        } else {
            return new ChatItemView().setContext(mContext).onCreateViewHolder(this, null, parent);
        }
        return new ChatItemView().setContext(mContext).onCreateViewHolder(this, null, parent);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RecyclerViewItemView view = ((RecyclerViewItemView.InnerViewHolder) holder).outter();
        view._onBindData(mMassegeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMassegeList.size();
    }

    public void addItem(Chat chat) {
        notifyItemInserted(mMassegeList.size());
        mMassegeList.add(chat);
    }

    public void notify(List<Chat> newChat) {
        if (newChat == null || newChat.isEmpty()) {
            return;
        }
        mMassegeList.addAll(0, newChat);
        notifyItemRangeChanged(0, newChat.size());
    }

    public EMMessage getLastMessage(){
        return mMassegeList.isEmpty()?null:mMassegeList.get(mMassegeList.size()-1).getMessage();
    }

    public String getMsgId() {
        try {
            return mMassegeList.get(0).getMessage().getMsgId();
        } catch (Exception e) {
            return null;
        }
    }

}
