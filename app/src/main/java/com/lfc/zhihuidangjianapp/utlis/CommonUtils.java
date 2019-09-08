package com.lfc.zhihuidangjianapp.utlis;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.lfc.zhihuidangjianapp.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class CommonUtils {
    private static final DecimalFormat DF1 = new DecimalFormat("0.##");

    private static double EARTH_RADIUS = 6378.137;


    public static Spannable priceSpan(Context context, String unit, String price, int color) {
        final SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(unit + " ", new AbsoluteSizeSpan(12, true), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(price, new AbsoluteSizeSpan(16, true), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new StyleSpan(Typeface.BOLD), builder.length() - price.length(),
                builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)),
                0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }

    public static Spannable priceSpanSmall(Context context, String unit, String price, int color) {
        final SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(unit + " ", new AbsoluteSizeSpan(12, true), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(price, new AbsoluteSizeSpan(12, true), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new StyleSpan(Typeface.BOLD), builder.length() - price.length(),
                builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)),
                0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }

    /**
     * 设置字体颜色，仅支持设置拼接中的后缀字体颜色
     *
     * @param context context
     * @param pre     前缀
     * @param text    text
     * @param color   color
     * @return builder
     */
    public static Spannable setTextColor(@NonNull Context context, @NonNull String pre, @NonNull String text, @NonNull int color) {
        final SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(pre);
        text = TextUtils.isEmpty(text) ? "暂无" : text;
        builder.append(text);
        builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), pre.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    /**
     * 格式化播放量
     *
     * @param playNum 播放量
     * @return 格式化之后
     */
    public static String formatPlayNum(double playNum) {
        // 最小单位是万，小于万的直接返回
        int denominator = 10000;
        if (playNum < denominator) return String.valueOf((int) playNum);
        double r = formatNumber(playNum, denominator);
        if (r > denominator) {
            return formatNumber(r, denominator) + "亿";
        }
        return ((int) r) > 0 ? r + "万" : String.valueOf(r);
    }


    public static double formatNumber(double d1, double d2) {
        int scale = 2;
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static String formatTime(long minute) {
        long hours = minute / 60;
        minute = minute % 60;
        long second = minute / 60;
        return (hours < 10 ? "0" + hours : hours) + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param time   精确到毫秒的字符串
     * @param format 格式化成目标
     * @return 格式化以后日期字符串
     */
    public static String timeStamp2Date(String time, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.parseLong(time)));
    }

    /**
     * 日期字符串转换时间戳
     *
     * @param time
     * @param format
     * @return
     */
    public static long date2TimeStamp(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 时间戳转成 今天明天和后天
     *
     * @param time time
     * @return day
     */
    public static String time2(String time) {
        return "";
    }

    /**
     * Date 格式化成字符串
     *
     * @param date   date
     * @param format format
     * @return 格式化以后日期字符串
     */
    public static String date2Str(Date date, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }


    public static String getAppName(@NonNull Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }

    public static int getAppVersion(@NonNull Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }


    /**
     * check if sdcard exist
     *
     * @return
     */
    public static boolean isSdcardExist() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 随机取色
     *
     * @return
     */
    public static int getRandomColor() {
        int[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.CYAN, Color.BLACK, Color.DKGRAY};
        int i = ((int) (Math.random() * 10)) % colors.length;
        return colors[i];
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度计算距离
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 距离
     */
    public static String getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        int distance = (int) s;
        if (distance < 1000) {
            return distance + " m";
        } else {
            return distance / 1000 + " km";
        }
    }


}
