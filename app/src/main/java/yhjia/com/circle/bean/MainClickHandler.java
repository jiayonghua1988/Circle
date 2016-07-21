package yhjia.com.circle.bean;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.yhjia.me.animation.FromBottomToTopPopup;
import com.yhjia.me.constant.CodeConstant;
import com.yhjia.me.constant.ExtraConstant;
import com.yhjia.me.constant.HttpConstant;
import com.yhjia.me.constant.JsonConstant;
import com.yhjia.me.logger.LogUtil;
import com.yhjia.me.okhttp.OkHttpUtils;
import com.yhjia.me.okhttp.callback.StringCallback;
import com.yhjia.me.photo.browse.BrowserImageActivity;
import com.yhjia.me.pickview.OptionPickerUtil;
import com.yhjia.me.pickview.TimePickerUtil;
import com.yhjia.me.pickview.TimePickerView;
import com.yhjia.me.update.Test;
import com.yhjia.me.util.ToastUtil;
import com.yhjia.me.webkit.WebContentActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import yhjia.com.circle.activity.AutoScrollDemoActivity;
import yhjia.com.circle.activity.ListPopupWindowDemoActivity;
import yhjia.com.circle.activity.NinePictureGridDemo;
import yhjia.com.circle.activity.PagerViewDemoActivity;
import yhjia.com.circle.activity.PinnerSectionListDemo;
import yhjia.com.circle.activity.PopupMenuDemoActivity;
import yhjia.com.circle.activity.R;
import yhjia.com.circle.activity.TestChoicePictureActivity;
import yhjia.com.circle.activity.TestGuideActivity;
import yhjia.com.circle.constant.MainItemPositionConstant;

/**
 * Created  on 16/7/12.
 * 配置首页的点击事件
 * @author  jiayonghua
 */
public class MainClickHandler implements MainItemClick{
    private Activity activity;
    private OptionPickerUtil numberPickerUtil;
    private TimePickerUtil timePickerUtil;
    private FromBottomToTopPopup popup;

    public MainClickHandler(Activity activity) {
        this.activity = activity;
        initChoiceView();
    }

    @Override
    public void onItem(int position) {
        Intent intent = null;
        switch (position) {
            // 引导页
            case MainItemPositionConstant.MAIN_POSITION_0:
                TestGuideActivity.startPage(activity,TestGuideActivity.class);
                break;
            // 扫描二维码
            case MainItemPositionConstant.MAIN_POSITION_1:
                intent = new Intent(activity, CaptureActivity.class);
                activity.startActivityForResult(intent, CodeConstant.CODE_234);
                break;
            // 异常采集 如果测试打开注释
            case MainItemPositionConstant.MAIN_POSITION_2:
//                String str = null;
//                if (str.equals("aa")) {
//
//                }
                break;
            // 加载网页
            case MainItemPositionConstant.MAIN_POSITION_3:
                intent = new Intent(activity, WebContentActivity.class);
                intent.putExtra(WebContentActivity.EXTRA_WEB_URL, ExtraConstant.TEST_BAIDU_URL);
                activity.startActivity(intent);
                break;
            // 从底部滑出
            case MainItemPositionConstant.MAIN_POSITION_4:
                outFromBottom();
                break;
            case MainItemPositionConstant.MAIN_POSITION_5:
                BrowserImageActivity.startPage(activity,getString(R.string.tv_test),
                        getBrowsePictureData(),getInteger(R.integer.res_0));
                break;
            case MainItemPositionConstant.MAIN_POSITION_6:
                TestChoicePictureActivity.startPage(activity, TestChoicePictureActivity.class);
                break;
            case MainItemPositionConstant.MAIN_POSITION_7:
                Test updateTest = new Test();
                updateTest.test(activity, ExtraConstant.TEST_BAIDU_URL, getInteger(R.integer.res_10000),
                        R.drawable.btn_back);
                break;
            // 打印log
            case MainItemPositionConstant.MAIN_POSITION_8:
                LogUtil.json(printJson());
                break;
            case MainItemPositionConstant.MAIN_POSITION_9:
                okHttp();
                break;
            case MainItemPositionConstant.MAIN_POSITION_10:
                timePickerUtil.show();
                break;
            case MainItemPositionConstant.MAIN_POSITION_11:
                numberPickerUtil.show();
                break;
            case MainItemPositionConstant.MAIN_POSITION_12:
                PagerViewDemoActivity.startPage(activity, PagerViewDemoActivity.class);
                break;
            case MainItemPositionConstant.MAIN_POSITION_13:
                NinePictureGridDemo.startPage(activity, NinePictureGridDemo.class);
                break;
            case MainItemPositionConstant.MAIN_POSITION_14:
                AutoScrollDemoActivity.startPage(activity, AutoScrollDemoActivity.class);
                break;
            case MainItemPositionConstant.MAIN_POSITION_15:
                PinnerSectionListDemo.startPage(activity, PinnerSectionListDemo.class);
                break;
            case MainItemPositionConstant.MAIN_POSITION_16:
                PopupMenuDemoActivity.startPage(activity,PopupMenuDemoActivity.class);
                break;
            case MainItemPositionConstant.MAIN_POSITION_17:
                ListPopupWindowDemoActivity.startPage(activity, ListPopupWindowDemoActivity.class);
                break;
        }
    }

    private void outFromBottom() {
        View view = LayoutInflater.from(activity).inflate(R.layout.test_from_bottom,null);
        popup = new FromBottomToTopPopup(activity,view);
        popup.show();
    }

    private String printJson() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JsonConstant.NAME,getString(R.string.json_name));
            jsonObject.put(JsonConstant.AGE,getInteger(R.integer.res_23));
            jsonObject.put(JsonConstant.SCHOOL,getString(R.string.json_school));
            jsonObject.put(JsonConstant.ADDRESS,getString(R.string.json_address));
            JSONObject dataJson = new JSONObject();
            dataJson.put(JsonConstant.DATA,jsonObject);
            return dataJson.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void okHttp() {
        OkHttpUtils.get().url(HttpConstant.ARTICLE_GET_TYPE_LIST).build().
                execute(new StringCallback() {
            @Override
            public void onResponse(String response) {
                LogUtil.json(response);
            }
        });
    }

    private void initChoiceView() {
        popup = new FromBottomToTopPopup(activity,LayoutInflater.from(activity)
                .inflate(R.layout.item_pinner_section,null));
        timePickerUtil = new TimePickerUtil(activity, TimePickerView.Type.YEAR_MONTH_DAY);
        timePickerUtil.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat(getString(R.string.time_format));
                ToastUtil.showToast(format.format(date));
            }
        });

        ArrayList<String> arrays = new ArrayList<String>();
        int startPosition = getInteger(R.integer.res_1);
        int endPoisiton = getInteger(R.integer.res_20);
        for (int i = startPosition;i < endPoisiton;i++) {
            arrays.add(i + "");
        }

        numberPickerUtil = new OptionPickerUtil(activity,arrays,getString(R.string.label_age));
    }

    private String getString(int resId) {
        return activity.getString(resId);
    }

    private ArrayList<String> getBrowsePictureData() {
        String [] pictureArrays = activity.getResources().getStringArray(R.array.load_images_demo_url);
        return (ArrayList)Arrays.asList(pictureArrays);
    }

    private int getInteger(int resId) {
        return activity.getResources().getInteger(resId);
    }
}
