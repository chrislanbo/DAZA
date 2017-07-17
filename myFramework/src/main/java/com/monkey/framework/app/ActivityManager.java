package com.monkey.framework.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * Created by pan on 15/5/5.
 * <p/>
 * 活动界面管理器
 */
public class ActivityManager {

    private static List<Activity> activities = new ArrayList<Activity>();

    /**
     * 活动界面创建时，管理器记录启动的Activity
     *
     * @param activity
     */
    public static void onActivityCreate(Activity activity) {
        activities.add(activity);
    }

    /**
     * 活动界面销毁时，管理器移除记录的Activity
     *
     * @param activity
     */
    public static void onActivityDestroy(Activity activity) {
        activities.remove(activity);
    }
    

    public static List<Activity> getActivities() {
		return activities;
	}

	/**
     * 获取Activity
     *
     * @param clz
     * @return
     */
    public static Activity getActivity(Class clz) {
        Activity activity = null;
        for (Activity a : activities) {
            if (a != null && a.getClass() == clz) {
                activity = a;
                break;
            }
        }
        return activity;
    }

    /**
     * 结束指定Class的Activity
     *
     * @param clz
     */
    public static void finish(Class clz) {
        for (int i = 0; i < activities.size(); ) {
            Activity a = activities.get(i);
            if (a != null && a.getClass() == clz) {
                a.finish();
                activities.remove(i);
            }
            i++;
        }
    }

    /**
     * 结束所有活动界面
     */
    public static void finishAll() {
        for (Activity a : activities) {
            if (a != null) {
                a.finish();
            }
        }
        activities.clear();
    }
}
