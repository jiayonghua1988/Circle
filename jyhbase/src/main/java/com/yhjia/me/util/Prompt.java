package com.yhjia.me.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;

import com.yhjia.me.view.LoadingDialog;

/**
 * 提示帮助、信息打印类
 * @author hao.jiang
 *
 */
public class Prompt {
	private static LoadingDialog mLoadingDialog;

	private static final String DEBUG_TAG = "Chiang";
	/** 是否为开发模�?,�?发模式将打印log消息 */
	public static boolean debug = false;
	
	/**
	 * 显示加载提示语句,传入null是默认提�?
	 */
	public static void showLoadingDialog(String msg,Activity act) {
		try{
			if(mLoadingDialog != null){
				dismissLoadingDialog();
			}
			
			if(TextUtils.isEmpty(msg)){
				mLoadingDialog = new LoadingDialog(act);
			}else{
				mLoadingDialog = new LoadingDialog(act,msg);
			}
			mLoadingDialog.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 是否可以手动的取消加载圈
	 */
	public static void setCancelable(boolean b){
		if(mLoadingDialog != null){
			mLoadingDialog.setCancelable(b);
		}
	}
	
	/**
	 * 修改Dialog的提示信�?
	 */
	public static void updateMsg(String msg){
		if(mLoadingDialog!=null){
			mLoadingDialog.setText(msg);
		}
	}
	
	/**
	 * �?单的dialog提示
	 */
	public static void showSimpleDialog(String msg,String btnText,Context context){
		showSimpleDialog(msg, btnText, context, null);
	}
	
	/**
	 * �?单的dialog提示
	 */
	public static void showSimpleDialog(String msg,String btnText,Context context,DialogInterface.OnClickListener listener){
		try{
			AlertDialog.Builder builder=new AlertDialog.Builder(context);
			builder.setMessage(msg);
			builder.setPositiveButton(btnText, listener);
			builder.show();
			//防止离开页面的时候弹框崩�?
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * �?单的dialog提示
	 */
	public static void showSimpleDialog(String msg,String btnPositionLabel,String btnNegativeLabel,Context context,DialogInterface.OnClickListener listener){
		try{
			AlertDialog.Builder builder=new AlertDialog.Builder(context);
			builder.setMessage(msg);
			builder.setPositiveButton(btnPositionLabel, listener);
			builder.setNegativeButton(btnNegativeLabel, null);
			builder.show();
			//防止离开页面的时候弹框崩�?
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭showLoadingDialog�?启的数据加载提示
	 */
	public static void dismissLoadingDialog(){
		if(mLoadingDialog!=null && mLoadingDialog.isShowing()){
			try{
				mLoadingDialog.dismiss();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *  @param object 传入你想要打印的信息，数字int类型也可�?
	 */
	public static void printLog(Object object){
		if(debug){
			Log.d(DEBUG_TAG, object.toString());
		}
	}
}
