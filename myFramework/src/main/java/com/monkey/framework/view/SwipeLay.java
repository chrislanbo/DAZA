/*
 *SwipeLay.java
 *Classes：com.example.testswipeview.SwipeLay
 *wangxiaojun Create at 2013-12-23 上午10:32:30	
 */
package com.monkey.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.monkey.framework.R;


/**
 * Description: com.example.testswipeview.SwipeLay
 * 
 * @version $Revision: 1.0 $
 * @author wangxiaojun
 * @email draculawang@hotmail.com
 * @date: 2013-12-23
 * @time: 上午10:32:30
 */
public class SwipeLay extends HorizontalScrollView {
	private SwipListener listener;
	private View leftView;
	private View rightView;
	private boolean isOpen = false;
	private boolean isScroll = true;

	/**
	 * SwipeLay
	 */
	public SwipeLay(Context context) {
		super(context);
	}

	/**
	 * SwipeLay
	 */
	public SwipeLay(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		leftView = findViewById(R.id.item_left_lay);
		leftView.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
		int lWidth = leftView.getMeasuredWidth();
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) leftView
				.getLayoutParams();
		lp.width = lWidth;
		leftView.setLayoutParams(lp);
		rightView = findViewById(R.id.item_right_lay);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	public void openActionView() {
		smoothScrollTo(rightView.getWidth(), 0);
		if (isOpen) {
			return;
		}
		isOpen = true;
		if (this.listener != null) {
			this.listener.opened(this);
		}
	}

	public void closeActionView() {
		smoothScrollTo(0, 0);
		if (!isOpen) {
			return;
		}
		isOpen = false;
		if (this.listener != null) {
			this.listener.closed(this);
		}
	}

	public boolean isOpenActivon() {
		return isOpen;
	}

	public void setListener(SwipListener lis) {
		this.listener = lis;
	}

	/**
	 * @see HorizontalScrollView#onInterceptTouchEvent(MotionEvent)
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		boolean result = super.onInterceptTouchEvent(ev);
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			if (!isOpen && this.listener != null) {
				this.listener.start(this);
			}
		}
		return result;
	}

	/**
	 * @see View#onTouchEvent(MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (!isScroll) {
			return true;
		}
		boolean result = super.onTouchEvent(ev);
		if (ev.getAction() == MotionEvent.ACTION_UP
				|| ev.getAction() == MotionEvent.ACTION_CANCEL) {
			int xWidth = rightView.getWidth();
			if (isOpen) {
				if (getScrollX() > xWidth / 3 * 2) {
					openActionView();
				} else {
					closeActionView();
				}
			} else {
				if (getScrollX() > xWidth / 3) {
					openActionView();
				} else {
					closeActionView();
				}
			}
		}
		return result;
	}
	
	

	public boolean isScroll() {
		return isScroll;
	}

	public void setScroll(boolean isScroll) {
		this.isScroll = isScroll;
	}

	public interface SwipListener {
		public void opened(SwipeLay view);

		public void closed(SwipeLay view);

		public void start(SwipeLay view);
	}
}
