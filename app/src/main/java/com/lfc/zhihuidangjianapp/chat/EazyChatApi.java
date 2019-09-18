package com.lfc.zhihuidangjianapp.chat;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.lfc.zhihuidangjianapp.BuildConfig;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.ui.activity.model.User;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @date: 2018/9/6
 * @autror: guojian
 * @description:
 */

public class EazyChatApi {

    private static final String TAG = "EazyChatApi";

    private static boolean isInit = false;

    public static final String APP_KEY = "1105190516090369#ztzhdj";

    public static void initEasemob(Context context, String appKey) {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(context, pid);
        /**
         * 如果app启用了远程的service，此application:onCreate会被调用2次
         * 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
         * 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
         */
        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {
            // 则此application的onCreate 是被service 调用的，直接返回
            return;
        }
        if (isInit) {
            return;
        }
        EMOptions options = new EMOptions();
        // 设置自动登录
        options.setAutoLogin(true);
        options.setAppKey(appKey);
        EMClient.getInstance().init(context, options);

        isInit = true;
    }

    /**
     * 根据Pid获取当前进程的名字，一般就是当前app的包名
     *
     * @param pid 进程的id
     * @return 返回进程的名字
     */
    private static String getAppName(Context context, int pid) {
        String processName = null;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取聊天服务器信息
     */
    public static void getChatUserInfo(User user, BaseActivity activity) {
        if (user == null) {
            return;
        }
        try {
            loginChat(user.getLoginName(), user.getImPwd(), activity, null);
        } catch (Exception e) {
            Log.e("EazyChatApi", e.getMessage());
        }
    }

    /**
     * 发送会议邀请
     *
     * @param users
     * @param conferenceId
     */
    public static void sendMeeting(List<User> users, String conferenceId, String password) {
        for (User user : users) {
            EMMessage message = EMMessage.createTxtSendMessage("join", user.getLoginName());
            message.setChatType(EMMessage.ChatType.Chat);
            //消息扩展
            message.setAttribute("conferenceId", conferenceId);
            message.setAttribute("password", password);
            message.setAttribute("user", new Gson().toJson(user));
            EMClient.getInstance().chatManager().sendMessage(message);
        }
    }


    public static void loginChat(String username, String password, BaseActivity activity, EMCallBack emCallBack) {
//        password = "zhdj-888888";
//        password = (TextUtils.isEmpty(password) ? "zhdj-888888" : password);
        if (username == null || password == null) {
            activity.toast("用户名密码错误！");
            return;
        }
        EMClient.getInstance().login(username, password, emCallBack == null ? new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("main", "登录聊天服务器成功！");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, code + "  " + message);
                        if (BuildConfig.DEBUG && code != 200)
                            activity.toast("登录聊天服务器失败！");
                    }
                });
                if (code == 200) {

                }
            }
        } : emCallBack);
    }

    public static List<EMMessage> getMessageFromId(String userId, String msgId, int pagesize) {
        EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(userId);
        return emConversation == null ? null : emConversation.loadMoreMsgFromDB(msgId, pagesize);
    }

    public static void clearHistory() {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        for (Map.Entry<String, EMConversation> entry : conversations.entrySet()) {
            EMClient.getInstance().chatManager().deleteConversation(entry.getKey(), true);
        }
    }

    public static List<EMMessage> getMessageList(String userId, int pageSize) {
        EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(userId);
        if (emConversation == null) {
            return null;
        }
        List<EMMessage> messages = emConversation.getAllMessages();
//        if (messages.size() < pageSize && messages.size() > 0) {
//            messages.addAll(emConversation.loadMoreMsgFromDB(messages.get(messages.size() - 1).getMsgId(), pageSize - messages.size()));
//        }
        return messages;
    }


}
