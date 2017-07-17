package com.monkey.framework.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * Description 权限管理
 *
 * @author monkey
 * @Date 2016/8/25
 * @Email j_monkey@sina.cn
 */

public class PermissionManagementUtil {

    /**
     * activity 权限请求
     *
     * @param activity    Activity
     * @param permission  权限
     * @param requestCode 请求的code
     * @return 是否具有权限
     */
    public static boolean ActivityPermission(Activity activity, String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(activity, new String[]{permission},
                        requestCode);
                return false;
            }
        }
        return true;
    }

    /**
     * fragment 权限请求
     *
     * @param fragment    fragment
     * @param permission  权限
     * @param requestCode 请求的code
     * @return 是否具有权限
     */
    public static boolean FragmentPermission(Fragment fragment, String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(fragment.getContext(), permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                fragment.requestPermissions(new String[]{permission},
                        requestCode);
            }
            return false;
        }
        return true;
    }
}
