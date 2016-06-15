package com.yhjia.me.common;

import android.app.Activity;

import com.yhjia.me.R;
import com.yhjia.me.httpclient.async.LSHttpClient;
import com.yhjia.me.httpclient.async.ResponseHandler;
import com.yhjia.me.httpclient.core.ParameterList;
import com.yhjia.me.util.Prompt;

/**
 * 网络请求帮助类
 * @author yhjia
 *
 */
public class CommonNet {
	public static void getExcute(Activity context,ParameterList params,String url,ResponseHandler responseHandler) {
		LSHttpClient client = new LSHttpClient();

		client.get(context,Constant.HTTP_PREFIX + url,params, responseHandler);
	}
	
	public static void postExcute(Activity context,ParameterList params,String url,ResponseHandler responseHandler) {
		Prompt.showLoadingDialog(context.getString(R.string.loading), context);
		LSHttpClient client = new LSHttpClient();
		client.post(context,Constant.HTTP_PREFIX + url,params, responseHandler);
	}
	
	public static void postExcute(Activity context,ParameterList params,String allUrl,ResponseHandler responseHandler,boolean isAll) {
		LSHttpClient client = new LSHttpClient();
		client.post(context,allUrl,params, responseHandler);
	}
}