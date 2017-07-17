package com.monkey.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.monkey.framework.MyFramwork;
import com.monkey.framework.view.LoadingDialog;
import com.monkey.framework.view.LoadingView;

/**
 * View 工具类
 *
 * @author pan
 */
public class UIUtil {

    private static Handler handler;

    private UIUtil() {
    }

    /**
     * 从资源中获取一个Drawable对象
     *
     * @param resId 资源ID
     * @return Drawable对象
     */
    public static Drawable getDrawable(int resId) {
        return MyFramwork.getAppContext().getResources().getDrawable(resId);
    }

    /**
     * 从资源中获取一个String对象
     *
     * @param resId 资源ID
     * @return String对象
     */
    public static String getString(int resId) {
        return MyFramwork.getAppContext().getResources().getString(resId);
    }

    /**
     * 从资源中获取一个Color
     *
     * @param resId 资源ID
     * @return
     */
    public static int getColor(int resId) {
        return MyFramwork.getAppContext().getResources().getColor(resId);
    }


    /**
     * 显示软键盘
     *
     * @param activity
     */
    public static void showSoftKeybord(Activity activity) {
        View v = activity.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!imm.isActive()) {
                imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
            }
        }
    }

  /**
     * 显示软键盘
     *
     * @param activity
     */
    public static void showSoftKeybord(Activity activity,View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!imm.isActive()) {
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }
    }

    /**
     * 显示软键盘
     *
     * @param context
     */
    public static void showSoftKeybord(Context context,View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!imm.isActive()) {
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }
    }

    /**
     * 从dimes 获取一个资源
     *
     * @param resId 资源ID
     * @return
     */
    public static int getDimensionPixelSize(int resId) {
        return MyFramwork.getAppContext().getResources().getDimensionPixelSize(resId);
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftKeybord(Activity activity) {
        View v = activity.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }

    /**
     * 获取屏幕尺寸
     *
     * @return
     */
    public static DisplayMetrics getDisplaySize() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) MyFramwork.getAppContext()
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * 获取MotionEvent的Action名称
     *
     * @param action
     * @return
     */
    public static String getAction(int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
            case MotionEvent.ACTION_HOVER_ENTER:
                return "ACTION_HOVER_ENTER";
            case MotionEvent.ACTION_HOVER_EXIT:
                return "ACTION_HOVER_EXIT";
            case MotionEvent.ACTION_HOVER_MOVE:
                return "ACTION_HOVER_MOVE";
            case MotionEvent.ACTION_MASK:
                return "ACTION_MASK";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_OUTSIDE:
                return "ACTION_OUTSIDE";
            case MotionEvent.ACTION_POINTER_DOWN:
                return "ACTION_POINTER_DOWN";
            case MotionEvent.ACTION_POINTER_INDEX_MASK:
                return "ACTION_POINTER_INDEX_MASK";
            case MotionEvent.ACTION_POINTER_UP:
                return "ACTION_POINTER_UP";
            case MotionEvent.ACTION_SCROLL:
                return "ACTION_SCROLL";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            default:
                return "UNKNOWN_ACTION";
        }
    }

    /**
     * 创建一个“加载中”视图对象
     *
     * @param context
     * @return
     */
    public static LoadingView createLoadingView(Context context) {
        return new LoadingDialog(context);
    }

    /**
     * 发起一个在UI线程执行的任务
     *
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.post(runnable);
    }

    /**
     * 发起一个在UI线程执行的任务
     *
     * @param runnable
     * @param delay
     */
    public static void runOnUIThread(Runnable runnable, int delay) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.postDelayed(runnable, delay);
    }

    /**
     * 获取横屏状态
     *
     * @return true 横屏 false 竖屏
     */
    public static boolean isScreenChange() {
        Configuration mConfiguration = MyFramwork.getAppContext().getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            //横屏
            return true;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            //竖屏
            return false;
        }
        return false;
    }
}
