package com.yhjia.me.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * 代码中的单位转换 px dip sp
 * 
 * @author perry
 * 
 */
public class DimensionPixelUtil {
	public final static int PX = TypedValue.COMPLEX_UNIT_PX;
	public final static int DIP = TypedValue.COMPLEX_UNIT_DIP;
	public final static int SP = TypedValue.COMPLEX_UNIT_SP;

	/**
	 * 
	 * @param unit
	 *            单位 </br>0 px</br>1 dip</br>2 sp
	 * @param value
	 *            size 大小
	 * @param context
	 * @return
	 */
	public static float getDimensionPixelSize(int unit, float value, Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metrics);
		switch (unit) {
		case PX:
			return value;
		case DIP:
		case SP:
			return TypedValue.applyDimension(unit, value, metrics);
		default:
			throw new IllegalArgumentException("unknow unix");
		}
	}

	/**
	 * 根据手机的屏幕属性从 dip转化成px
	 * 
	 * @param context
	 * @param value
	 * @return
	 */
	public static int dip2px(Context context, float value) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return (int)(value * metrics.density);
	}

	/**
	 * 根据手机的屏幕属性从 px转化成dip
	 * 
	 * @param context
	 * @param value
	 * @return
	 */
	public static float px2dip(Context context, float value) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return value / metrics.density;
	}

	/**
	 * 根据手机的屏幕属性从 sp 转化成px
	 * 
	 * @param context
	 * @param value
	 * @return
	 */
	public static float sp2px(Context context, float value) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return value * metrics.scaledDensity;
	}

	/**
	 * 根据手机的屏幕属性从 px 转化成sp
	 * 
	 * @param context
	 * @param value
	 * @return
	 */
	public static float px2sp(Context context, float value) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return value / metrics.scaledDensity;
	}

}
