package com.lfc.zhihuidangjianapp.ui.activity;

import android.content.Intent;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chatuidemo.conference.ConferenceActivity;
import com.hyphenate.exceptions.HyphenateException;
import com.lfc.zhihuidangjianapp.app.Constants;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.AppConferenceActivity;

import java.util.List;

/**
 * @date: 2019-08-17
 * @autror: guojian
 * @description:
 */
public abstract class EazyChatListenerActivity extends BaseActivity {

    /**
     * 接受message
     */
    protected EMMessageListener emMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (list != null && list.size() != 0) {
                        EMMessage emMessage = list.get(0);
                        if (emMessage.getChatType() == EMMessage.ChatType.Chat) {
                            try {

                                Intent intent = new Intent(getActivity(), AppConferenceActivity.class);
                                intent.putExtra("conferenceId", emMessage.getStringAttribute("conferenceId"));
                                intent.putExtra("password", emMessage.getStringAttribute("password"));
                                intent.putExtra("enterType", AppConferenceActivity.TYPE_RECEIVE);
                                startActivity(intent);
                               ConferenceActivity.receiveConferenceCall(getActivity(), emMessage.getStringAttribute("conferenceId"), Constants.CONFERENCE_PASSWORD, "", null);
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
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
    };

}
