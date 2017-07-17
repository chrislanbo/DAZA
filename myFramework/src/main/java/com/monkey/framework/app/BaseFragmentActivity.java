package com.monkey.framework.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.monkey.framework.R;
import com.monkey.framework.utils.UIUtil;
import com.monkey.framework.view.LoadingView;

/**
 * 基本Activity，编译MinSdk<11时继承此Activity，以实现Fragment布局支持
 *
 * @author pan
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    private LoadingView loadingView;

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
        afterInject(savedInstanceState);
        initTile();
//        Thread.setDefaultUncaughtExceptionHandler(new
//                UncaughtExceptionHandler(this));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.onActivityDestroy(this);
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
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

    protected void afterInject(Bundle savedInstanceState) {
    }

    public abstract int getLayoutRes();

    protected boolean back() {
        close();
        return true;
    }

    public void close() {
        ActivityManager.finish(this.getClass());
        UIUtil.hideSoftKeybord(this);
    }
}
