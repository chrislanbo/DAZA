package com.monkey.framework.app;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基本对话框
 * 
 * @author pan
 * 
 */
@SuppressLint("NewApi")
public abstract class BaseDialogFragment extends DialogFragment {

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
		afterInject(savedInstanceState);
		return contentView;
	}

	protected void afterInject(Bundle savedInstanceState) {
	}

	public abstract int getLayoutRes();

}
