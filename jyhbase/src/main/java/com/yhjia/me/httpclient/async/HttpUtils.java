package com.yhjia.me.httpclient.async;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.Log;


import com.yhjia.me.httpclient.core.ParameterList;

public class HttpUtils {
	
	public static String getCoockieFromHeaders(Map<String, List<String>> headers) {
		
		List<String> cookieList = null;
		if (headers == null || (cookieList = headers.get("Set-Cookie")) == null)
			return null;
		for (String item : cookieList) {
			Log.e("Test", "cookie信息=" + item + "\n");
			if (item.startsWith("JSESSIONID=")) {
				item = item.split(";")[0];
				return item;
			}
		}
		return null;
	}
	
	public static String getNetCoockieFromHeaders(Map<String, List<String>> headers) {
		
		StringBuffer buffer = new StringBuffer();
		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			String key = entry.getKey();
			List<String> list = entry.getValue();
			if ("Set-Cookie".equals(key) && list != null && list.size() > 0) {
				int size = list.size();
				for (int i = 0;i< size;i++) {
					if (i == 0) {
						buffer.append(list.get(i));
					} else if (i < list.size() -1) {
						buffer.append(";").append(list.get(i));
					}
				}
			} else if ("set-cookie".equals(key) && list != null && list.size() > 0) {
				int size = list.size();
				for (int i = 0;i< size;i++) {
					if (i == 0) {
						buffer.append(list.get(i));
					} else if (i < list.size() -1) {
						buffer.append(";").append(list.get(i));
					}
				}
			}
		}
		return buffer.toString();
	}
	
//	public static ParameterList getPublicParameterList() {
//		return addPublicHeader(null);
//	}
	
	public static ParameterList addPublicHeader(ParameterList parameterList) {
		if (parameterList == null) {
			parameterList = new ParameterList();
		}
//		String cookie = UserLocalData.getNetCookie();
		//parameterList.addHeader("cookie",UserData.cookie);
//		parameterList.addHeader("PHPSESSID", UserData.getSeesion(IApplication.getInstance().getApplicationContext()));
		parameterList.addHeader("timestamp",System.currentTimeMillis() + "");
		parameterList.addHeader("device","android" + "_" + VERSION.SDK_INT);
		return parameterList;
	}
}
