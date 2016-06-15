package com.yhjia.me.httpclient.async;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.yhjia.me.app.IApplication;
import com.yhjia.me.bean.ResultInfo;
import com.yhjia.me.util.DebugUtil;
import com.yhjia.me.util.Logger;

public abstract class LSResponseHandler<JSON_TYPE> extends ResponseHandler {
	/*
	 * 服务器返回的json字符串转换的JavaBean类型
	 */
	private Class<JSON_TYPE> type;
	private String debugPath;
	
	public LSResponseHandler(Class<JSON_TYPE> type) {
		this.type = type;
	}
	
	public LSResponseHandler(Class<JSON_TYPE> type,String debugPath) {
		this.type = type;
		this.debugPath = debugPath;
	}
	
	public LSResponseHandler() {
		
	}
	
	public abstract void onSuccess(int statusCode, Map<String, List<String>> headers,JSON_TYPE data,ResultInfo result);
	
	public  void onError(int statusCode, Map<String, List<String>> headers,ResultInfo error) {
		try {
//			UI.showLongToast(mContext, error.getMessage());
		} catch (Exception e) {
			
		}
	}

	@Override
	public void onFailure(int statusCode, Throwable error) {
		try {
//			UI.showLongToast(mContext, R.string.exception_default);
		} catch (Exception e) {
			
		}
	}
	@Override
	public void onSuccess(int statusCode, Map<String, List<String>> headers,String response) {
		if (debugPath != null && IApplication.isTestData) {
			String res = DebugUtil.getResponse(debugPath);
			if (res != null) {
				response = res;
			}
		}

		try {
			JSONObject jo = new JSONObject(response);
			Log.e("Test", "response=" + response);
			Gson g = new Gson();
			String resultStr = jo.getString("success");
			ResultInfo resultInfo = new ResultInfo();
			resultInfo.setSuccess(resultStr);
			if (jo.has("errorMsg")) {
				String errorMsg = jo.getString("errorMsg");
				resultInfo.setErrorMsg(errorMsg);
			}
			if (jo.has("errorCode")) {
				String errorCode = jo.getString("errorCode");
				resultInfo.setErrorCode(errorCode);
			}
//			ResultInfo resultInfo = g.fromJson(resultStr, ResultInfo.class);
			if ("false".equals(resultStr)) {
				//error 
//				Logger.d(LSHttpClient.TAG, "onError: " + resultStr);
//				if ("未登�?".equals(resultInfo.getMessage())) {
//					//跳转到登陆页�?
//					UI.showLongToast(IApplication.getInstance(), "身份已经失效，请注销后重新登�?");
//					Intent intent = new Intent(IApplication.getInstance(),LoginActivity.class);
//					intent.putExtra(LoginActivity.EXTRA_KEY_FROM_HTTP_HANDLER, true);
//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					IApplication.getInstance().startActivity(intent);
//				}
				onError(statusCode, headers, resultInfo);
			}else {
				//success
				JSON_TYPE data = null;
				if ("true".equals(resultInfo.getSuccess())) {
					if (type != null) {
						String dataStr = jo.getString("data");
						data = g.fromJson(dataStr, type);
						if (data == null) {
							throw new IllegalStateException("Parse out a null object !!!");
						}
//						Logger.d(LSHttpClient.TAG, "onSuccess: " + dataStr);
					} else {
//						Logger.d(LSHttpClient.TAG, "onSuccess: " + resultStr);
					}
					onSuccess(statusCode, headers, data,resultInfo);
				} else {
					onSuccess(statusCode, headers, null,resultInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logger.d(LSHttpClient.TAG,"parse response thrown an problem:  " + response);
			onFailure(statusCode, e);
		}
	}
}
