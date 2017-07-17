package com.monkey.framework.utils;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class Usually {

    // 时间选择 不传入时间
    public static void timeCheck(Context context,
                                 final CallBackTime callBackTime) {
        flag = true;
        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker arg0, int hour, int minute) {
                if (flag) {
                    flag = false;
                    callBackTime.send(String.format("%s:%s", timeFormat(hour),
                            timeFormat(minute)));
                }
            }

        }, 00, 00, true).show();
    }

    //日期选择 不传入日期
    public static void dateCheck(Context context,
                                 final CallBackTime callBackTime) {
        flag = true;
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(context, new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                if (flag) {
                    flag = false;
                    callBackTime.send(String.format("%s-%s-%s", timeFormat(year),
                            timeFormat(monthOfYear + 1), timeFormat(dayOfMonth)));
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    // 时间选择
    public static void timeCheck(Context context,
                                 final CallBackTime callBackTime, int h, int m) {
        flag = true;
        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker arg0, int hour, int minute) {
                if (flag) {
                    flag = false;
                    callBackTime.send(String.format("%s:%s", timeFormat(hour),
                            timeFormat(minute)));
                }
            }
        }, h, m, true).show();
    }

    private static boolean flag = true;

    //日期选择
    public static void dateCheck(Context context,
                                 final CallBackTime callBackTime, int y, int m, int d) {
        flag = true;
        new DatePickerDialog(context, new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                if (flag) {
                    flag = false;
                    callBackTime.send(String.format("%s-%s-%s", timeFormat(year),
                            timeFormat(monthOfYear + 1), timeFormat(dayOfMonth)));
                }
            }
        }, y, m, d).show();
    }


    public static String timeFormat(int value) {
        return value < 10 ? "0" + value : "" + value;
    }

    public interface CallBackTime {
        public void send(String time);

        public void sendDate(int year, int monthOfYear, int dayOfMonth);

        public void sendTime(int hour, int minute);
    }

}
