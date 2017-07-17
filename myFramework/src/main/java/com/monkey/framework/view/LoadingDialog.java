package com.monkey.framework.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import com.monkey.framework.R;


/**
 * 加载中Dialog
 *
 * @author pan
 */
public class LoadingDialog extends Dialog implements LoadingView {

    private View root;
    private int loadingCount;
    private Handler mHandler;

    @SuppressLint("InflateParams")
    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog);
        root = LayoutInflater.from(context).inflate(R.layout.my_loading_dialog,
                null);
        setContentView(root);
        setCancelable(false);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    showLoadingDialog();
                } else {
                    dismissLoadingDialog();
                }
            }
        };
    }

    /**
     * 设置背景透明
     */
    public void setBackgroundTransparent() {
        root.setBackgroundColor(getContext().getResources().getColor(
                R.color.transparent));
    }

    @Override
    public void showLoadingView() {
        loadingCount++;
        if (!isShowing()) {
            mHandler.obtainMessage(1).sendToTarget();
        }
    }

    @Override
    public boolean isShow() {
        return isShowing();
    }

    @Override
    public void hideLoadingView() {
        loadingCount--;
        if (loadingCount <= 1) {
            mHandler.obtainMessage(0).sendToTarget();
        }
    }

    /**
     * 显示
     */
    public void showLoadingDialog() {
        super.show();
    }


    /**
     * 隐藏
     */
    public void dismissLoadingDialog() {
        super.dismiss();
        loadingCount = 0;
    }
}
