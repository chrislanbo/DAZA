package com.lanbo.daza.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.List;

/**
 * header抽象类
 * @param <T>
 */
public abstract class AbsHeaderView<T> {

    protected Activity mActivity;
    protected LayoutInflater mInflate;
    protected T mEntity;

    public AbsHeaderView(Activity activity) {
        this.mActivity = activity;
        mInflate = LayoutInflater.from(activity);
    }

    /**
     * 填充view
     * @param t 数据
     * @param listView listView
     * @return 数据是否正确
     */
    public boolean fillView(T t, ListView listView) {
        if (t == null) {
            return false;
        }
        if ((t instanceof List) && ((List) t).size() == 0) {
            return false;
        }
        this.mEntity = t;
        getView(t, listView);
        return true;
    }

    /**
     * 加载布局
     * @param t 数据
     * @param listView s
     */
    protected abstract void getView(T t, ListView listView);
}
