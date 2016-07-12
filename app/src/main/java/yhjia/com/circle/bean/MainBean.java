package yhjia.com.circle.bean;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import yhjia.com.circle.activity.R;

/**
 * Created by jiayonghua on 16/7/12.
 * 添加首页数据
 */
public class MainBean {
    public static MainBean instance = new MainBean();

    private static Context context;

    private MainBean() {

    }

    public static MainBean getInstance(Context mContext) {
        context = mContext;
        return instance;
    }

    public List<String> getData() {
        int [] resIds = new int []{
                R.string.btn_guide,
                R.string.btn_erCode,
                R.string.btn_crash,
                R.string.btn_load_web,
                R.string.btn_out_from_bottom,
                R.string.btn_big_picture_browse,
                R.string.btn_choice_picture,
                R.string.btn_update_version,
                R.string.btn_print_log,
                R.string.btnOkHttp_label,
                R.string.pick_date,
                R.string.pick_number,
                R.string.change_tab,
                R.string.nine_grid_picture,
                R.string.btn_banner,
                R.string.btn_type_1
        };
        return getLabels(resIds);
    }

    private List<String> getLabels(int [] resIds) {
        List<String> list = new ArrayList<String>();
        for (int resId : resIds) {
            list.add(context.getString(resId));
        }
        return list;
    }

}
