package com.monkey.framework.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.monkey.framework.R;
import com.monkey.framework.utils.UIUtil;
import com.monkey.framework.view.LoadingView;

/**
 * 基本Activity
 *
 * @author Appeng
 */
public abstract class BaseActivity extends Activity {
    public static final String DATA = "data";
    protected LoadingView loadingView;

    /**
     * 获取加载中视图，如需自定义可重写此方法
     *
     * @return
     */
    public LoadingView getLoadingView() {
        if (loadingView == null) {
            loadingView = UIUtil.createLoadingView(this);
        }
        return loadingView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.onActivityCreate(this);
        setContentView(getLayoutRes());
//        Thread.setDefaultUncaughtExceptionHandler(new
//                UncaughtExceptionHandler(this));
        afterInject(savedInstanceState);
        initTile();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.onActivityDestroy(this);
    }

    protected void afterInject(Bundle savedInstanceState) {
    }

    private void initTile() {
        View view = findViewById(R.id.img_back);
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    back();
                }
            });
        }
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    protected boolean back() {
        close();
        return true;
    }

    /**
     * 返回布局文件
     */
    public abstract int getLayoutRes();

    public void close() {
        ActivityManager.finish(this.getClass());
        UIUtil.hideSoftKeybord(this);
    }

}
