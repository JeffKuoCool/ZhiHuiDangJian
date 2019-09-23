package com.lfc.zhihuidangjianapp.chat;

import android.content.Context;

import com.hwangjr.rxbus.RxBus;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMMessage;
import com.lfc.zhihuidangjianapp.helper.ActivityManager;
import com.lfc.zhihuidangjianapp.ui.activity.chat.ChatActivity;
import com.lfc.zhihuidangjianapp.utlis.NotificationUtil;

import java.util.List;


/**
 * @date: 2018/9/13
 * @autror: guojian
 * @description:
 */

public class ChatMessageListener implements EMMessageListener {

    private Context mContext;

    public ChatMessageListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        //在聊天页面不推送消息
        if (ActivityManager.isForeground(mContext, ChatActivity.class.getName())) {
            return;
        }
        try {
            EMMessage emMessage = list.get(0);
            if(emMessage.getChatType()== EMMessage.ChatType.Chat) {
                NotificationUtil.send(emMessage, mContext, ChatActivity.class);
                RxBus.get().post(emMessage);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }
}
