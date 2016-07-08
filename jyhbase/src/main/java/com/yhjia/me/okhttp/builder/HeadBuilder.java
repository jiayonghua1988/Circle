package com.yhjia.me.okhttp.builder;


import com.yhjia.me.okhttp.OkHttpUtils;
import com.yhjia.me.okhttp.request.OtherRequest;
import com.yhjia.me.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers).build();
    }
}
