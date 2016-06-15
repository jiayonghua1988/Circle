package com.yhjia.me.util;

import android.os.Environment;

public class SdCardUtil {

	/**
	 * sd���Ƿ�ɶ�д
	 * @return true �ɶ�д 
	 */
	public static boolean sdCardMounted() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
			return true;
		return false;
	}
	
	/**
	 * ��ȡsd��·��
	 */
	public static String getSdCardPath(){
		return Environment.getExternalStorageDirectory().toString() + "/";
	}
	
}
