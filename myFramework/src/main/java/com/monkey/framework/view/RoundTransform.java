package com.monkey.framework.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;

/**
 * Author: Monkey
 * 
 * Date: 2016/1/22.
 * 
 * Email: j_monkey@sina.cn
 * 
 * Description：Picasso圆形图片
 */
public class RoundTransform implements Transformation {

	private final float cornerRadius;
	protected final RectF mRect = new RectF();

	/**
	 * RoundTransform
	 */
	public RoundTransform() {
		this(8);
	}
	
	/**
	 * RoundTransform
	 */
	public RoundTransform(float cornerRadius) {
		super();
		this.cornerRadius = cornerRadius;
	}

	@Override
	public Bitmap transform(Bitmap source) {
		int size = Math.min(source.getWidth(), source.getHeight());
		int x = (source.getWidth() - size) / 2;
		int y = (source.getHeight() - size) / 2;

		Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
		if (squaredBitmap != source) {
			source.recycle();
		}
		Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		BitmapShader shader = new BitmapShader(squaredBitmap,
				BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
		paint.setShader(shader);
		paint.setAntiAlias(true);
		mRect.set(0, 0, size, size);
		canvas.drawRoundRect(mRect, cornerRadius, cornerRadius, paint);
		squaredBitmap.recycle();
		return bitmap;
	}

	@Override
	public String key() {
		return "circle";
	}
}
