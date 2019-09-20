package com.lfc.zhihuidangjianapp.app;

import com.lfc.zhihuidangjianapp.BuildConfig;

/**
 * @date: 2019-09-20
 * @autror: guojian
 * @description:
 */
public class AppConstants {
    private static final String BUGLY_DEBUG = "8e9f06cfbc";
    private static final String BUGLY_RELEASE = "91fb4b0231";

    public static String getBuglyAppId() {
        return BuildConfig.DEBUG ? BUGLY_DEBUG : BUGLY_RELEASE;
    }
}
