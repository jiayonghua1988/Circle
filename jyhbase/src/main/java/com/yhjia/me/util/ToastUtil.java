package com.yhjia.me.util;

import android.content.Context;
import android.widget.Toast;

import com.yhjia.me.app.IApplication;

/**
 * Created by jiayonghua on 16/6/17.
 * toast 提示封装类
 */
public final class ToastUtil {
    private static Toast mToast;

    public static void showToast(int resourceId) {
        if (IApplication.context == null) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(IApplication.context, resourceId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resourceId);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showToast(String message) {
        if (IApplication.context == null) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(IApplication.context, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showToast(Context context,int resourceId) {
        if (context == null) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), resourceId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resourceId);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showToast(Context context,String message) {
        if (context == null) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

}