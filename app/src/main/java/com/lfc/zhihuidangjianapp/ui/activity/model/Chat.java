package com.lfc.zhihuidangjianapp.ui.activity.model;

import com.hyphenate.chat.EMMessage;
import com.lfc.zhihuidangjianapp.base.BaseActivity;


/**
 * @date: 2018/9/7
 * @autror: guojian
 * @description:
 */

public class Chat {
    private EMMessage mMessage;
    //0我的消息 1别人的消息
    private int type;

    private String mHeadUrl;

    private String mUsername;

    //0 文本 1语音
    private int msgType;

    private String userNickname;

    private boolean isShowMessageTime = false;

    private BaseActivity context;

    public Chat(EMMessage message, int type, String headUrl) {
        mMessage = message;
        this.type = type;
        this.mHeadUrl = headUrl;
    }

    public Chat(EMMessage message, int type, String headUrl, String username) {
        mMessage = message;
        this.type = type;
        this.mHeadUrl = headUrl;
        this.mUsername = username;
    }

    public boolean isShowMessageTime() {
        return isShowMessageTime;
    }

    public void setShowMessageTime(boolean showMessageTime) {
        isShowMessageTime = showMessageTime;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getMsgType() {
        return msgType;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getHeadUrl() {
        return mHeadUrl;
    }

    public EMMessage getMessage() {
        return mMessage;
    }

    public int getType() {
        return type;
    }

    public BaseActivity getContext() {
        return context;
    }

    public void setContext(BaseActivity context) {
        this.context = context;
    }
}
