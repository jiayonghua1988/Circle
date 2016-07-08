package com.yhjia.me.okhttp.utils;

import com.yhjia.me.okhttp.OkHttpUtils;
import com.yhjia.me.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by jiayonghua on 16/7/5.
 */
public class GetUtil {
    public static void post(String url,Map<String,String> map,Callback callback) {
        OkHttpUtils.post().url(url).params(map).build().execute(callback);
    }
}
