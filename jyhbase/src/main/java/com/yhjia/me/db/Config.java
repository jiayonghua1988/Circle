package com.yhjia.me.db;

import android.os.Environment;

public class Config extends FConfig {
	public static final String CACHE_IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.jiahao.qy.goodtoday/cache/";
	public static final String CACHE_VOICE_PATH = Environment.getExternalStorageDirectory().getPath()+"/Android/data/com.jiahao.qy.goodtoday/Voice/";
	public static final String DB_NAME = "jiahao_v3.db";


	
	public static final int RESULT_FINISH = 9999;
	
}