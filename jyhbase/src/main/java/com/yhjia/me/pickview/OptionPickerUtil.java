package com.yhjia.me.pickview;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiayonghua on 16/7/7.
 * 数字选择器
 */
public class OptionPickerUtil {
    private Activity activity;
    private List<String> list;
    private OptionsPickerView pickerView;
    public OptionPickerUtil(Activity activity, ArrayList<String> list, String label) {
        this.activity = activity;
        this.list = list;
        pickerView = new OptionsPickerView(activity);
        pickerView.setPicker(list);
        pickerView.setCyclic(false);
        pickerView.setLabels(label);
    }


    public void show() {
        pickerView.show();
    }

}
