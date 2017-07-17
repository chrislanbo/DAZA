package com.monkey.framework.app;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monkey.framework.view.LoadingView;

/**
 * 基本Fragment（minSDK>11时使用此Fragment）
 *
 * @author pan
 */
@SuppressLint("NewApi")
public abstract class BaseFragment extends Fragment {

    protected LoadingView loadingView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container,
                savedInstanceState);
        if (contentView == null) {
            int layoutResource = getLayoutRes();
            if (layoutResource == 0) {
                return null;
            }
            contentView = inflater.inflate(layoutResource, container, false);
        }
        afterInject(savedInstanceState, contentView);
        return contentView;
    }

    protected void afterInject(Bundle savedInstanceState, View contentView) {
    }

    public abstract int getLayoutRes();

    /**
     * 获取加载中视图，如需自定义可重写此方法
     *
     * @return
     */
    public LoadingView getLoadingView() {
        return ((BaseActivity) getActivity()).getLoadingView();
    }

}
