package com.lfc.zhihuidangjianapp.helper;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2018/8/29
 * @autror: guojian
 * @description:
 */

public class ActivityManager {

    public static List<Activity> mActivites = new ArrayList<>();

    public static void addActivity(Activity act) {
        mActivites.add(act);
    }

    public static void removeActivity(Activity act) {
        mActivites.remove(act);
    }

    public static void removeActivity(Class clazz) {
        for (int i = 0; i < mActivites.size(); i++) {
            Activity act = mActivites.get(i);
            if (act.getClass().getName().equals(clazz.getName())) {
                mActivites.remove(act);
                act.finish();
            }
        }
    }

    public static void finishAll() {
        for (Activity act : mActivites) {
            if (!act.isFinishing()) {
                act.finish();
            }
        }
    }

    public static void finishAllBySize() {
        try {
            for (int i = 0; i < mActivites.size(); i++) {
                mActivites.get(i).finish();
            }
        } catch (IndexOutOfBoundsException e) {
        }
    }

    /**
     * 判断某个界面是否在前台
     * @param context
     * @param className
     * @return
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        android.app.ActivityManager am = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<android.app.ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }


}

