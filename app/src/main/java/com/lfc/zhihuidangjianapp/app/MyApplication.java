package com.lfc.zhihuidangjianapp.app;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.hyphenate.chatuidemo.DemoApplication;
import com.lfc.zhihuidangjianapp.bean.LoginBean;
import com.lfc.zhihuidangjianapp.chat.EazyChatApi;
import com.lfc.zhihuidangjianapp.image.ImageLoader;
import com.lfc.zhihuidangjianapp.ui.activity.model.UserInfo;
import com.lfc.zhihuidangjianapp.utlis.SPUtil;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

public class MyApplication extends DemoApplication {

    private static String deptNumber;

    private volatile static MyApplication sBaseApplication;
    private static MyApplication app;

    public static MyApplication getApp() {
        return app;
    }

    private static UserInfo mUserInfo;

    public static LoginBean.DataBean getLoginBean() {
        String aoth = SPUtil.getObject(getAppContext(), UserConstants.AUTHORIZATION, "").toString();
        LoginBean.DataBean loginBean = new Gson().fromJson(aoth, LoginBean.DataBean.class);
        if (loginBean == null) return new LoginBean.DataBean();
        return loginBean;
    }

    public static void setLoginBean(LoginBean.DataBean loginBean) {
        SPUtil.putObject(getAppContext(), UserConstants.AUTHORIZATION, new Gson().toJson(loginBean));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        sBaseApplication = this;
        // 初始化吐司工具类
        ToastUtils.init(this);
        // 初始化图片加载器
        ImageLoader.init(this);

        // 环信初始化
//        DemoHelper.getInstance().init(app);
        EazyChatApi.initEasemob(getAppContext(), EazyChatApi.APP_KEY);

        //ARouter
        ARouter.openLog();     // Print log
        ARouter.openDebug();
        ARouter.init(this);
        //bugly
        //CrashReport.initCrashReport(getApplicationContext(), "8e9f06cfbc", false);
        Bugly.init(getApplicationContext(), AppConstants.getBuglyAppId(), false);

    }

    public static String getDeptNumber() {
        return deptNumber;
    }

    public static void setDeptNumber(String deptNumber) {
        MyApplication.deptNumber = deptNumber;
    }

    public static Context getAppContext() {
        if (sBaseApplication == null) {
            synchronized (MyApplication.class) {
                if (sBaseApplication == null) {
                    sBaseApplication = new MyApplication();
                }
            }
        }
        return sBaseApplication;
    }

    public static MyApplication getApplication() {
        return sBaseApplication;
    }

    public static UserInfo getmUserInfo() {
        if (mUserInfo == null)
            mUserInfo = new UserInfo();
        return mUserInfo;
    }

    public static void setmUserInfo(UserInfo mUserInfo) {
        if (mUserInfo == null) return;
        MyApplication.mUserInfo = mUserInfo;
    }
}
