package com.yhjia.me.db;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 小数据缓存
 * @author Aaren
 *
 */
public class SharePre {
	public static final String USER_DATA = "userData";
	
	public static final String AVATAR_URL ="avatar_url";//头像
	public static final String ACCOUNT = "account";
	public static final String PASSWORD = "password";
	public static final String VERIFIED =  "verified";
	public static final String VERIFIEDCOMMENT = "verifiedcomment";

	public static final String ACCID = "accid";//云信account
	public static final String YUNXIN_TOKEN = "yunxin_token";//云信token

	public static final String USER_ID = "id";// 用户ID
	public static final String HOST = "host";// IP
	public static final String PORT = "port";// 端口号

	private SharedPreferences preferences;
	
	public SharePre(Context context) {
		preferences = context.getSharedPreferences("Conf", Context.MODE_PRIVATE);
	}

	public void set(String key, String value) {
		preferences.edit().putString(key, value).commit();
	}

	public void set(String key, int value) {
		preferences.edit().putInt(key, value).commit();
	}

	public void setWithUser(String key, int value) {
		String keyWithUser = key + preferences.getString(USER_ID, "");
		preferences.edit().putInt(keyWithUser, value).commit();
	}

	public int getIntWithUser(String key) {
		String keyWithUser = key + preferences.getString(USER_ID, "");
		return preferences.getInt(keyWithUser, 0);
	}
	
	public void setWithUser(String key,String value) {
		String keyWithUser = key + preferences.getString(USER_ID, "");
		preferences.edit().putString(keyWithUser, value).commit();
	}
	
	public String getWithUser(String key,String value) {
		String keyWithUser = key + preferences.getString(USER_ID, "");
		return preferences.getString(keyWithUser, "");
	}

	public String getValue(String key) {
		return preferences.getString(key, null);
	}
	
	/**
	 * @param def 如果没有获取到参数，默认返回def
	 */
	public String getValue(String key,String def) {
		return preferences.getString(key, def);
	}

	public int getIntVal(String key) {
		return preferences.getInt(key, 0);
	}
	
	/**
	 * @param def 如果没有获取到参数，默认返回def
	 */
	public int getIntVal(String key,int def) {
		return preferences.getInt(key, def);
	}
	
	public void clearData() {
		preferences.edit().clear().commit();
	}
}
