package com.lfc.zhihuidangjianapp.utlis;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author snkso <snkso@foxmail.com>
 */
public class DateUtils {

    /**
     * 返回unix时间戳 (1970年至今的秒数)
     *
     * @return r
     */
    public static long getUnixStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 得到昨天的日期
     *
     * @return r
     */
    public static String getYestoryDate() {
        return getSomeDate(-1, "");
    }

    /**
     * 得到距离今天的某天日期
     *
     * @param someDay 如果是将来的日期比如今后10天则传10即可，如果是今天之前的日期，则传负数比如前5天 -5
     * @return r
     */
    public static String getSomeDate(int someDay, String formart) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, someDay);
        if (TextUtils.isEmpty(formart)) formart = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(formart);
        String tarDay = sdf.format(calendar.getTime());
        return tarDay;
    }

    /**
     * 得到今天的日期
     *
     * @return r
     */
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp t
     * @return r
     */
    public static String timeStampToStr(long timeStamp, String formart) {
        if (TextUtils.isEmpty(formart)) formart = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(formart);
        String date = sdf.format(convertTime2Millis(timeStamp));
        return date;
    }


    private static long convertTime2Millis(long timeStamp) {
        return String.valueOf(timeStamp).length() > 10 ? timeStamp : timeStamp * 1000;
    }

    /**
     * 得到日期   yyyy-MM-dd
     *
     * @param timeStamp 时间戳 这里是精确到毫秒
     * @return r
     */
    public static String formatDate(long timeStamp, String formart) {
        if (null == formart || TextUtils.isEmpty(formart)) formart = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(formart);
        String date = sdf.format(convertTime2Millis(timeStamp));
        return date;
    }

    /**
     * 得到时间  HH:mm:ss
     *
     * @param timeStamp 时间戳
     * @return r
     */
    public static String getTime(long timeStamp) {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(convertTime2Millis(timeStamp));
        String[] split = date.split("\\s");
        if (split.length > 1) {
            time = split[1];
        }
        return time;
    }

    /**
     * 日期转时间戳秒数
     *
     * @param oDate   date
     * @param formart formart
     * @return long time
     */
    public static long date2TimeStamp(String oDate, String formart) {
        if (TextUtils.isEmpty(formart)) formart = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(formart);
        try {
            Date date = format.parse(oDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp t
     * @return r
     */
    public static String convertTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - convertTime2Millis(timeStamp) / 1000;

        if (time < 0) {
            // 明天/后天，不是则直接返回格式化之后的时间字符串
            String formart = "yyyy-MM-dd";
            // 明天时间戳
            long tDay = date2TimeStamp(getSomeDate(1, formart), formart);
            // 目标时间戳
            long nDay = date2TimeStamp(timeStampToStr(convertTime2Millis(timeStamp), formart), formart);

            if ((tDay - nDay) / 1000 == 0) {
                return "明天";
            } else if ((tDay - nDay) / 1000 == -86400) {
                return "后天";
            }

            return timeStampToStr(timeStamp, formart);
        }
        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 2) {//昨天
            return  "昨天";
        } else if (time >= 3600 * 24 * 2 && time < 3600 * 24 * 7) {
            return time / 3600 / 24  + "天前";
        }  else {
            return timeStampToStr(timeStamp, "");
        }

    }

    /**
     * 将一个时间戳转换成提示性时间字符串，(多少分钟)
     *
     * @param timeStamp t
     * @return r
     */
    public static String timeStampToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;
        return time / 60 + "";
    }

    /**
     * 获取年
     * @return
     */
    public static int getYear(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.YEAR);
    }
    /**
     * 获取月
     * @return
     */
    public static int getMonth(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.MONTH)+1;
    }
    /**
     * 获取日
     * @return
     */
    public static int getDay(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.DATE);
    }
    /**
     * 获取时
     * @return
     */
    public static int getHour(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.HOUR);
    }
    /**
     * 获取分
     * @return
     */
    public static int getMinute(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.MINUTE);
    }
}
