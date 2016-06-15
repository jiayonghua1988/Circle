package com.yhjia.me.util;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData {
	public static String cookie;
	public static void saveSeesion(Context context,String session) {
		SharedPreferences.Editor editor = context.getSharedPreferences("Session_key", 0).edit();
		editor.putString("session", session);
		editor.commit();
	} 
	public static String  getSeesion(Context context) {
		SharedPreferences  editor = context.getSharedPreferences("Session_key", 0);
		return editor.getString("session", null); 
	} 
	
	public static void saveUserId(Context context,String userId) {
		SharedPreferences.Editor editor = context.getSharedPreferences("userId_key", 0).edit();
		editor.putString("userId", userId);
		editor.commit();
	} 
	public static String  getUserId(Context context) {
		SharedPreferences  editor = context.getSharedPreferences("userId_key", 0);
		return editor.getString("userId", null); 
	} 
	
	
	public static void saveCookie(Context context,String cookie) {
		SharedPreferences.Editor editor = context.getSharedPreferences("cookie_key", 0).edit();
		editor.putString("cookie", cookie);
		editor.commit();
	} 
	public static String  getCookie(Context context) {
		SharedPreferences  editor = context.getSharedPreferences("cookie_key", 0);
		return editor.getString("cookie", null); 
	} 
	
	
	public static void saveUserName(Context context,String userName) {
		SharedPreferences.Editor editor = context.getSharedPreferences("userName_key", 0).edit();
		editor.putString("userName", userName);
		editor.commit();
	} 
	public static String  getUserName(Context context) {
		SharedPreferences  editor = context.getSharedPreferences("userName_key", 0);
		return editor.getString("userName", null); 
	} 
	
	public static void savePassword(Context context,String password) {
		SharedPreferences.Editor editor = context.getSharedPreferences("password_key", 0).edit();
		editor.putString("password", password);
		editor.commit();
	} 
	public static String  getPassword(Context context) {
		SharedPreferences  editor = context.getSharedPreferences("password_key", 0);
		return editor.getString("password", null); 
	} 
	
	 
}
