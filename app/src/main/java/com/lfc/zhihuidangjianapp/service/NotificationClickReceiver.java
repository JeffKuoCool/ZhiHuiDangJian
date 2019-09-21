package com.lfc.zhihuidangjianapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lfc.zhihuidangjianapp.ui.activity.Act_Main;

/**
 * @date: 2018/9/14
 * @autror: guojian
 * @description:
 */

public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent2 = new Intent(context, Act_Main.class);
        context.startActivity(intent2);
//        NotificationUtil.startApp(context, intent.getParcelableExtra(ActivityRouter.USER), ChatActivity.class);
    }
}
