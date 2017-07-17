package com.monkey.framework.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.TextUtils;

/**
 * 时间工具类
 */
public class TimeUtil {




    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat sdf = new SimpleDateFormat();

    public static String getTime() {
        return getString("HH:mm:ss");
    }

    public static String getString(String pattern) {
        sdf.applyPattern(pattern);
        return sdf.format(new Date());
    }

    /**
     * 将 String 转换成date
     *
     * @param s
     * @return
     * @throws ParseException
     */
    public static Date getTime(String pattern, String time) throws ParseException {
        sdf.applyPattern(pattern);
        return sdf.parse(time);
    }

    /**
     * 将 时间戳 转换成日期形式
     *
     * @param s
     * @return
     * @throws ParseException
     */
    public static String getString(String pattern, Long time)
            throws ParseException {
        sdf.applyPattern(pattern);
        return sdf.format(time);
    }

    /**
     * 根据日期计算年龄
     *
     * @param birthday
     * @return
     */
    public static final String calculateDatePoor(String birthday) {
        try {
            if (TextUtils.isEmpty(birthday))
                return "0";
            sdf.applyPattern("yyyy-MM-dd");
            Date birthdayDate = sdf.parse(birthday);
            String currTimeStr = sdf.format(new Date());
            Date currDate = sdf.parse(currTimeStr);
            if (birthdayDate.getTime() > currDate.getTime()) {
                return "0";
            }
            long age = (currDate.getTime() - birthdayDate.getTime())
                    / (24 * 60 * 60 * 1000) + 1;
            String year = new DecimalFormat("0.00").format(age / 365f);
            if (TextUtils.isEmpty(year))
                return "0";
            return String.valueOf(new Double(year).intValue());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * 根据月日计算星座
     *
     * @param month
     * @param day
     * @return
     */
    public static String getAstro(int month, int day) {
        String[] astro = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座",
                "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};
        int[] arr = new int[]{20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};// 两个星座分割日
        int index = month;
        // 所查询日期在分割日之前，索引-1，否则不变
        if (day < arr[month - 1]) {
            index = index - 1;
        }
        // 返回索引指向的星座string
        return astro[index];
    }

    /**
     * 根据年计算生肖
     *
     * @param month
     * @param day
     * @return
     */
    public static String getZodiac(Integer year) {
        if (year < 1900) {
            return "未知";
        }
        Integer start = 1900;
        String[] years = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊",
                "猴", "鸡", "狗", "猪"};
        return years[(year - start) % years.length];
    }

    /**
     * 返回今天是星期几
     *
     * @return
     */
    public static int getWeek() {
        // 获取今天是星期几
        Calendar c = Calendar.getInstance();
        int week_day = c.get(Calendar.DAY_OF_WEEK);
        if (week_day == 1) {
            week_day = 7;
        } else if (week_day == 0) {
            week_day = 6;
        } else {
            week_day -= 1;
        }
        return week_day;
    }


    public static Long getDayTime() {
        try {
            return getTime("yyyy-MM-dd", getString("yyyy-MM-dd")).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * 格式化时间
     *
     * @param timeStr
     * @param currentTime
     * @return
     */
    public static String getTimeLabel(String timeStr, String currentTime) {
        try {
            Date itemDate = getTime("yyyy-MM-dd HH:mm:ss", timeStr);
            Date date = null;
            if (TextUtils.isEmpty(currentTime)) {
                date = new Date();
            } else {
                date = getTime("yyyy-MM-dd HH:mm:ss", currentTime);
            }
            int timeInterval = (int) ((date.getTime() - itemDate.getTime()) / 1000);
            String result;
            int temp;
            if (timeInterval < 60) {
                result = "刚刚";
            } else if ((temp = timeInterval / 60) < 60) {
                result = String.format("%d分前", temp);
            } else if ((temp = temp / 60) < 24) {
                result = String.format("%d小时前", temp);
            } else if ((temp = temp / 24) < 30) {
                result = String.format("%d天前", temp);
            } else if ((temp = temp / 30) < 12) {
                result = String.format("%d月前", temp);
            } else {
                temp = temp / 12;
                result = String.format("%d年前", temp);
            }
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStr;
    }
}
