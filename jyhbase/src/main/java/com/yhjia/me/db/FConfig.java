package com.yhjia.me.db;

import android.content.Context;

public class FConfig {
	// 语音缓存目录
	private static String cacheVoicePath;
	// 图片缓存目录
	private static String cacheImagePath;
	
	public static int IMAGE_ROUND = 5;

	/**
	 * 初始化地址路径
	 */
	public static void initPath(Context context) {
	}

	/**
	 * 获取缓存语音的路径
	 * 
	 * @return 优先返回SD的路径， 如果没有就返回应用缓存路径
	 */
	public static String getCacheVoicePath() {
		return cacheVoicePath;
	}

	/**
	 * 获取缓存图片的路径
	 * 
	 * @return 优先返回SD的路径， 如果没有就返回应用缓存路径
	 */
	public static String getCacheImagePath() {
		return cacheImagePath;
	}
}