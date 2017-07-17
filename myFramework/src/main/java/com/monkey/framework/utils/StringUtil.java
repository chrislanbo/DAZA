package com.monkey.framework.utils;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.EditText;

public class StringUtil {

    private StringUtil() {
    }

    /**
     * 获取输入框的内容
     *
     * @param editText
     * @return
     */
    public static String getInput(EditText editText) {
        String s = editText.getText().toString();
        if (!s.equals(s.trim())) {
            editText.setText(s.trim());
        }
        return s.trim();
    }

    /**
     * 检查输入框内容是否为空
     *
     * @param editText
     * @return
     */
    public static boolean isNull(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    /**
     * 检查字符是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        if (str == null)
            return true;
        return str.trim().length() == 0;
    }

    /**
     * 如果小于10 返回 0num
     *
     * @param str
     * @return
     */
    public static String getNumString(int num) {
        if (num < 10) {
            return "0" + num;
        }
        return num + "";
    }

    public static String formatt(double value) {
        return formatt(value, "#0.00");
    }

    public static String formatt(double value, String pattern) {
        return new DecimalFormat(pattern).format(value);
    }

}
