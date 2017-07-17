package com.monkey.framework.view;

/**
 * 加载中View接口
 */
public interface LoadingView {

    /**
     * 显示加载中View
     */
    void showLoadingView();

    //是否显示
    boolean isShow();

    /**
     * 隐藏加载中View
     */
    void hideLoadingView();
}
