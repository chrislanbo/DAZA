package com.monkey.framework.utils;

import android.content.Context;

/**
 * 判断是否为魅族手机
 * 
 * @author Administrator
 * 
 */
public class ScreenAapiveUtils {
	/**
	 * 是否为魅族手机
	 * 
	 * @return
	 */
	public static boolean isMeiZu() {
		String brand = android.os.Build.BRAND;
		if (!StringUtil.isNull(brand)) {
			if ("Meizu".equals(brand)) {
				return true;
			}
		}
		return false;
	}

	public static int getSmartBarHeight(Context context) {
		int pxHeight = (int) (UIUtil.getDisplaySize().density * 48 + 0.5f);
		return pxHeight;
	}

}
