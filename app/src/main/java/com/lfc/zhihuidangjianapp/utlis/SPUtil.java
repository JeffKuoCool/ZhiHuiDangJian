package com.lfc.zhihuidangjianapp.utlis;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @date: 2018/8/9
 * @autror: guojian
 * @description: SharedPreferences工具类
 */

public class SPUtil {

    private static final String FILE_NAME = "zoom_mark";

    public static void putObject(Context context, String key, Object data) {
        String type = data.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) data);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) data);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) data);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) data);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) data);
        }
        editor.apply();
    }

    public static Object getObject(Context context, String key, Object defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (FILE_NAME, Context.MODE_PRIVATE);
        if (defValue instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof String) {
            return sharedPreferences.getString(key, (String) defValue);
        } else if (defValue instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defValue);
        }
        return defValue;
    }

    public static void remove(Context context, String key){
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }


}
