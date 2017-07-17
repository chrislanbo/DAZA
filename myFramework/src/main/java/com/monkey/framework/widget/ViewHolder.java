package com.monkey.framework.widget;

import android.view.View;

/**
 * 适配器视图保持器
 * 
 * @author pan
 * 
 */
public abstract class ViewHolder<T> {

	protected int position;

	/**
	 * 保持器的位置发生变化时，使用此Holder对象的Adapter将自动调用此方法
	 * 
	 * @param position
	 */
	public void onPositionChange(int position) {
		this.position = position;
	}

	/**
	 * 需要加载子控件时，使用此Holder对象的Adapter将自动调用此方法
	 * 
	 * @param root
	 *            根布局
	 */
	public abstract void onFindView(View root);

	/**
	 * 加载布局中控件的数据时，使用此Holder对象的Adapter将自动调用此方法绑定控件数据
	 * 
	 * @param data
	 */
	public abstract void onBindData(T data);
}
