package com.lh16808.app.lhds.utils.dataUtils;

import android.annotation.SuppressLint;

import com.lh16808.app.lhds.utils.AppLog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式化类
 *
 * @author zhouyujing
 * @version v1.0
 * @e-mail 1032668839@qq.com
 * @create-time 2015年10月19日 下午4:19:34
 */

@SuppressLint("SimpleDateFormat")
public class DateFormatUtils {
    private static final String TAG = "DateFormatUtils";

    /**
     * 獲取當前時間
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    /**
     * 獲取當前時間
     *
     * @return
     */
    public static String getCurrentTimeNots() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    /**
     * 獲取當前時間
     *
     * @return
     */
    public static String getCurrentTime2() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    /**
     * 獲取當前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    /**
     * 转为yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatWithYMD(long date) {
        if (date == 0) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(date));
    }

    /**
     * 转为yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatWithYMD2(long date) {
        if (date == 0) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(new Date(date));
    }


    /**
     * 返回时间差
     *
     * @param date
     * @return
     */
    public static String deltaT(long date) {
        long time = (System.currentTimeMillis() - date) / 1000;
        if (time < 60) {
            return "刚刚";
        } else if (time < 3600) {
            return time / 60 + "分钟前";
        } else if (time < 24 * 3600) {
            return time / 3600 + "小时前";
        } else {
            return time / 24 / 3600 + "天前";
        }
    }

    /**
     * 转为yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatWithYM(long date) {
        if (date == 0) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(new Date(date));

    }

    /**
     * 转为HH:mm
     *
     * @param date
     * @return
     */
    public static String formatWithHm(long date) {
        if (date == 0) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date(date));
    }

    /**
     * 转为dd
     *
     * @param date
     * @return
     */
    public static String formatWithd(long date) {
        if (date == 0) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(new Date(date));
    }

    /**
     * 转为HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatWithHms(long date) {
        if (date == 0) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date(date));
    }

    /**
     * 转为mm:ss
     *
     * @param date
     * @return
     */
    public static String formatWithms(long date) {
        if (date == 0) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        return sdf.format(new Date(date));
    }

    /**
     * 转为 yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String formatWithYMDHm(long date) {
        if (date == 0) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(date));
    }

    /**
     * 转为MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String formatWithMDHm(long date) {
        if (date == 0) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(new Date(date));
    }

    /**
     * 转为 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatWithYMDHms(long date) {
        if (date == 0) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(date));
    }

    /**
     * 由yyyy-MM-dd'T'HH:mm:ssZ -> 时间戳
     *
     * @param datetime
     * @return
     */
    public static long parse(String datetime) {
        try {

            if (StringUtils.isEmpty(datetime)) {
                return System.currentTimeMillis();
            }
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ssZ");
            return sdf.parse(datetime).getTime();
        } catch (ParseException e) {
            AppLog.redLog(TAG, e + "");
        }

        return System.currentTimeMillis();
    }

    /**
     * 由yyyy-MM-dd'T'HH:mm\ -> 时间戳
     *
     * @param datetime
     * @return
     */
    public static long parseToLong(String datetime) {
        try {

            if (StringUtils.isEmpty(datetime)) {
                return System.currentTimeMillis();
            }
            // SimpleDateFormat sdf = new
            // SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.parse(datetime).getTime();
        } catch (ParseException e) {
            AppLog.redLog(TAG, e + "");
        }

        return System.currentTimeMillis();
    }

    public static Date parseToDate(String datetime) {
        try {

            if (StringUtils.isEmpty(datetime)) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(datetime);
        } catch (ParseException e) {
            AppLog.redLog(TAG, e + "");
        }

        return null;
    }

    /**
     * 将日期转化为年月日时分秒
     *
     * @param date
     * @return
     */
    public static String parseDateToString(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 由yyyy-MM-dd -> 时间戳
     *
     * @param datetime
     * @param pattern
     * @return
     */
    public static long parseToYMD(String datetime) {
        try {

            if (StringUtils.isEmpty(datetime)) {
                return System.currentTimeMillis();
            }
            // SimpleDateFormat sdf = new
            // SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(datetime).getTime();
        } catch (ParseException e) {
            AppLog.redLog(TAG, e + "");
        }

        return System.currentTimeMillis();
    }

    /**
     * 时间转换成毫秒
     *
     * @param expireDate
     * @return
     */
    public static long getSecondsFromDate(String expireDate) {
        if (expireDate == null || expireDate.trim().equals(""))
            return 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(expireDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 转换成时间格式(类似音乐、视频计算时间)
     *
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":"
                        + unitFormat(second);
            }
        }
        return timeStr;
    }

    /**
     * 之上的附属方法
     *
     * @param i
     * @return
     */
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 時間差
     *
     * @param nowTime 當前時間
     * @param oldTime 開獎時間
     * @return 返回毫秒
     */
    public static long getTimeDifference(String nowTime, String oldTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date d1 = df.parse(nowTime);
            Date d2 = df.parse(oldTime);

            long diff = d2.getTime() - d1.getTime();

            return diff;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;

    }

    /**
     * 時間差
     *
     * @param nowTime 當前時間
     * @param oldTime 開獎時間
     * @return 返回毫秒
     */
    public static long getTimeDifferenceNots(String nowTime, String oldTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date d1 = df.parse(nowTime);
            Date d2 = df.parse(oldTime);

            long diff = d2.getTime() - d1.getTime();

            return diff;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;

    }

    /**
     * 時間差
     *
     * @param nowTime 當前時間
     * @param oldTime 開獎時間
     * @return 返回毫秒
     */
    public static long getTimeDifference2(String nowTime, String oldTime) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");

        try {
            Date d1 = df.parse(nowTime);
            Date d2 = df.parse(oldTime);

            long diff = d2.getTime() - d1.getTime();

            return diff;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;

    }

    public static String[] getTotalTime(long time) {
        long h = time / 3600000;
        long d = h / 24;
        long hour = h % 24;
        long m = (time - h * 3600000) / 60000;
        long s = (time - h * 3600000 - m * 60000) / 1000;
        String[] times = new String[4];
        times[0] = d > 9 ? (d + "") : ("0" + d);
        times[1] = hour > 9 ? (hour + "") : ("0" + hour);
        times[2] = m > 9 ? (m + "") : ("0" + m);
        times[3] = s > 9 ? (s + "") : ("0" + s);
        return times;
    }
}
