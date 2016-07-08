package com.yhjia.me.logger;

/**
 * Created by jiayonghua on 16/6/25.
 * 打印log帮助类
 */
public class LogUtil {
    public static boolean DEBUG = true;
    public static String TAG = "TAG";

    public static void i(String msg) {
        if (DEBUG) {
            Logger.i(msg,TAG);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Logger.e(msg, TAG);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            Logger.d(msg, TAG);
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Logger.w(msg, TAG);
        }
    }
    public static void json(String json) {
        if (DEBUG) {
            Logger.json(json);
        }
    }
}
