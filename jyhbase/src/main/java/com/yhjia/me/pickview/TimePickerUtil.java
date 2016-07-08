package com.yhjia.me.pickview;

import android.app.Activity;

import java.util.Date;

/**
 * Created by jiayonghua on 16/7/6.
 * 日期选择控件包括
 * yyyy-MM-dd
 * yyyy-MM
 * yyyy-MM-dd HH:mm
 */
public class TimePickerUtil {
    private Activity activity;
    private TimePickerView.Type type;
    private TimePickerView timePickerView;
    private TimePickerView.OnTimeSelectListener listener;

    public TimePickerUtil(Activity activity,TimePickerView.Type type) {
        this.activity = activity;
        this.type = type;
        timePickerView = new TimePickerView(activity,type);
        timePickerView.setCyclic(false);
        timePickerView.setTime(new Date());
        timePickerView.setCancelable(true);
    }

    /**
     * 绑定事件回调
     * @param listener
     */
    public void setOnTimeSelectListener(TimePickerView.OnTimeSelectListener listener) {
        timePickerView.setOnTimeSelectListener(listener);
    }

    public void show() {
        timePickerView.show();
    }
}
