package com.monkey.framework.utils;

import android.app.Activity;
import android.os.Process;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * Description:
 *
 * @author wangxiaojun
 * @version $Revision: 1.2 $
 * @date: 2011-9-26
 * @time: ����03:39:06
 */
public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Activity myContext;

    public UncaughtExceptionHandler(Activity context) {
        myContext = context;
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        Process.killProcess(Process.myPid());
        System.exit(10);
    }
}
