package com.monkey.framework.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.webkit.URLUtil;

import com.monkey.framework.MyFramwork;
import com.monkey.framework.view.MyToast;

/**
 * 系统工具类，封装了调用系统接口的方法
 */
public class SystemUtil {

    private static int memorySize;
    private static String imei;
    private static String phoneNumber;
    private static String version;
    private static int versionCode;

    private static String macId;
    private static String ipAddress;

    private SystemUtil() {
    }

    /**
     * 获取系统内存大小
     *
     * @return
     */
    public static int getMemorySize(Context appContext) {
        if (memorySize <= 0) {
            memorySize = 1024 * 1024 * ((ActivityManager) appContext
                    .getSystemService(Context.ACTIVITY_SERVICE))
                    .getMemoryClass();
        }
        return memorySize;
    }

    /**
     * 获取设备IMEI号
     *
     * @return
     */
    public static String getIMEI() {
        if (imei == null) {
            getSystemInfo();
        }
        return imei;
    }

    /**
     * 获取用户手机号码
     *
     * @return
     */
    public static String getPhoneNumber() {
        if (phoneNumber == null) {
            getSystemInfo();
        }
        return phoneNumber;
    }

    /**
     * 获取Android设备ID
     *
     * @return
     */
    public static String getAndroidId() {
        return Secure.getString(MyFramwork.getAppContext()
                .getContentResolver(), Secure.ANDROID_ID);
    }

    public static String getUUID(Context mContext) {
        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + Secure.getString(mContext.getContentResolver(), Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();

        return deviceId;
    }

    /**
     * 获取当前版本名称
     *
     * @return
     */
    public static String getVersion() {
        if (version == null) {
            getSystemInfo();
        }
        return version;
    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    public static int getVersionCode() {
        if (versionCode == 0) {
            getSystemInfo();
        }
        return versionCode;
    }

    /**
     * 获取Mac地址
     *
     * @return
     */
    public static String getMacId() {
        if (macId == null)
            macId = ((WifiManager) MyFramwork.getAppContext().getSystemService(
                    Context.WIFI_SERVICE)).getConnectionInfo().getMacAddress();
        return macId;
    }

    /**
     * 获取IP地址
     *
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String getIpAddress() {
        WifiManager wifiManager = (WifiManager) MyFramwork.getAppContext()
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipCode = wifiInfo.getIpAddress();
        try {
            ipAddress = InetAddress.getByName(
                    String.format("%d.%d.%d.%d", (ipCode & 0xff),
                            (ipCode >> 8 & 0xff), (ipCode >> 16 & 0xff),
                            (ipCode >> 24 & 0xff))).getHostAddress();
        } catch (UnknownHostException e) {
            ipAddress = "unknown host";
        }
        return ipAddress;
    }

    /**
     * 网络是否可用
     *
     * @return
     */
    public static boolean isNetActive() {
        ConnectivityManager connMgr = (ConnectivityManager) MyFramwork
                .getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        return false;
    }

    private static void getSystemInfo() {
        TelephonyManager phoneMgr = (TelephonyManager) MyFramwork
                .getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        PackageManager manager;
        PackageInfo info = null;
        manager = MyFramwork.getAppContext().getPackageManager();
        try {
            info = manager.getPackageInfo(MyFramwork.getAppContext()
                    .getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        imei = phoneMgr.getDeviceId();
        phoneNumber = phoneMgr.getLine1Number();
        version = info != null ? info.versionName : "unkown";
        versionCode = info != null ? info.versionCode : -1;
    }

    /**
     * 调用系统浏览器打开URL
     *
     * @param context
     * @param url
     */
    public static void openUrl(Context context, String url) {
        if (URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url)) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri
                    .parse(url)));
        } else
            MyToast.showBottom("URL地址无效");
    }

    /**
     * SD是否装载
     *
     * @return
     */
    public static boolean isSdcardExisting() {
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isApkstalled(Context context, String packagename) {
        if (TextUtils.isEmpty(packagename)) {
            return false;
        }
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packagename,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @param cxt 上下文
     * @param pid pid
     * @return
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * @param context 上下文
     * @param intent  intent携带activity
     * @return boolean true为在最顶层，false为否
     * @Description: 判断activity是否在应用的最顶层
     * @author Sunday
     * @date 2016年3月15日
     */
    public static boolean isTop(Context context, Intent intent) {
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> appTask = am.getRunningTasks(1);
        if (appTask.size() > 0 && appTask.get(0).topActivity.equals(intent.getComponent())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param context 上下文
     * @param intent  intent携带activity
     * @return boolean true为在运行，false为已结束
     * @Description: 判断应用是否在运行
     * @author Sunday
     * @date 2016年3月15日
     */
    public static boolean isRuning(Context context, Intent intent) {
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (tasks.size() > 0 && tasks.get(0).baseActivity.equals(intent.getComponent())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断包名对应的程序是否运行
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 返回
     */
    public static boolean checkBrowser(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(
                    packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    //判断程序处于前台还是后台
    public static boolean isInBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                MyLog.e("此appimportace =" + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    MyLog.e("处于后台" + appProcess.processName);
                    return true;
                } else {
                    MyLog.e("处于前台" + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 启动activity,如果该activity已经启动,则直接打开;如果该activity没有启动,则从新启动.
     * 一般用于状态栏点击进入程序时使用.
     *
     * @param context
     * @param cls     被启动的activity
     */
    public static Intent startTaskIntent(Context context, Class<?> cls) {
        if (Build.VERSION.SDK_INT >= 21) {
            return startNewApiTaskIntent(context, cls);
        } else {
            return startOldApiTaskIntent(context, cls);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Intent startNewApiTaskIntent(Context context, Class<?> cls) {
        final PackageManager pm = context.getPackageManager();
        final ActivityManager am = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> appTasks = am.getAppTasks();
        for (ActivityManager.AppTask task : appTasks) {
            final ActivityManager.RecentTaskInfo info = task.getTaskInfo();
            Intent intent = new Intent(info.baseIntent);
            if (info.origActivity != null) {
                intent.setComponent(info.origActivity);
            }
            final ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
            if (resolveInfo != null) {
                final ActivityInfo activityInfo = resolveInfo.activityInfo;
                if (TextUtils.equals(activityInfo.packageName, context.getPackageName())) {
                    return intent;
                }
            }
        }

        Intent intent = new Intent(context, cls);
        intent.setFlags((intent.getFlags() & ~Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED));

        return intent;
    }

    private static Intent startOldApiTaskIntent(Context context, Class<?> cls) {
        final PackageManager pm = context.getPackageManager();
        final ActivityManager am = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);

        final List<ActivityManager.RecentTaskInfo> recentTasks = am.getRecentTasks(20, 0x0002);
        for (ActivityManager.RecentTaskInfo info : recentTasks) {
            Intent intent = new Intent(info.baseIntent);
            if (info.origActivity != null) {
                intent.setComponent(info.origActivity);
            }
            final ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
            if (resolveInfo != null) {
                final ActivityInfo activityInfo = resolveInfo.activityInfo;
                if (TextUtils.equals(activityInfo.packageName, context.getPackageName())) {
                    context.startActivity(intent);
                    return intent;
                }
            }
        }

        Intent intent = new Intent(context, cls);
        intent.setFlags((intent.getFlags() & ~Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED));
        return intent;
    }

}