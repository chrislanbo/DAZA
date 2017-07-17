package com.monkey.framework.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Description 倒计时
 *
 * @author monkey
 * @email j_monkey@sina.cn
 * @date 2016/7/18
 */
public class TimeCount extends CountDownTimer {

    private TextView textView;
    private long countDownInterval;
    private String unit;
    private String label;

    /**
     * @param millisInFuture    时长
     * @param countDownInterval 间隔
     * @param textView
     */
    public TimeCount(long millisInFuture, long countDownInterval,
                     TextView textView, String unit, String label) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.countDownInterval = countDownInterval;
        this.unit = unit;
        this.label = label;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setEnabled(false);
        textView.setText(String.format("%d%s", millisUntilFinished
                / countDownInterval, unit));
    }

    @Override
    public void onFinish() {
        textView.setEnabled(true);
        textView.setText(label);
    }


}
