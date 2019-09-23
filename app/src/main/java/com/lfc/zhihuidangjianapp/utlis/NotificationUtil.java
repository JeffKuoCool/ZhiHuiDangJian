package com.lfc.zhihuidangjianapp.utlis;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.chat.EazyChatApi;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.service.NotificationClickReceiver;
import com.lfc.zhihuidangjianapp.ui.activity.model.User;
import com.lfc.zhihuidangjianapp.ui.activity.model.UserInfo;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2018/9/13
 * @autror: guojian
 * @description:
 */

public class NotificationUtil {
    protected static int notifyID = 0525; // start notification id
    protected static int foregroundNotifyID = 0555;    //当前app是不是在用户可是界面
    private AndroidLifecycleScopeProvider mScopeProvider;
    public static final int ACTIVITY_TOP = 1;
    public static final int ACTIVITY_HAS = 2;
    public static final int ACTIVITY_NULL = 3;
    private static final String USER = "user";


    /**
     * notify打开应用进入聊天页
     *
     * @param emMessage
     * @param context
     * @param activity
     */
    public static void send(EMMessage emMessage, Context context, Class activity) {
        getUserInfo(emMessage, activity, context);
    }

    private static void getUserInfo(EMMessage emMessage, Class activity, Context context) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", emMessage.getFrom());
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryUserByUserId(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<UserInfo>(context) {
                    @Override
                    protected void onNext(UserInfo response) {
                        if (response != null) {
                            notidfy(response.getUser(), activity, emMessage, context);
                        }
                    }

                    @Override
                    protected void onError(Throwable e) {
                        Log.e("onError=", e.getMessage());
                    }
                }.actual());

    }


    private static void notidfy(User user, Class activity, EMMessage emMessage, Context context) {
        if (user == null) {
            return;
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        Intent intent;
        PendingIntent pendingIntent;
        if (!isBackground(context)) {
            intent = new Intent(context, activity);
            intent.putExtra(USER, user.getLoginName());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(
                    context,
                    notifyID,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

        } else {
            intent = new Intent(context, NotificationClickReceiver.class);
            intent.putExtra(USER, user.getLoginName());
            pendingIntent = PendingIntent.getBroadcast(context, notifyID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setContentTitle(user.getSealName());// 设置通知栏标题
        EMMessageBody messageBody = emMessage.getBody();
        if (messageBody instanceof EMTextMessageBody)
            mBuilder.setContentText(((EMTextMessageBody) messageBody).getMessage());
        mBuilder.setSmallIcon(R.mipmap.img_iconlogo);// 设置通知小ICON（5.0必须采用白色透明图片）
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.img_iconlogo));// 设置通知大ICON
        mBuilder.setWhen(System.currentTimeMillis());// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT); // 设置该通知优先级
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);//在任何情况下都显示，不受锁屏影响。
        mBuilder.setAutoCancel(true);// 设置这个标志当用户单击面板就可以让通知将自动取消
        mBuilder.setOngoing(false);// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
        // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用NotificationCompat.DEFAULT_ALL属性，可以组合
        mBuilder.setVibrate(new long[]{0, 100, 500, 100});//振动效果需要振动权限

        Uri defaultSoundUrlUri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); //声音

        mBuilder.setSound(defaultSoundUrlUri);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);//闪灯

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        //Notification notification = mBuilder.getNotification();//API 11

        Notification notification = mBuilder.build();//API 16

        mNotificationManager.notify(1, notification);
    }


    /**
     * notify打开应用
     *
     * @param context
     * @param message
     * @param isForeground
     */
    public static void sendNotification(Context context, String message, boolean isForeground) {

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        try {
            String notifyText = message;
            PackageManager packageManager = context.getPackageManager();
            String appName = (String) packageManager
                    .getApplicationLabel(context.getApplicationInfo());

            // notification title
            String contentTitle = appName;
            String packageName = context.getApplicationInfo().packageName;

            Uri defaultSoundUrlUri = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            // create and send notification
            android.support.v4.app.NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(context)
                    .setSmallIcon(context.getApplicationInfo().icon)
                    .setSound(defaultSoundUrlUri)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true);

            Intent msgIntent = context.getPackageManager()
                    .getLaunchIntentForPackage(packageName);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    notifyID,
                    msgIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder.setPriority(NotificationCompat.PRIORITY_MAX); // 设置该通知优先级
            mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);//在任何情况下都显示，不受锁屏影响。
            mBuilder.setContentTitle(contentTitle);
            mBuilder.setTicker(notifyText);
            mBuilder.setContentText(notifyText);
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setFullScreenIntent(pendingIntent, true);
            mBuilder.setOnlyAlertOnce(false);
            mBuilder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);//闪灯
            Notification notification = mBuilder.build();

            if (isForeground) {
                notificationManager.notify(foregroundNotifyID, notification);
                notificationManager.cancel(foregroundNotifyID);
            } else {
                notificationManager.notify(notifyID, notification);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }


}
