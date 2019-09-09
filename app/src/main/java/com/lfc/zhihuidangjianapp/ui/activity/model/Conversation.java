package com.lfc.zhihuidangjianapp.ui.activity.model;

import android.content.Context;

import com.hyphenate.chat.EMConversation;

/**
 * 消息列表model
 * @author: guojian
 */
public class Conversation {
    private String chatUsername;
    private EMConversation emConversation;
    private Context context;

    public String getChatUsername() {
        return chatUsername;
    }

    public EMConversation getEmConversation() {
        return emConversation;
    }

    public void setChatUsername(String chatUsername) {
        this.chatUsername = chatUsername;
    }

    public void setEmConversation(EMConversation emConversation) {
        this.emConversation = emConversation;
    }

    public Context getContext() {
        return context;
    }

    public Conversation(String chatUsername, EMConversation emConversation, Context context) {
        this.chatUsername = chatUsername;
        this.emConversation = emConversation;
        this.context = context;
    }
}
