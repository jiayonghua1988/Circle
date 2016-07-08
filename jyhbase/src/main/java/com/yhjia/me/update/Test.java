package com.yhjia.me.update;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by jiayonghua on 16/6/18.
 */
public class Test {

    /**
     * 版本更新测试
     * @param context
     * @param url
     * @param size
     * @param drawableId
     */
    public void test(Context context,String url,long size,int drawableId) {
        DownLoadApk loadApk = new DownLoadApk(context,drawableId);
        loadApk.showUpdate(url, "版本 更新 message", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }
}
