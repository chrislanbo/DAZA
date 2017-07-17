package com.monkey.framework.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.monkey.framework.R;


/**
 * 自定义Toast
 */
public class MyToast {

	private static Context context;
	private static Toast toast;
	private static Handler mHandler;

	public static void hide() {
		if (toast != null)
			toast.cancel();
	}

	public static void init(Context context) {
		MyToast.context = context;
		MyToast.mHandler = new Handler(context.getMainLooper()) {
			@Override
			public void handleMessage(Message msg) {
				showToast((String) msg.obj, msg.what);
			}
		};
	}

	/**
	 * 在底部显示一个短（时间）Toast
	 * 
	 * @param msg
	 */
	public static void showBottom(String msg) {
		mHandler.obtainMessage(0, msg).sendToTarget();
	}

	/**
	 * 在底部显示一个长（时间）Toast
	 * 
	 * @param msg
	 */
	public static void showBottomL(String msg) {
		mHandler.obtainMessage(1, msg).sendToTarget();
	}

	/**
	 * 在中部显示一个短（时间）Toast
	 * 
	 * @param msg
	 */
	public static void showMiddle(String msg) {
		mHandler.obtainMessage(2, msg).sendToTarget();
	}

	/**
	 * 在中部显示一个长（时间）Toast
	 * 
	 * @param msg
	 */
	public static void showMiddleL(String msg) {
		mHandler.obtainMessage(3, msg).sendToTarget();
	}

	@SuppressLint("InflateParams")
	private static void showToast(final String msg, final int what) {
		if (toast != null) {
			hide();
		}
		toast = new Toast(context);
		View root = LayoutInflater.from(context).inflate(R.layout.my_toast,
				null);
		toast.setView(root);
		TextView text = (TextView) root.findViewById(R.id.my_toast_text);
		text.setText(msg);
		if (what >= 2) {
			toast.setGravity(Gravity.CENTER, 0, 0);
		}
		toast.setDuration(what % 2);
		toast.show();
	}
}
