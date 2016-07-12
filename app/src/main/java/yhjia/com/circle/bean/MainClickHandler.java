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
import java.util.Date;

import yhjia.com.circle.activity.AutoScrollDemoActivity;
import yhjia.com.circle.activity.NinePictureGridDemo;
import yhjia.com.circle.activity.PagerViewDemoActivity;
import yhjia.com.circle.activity.PinnerSectionListDemo;
import yhjia.com.circle.activity.R;
import yhjia.com.circle.activity.TestChoicePictureActivity;
import yhjia.com.circle.activity.TestGuideActivity;

/**
 * Created by jiayonghua on 16/7/12.
 * 配置首页的点击事件
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
            case 0: // 引导页
                TestGuideActivity.startPage(activity,TestGuideActivity.class);
                break;
            case 1: // 扫描二维码
                intent = new Intent(activity, CaptureActivity.class);
                activity.startActivityForResult(intent, CodeConstant.CODE_234);
                break;
            case 2: // 异常采集 如果测试打开注释
//                String str = null;
//                if (str.equals("aa")) {
//
//                }
                break;
            case 3: // 加载网页
                intent = new Intent(activity, WebContentActivity.class);
                intent.putExtra(WebContentActivity.EXTRA_WEB_URL, ExtraConstant.TEST_BAIDU_URL);
                activity.startActivity(intent);
                break;
            case 4: // 从底部滑出
                outFromBottom();
                break;
            case 5:
                ArrayList<String> pictures = new ArrayList<String>();
                pictures.add("http://test.jiahao.me/repo/emo3pd141lzfa");
                pictures.add("http://test.jiahao.me/repo/2a32yk4yih4q3");
                pictures.add("http://test.jiahao.me/repo/hnbd4tyrejlru");
                pictures.add("http://test.jiahao.me/repo/ynwk1lthcxhvg");
                intent = new Intent(activity, BrowserImageActivity.class);
                intent.putExtra(BrowserImageActivity.TAG_NAME,"测试");
                intent.putExtra(BrowserImageActivity.TAG_IMGS,pictures);
                intent.putExtra(BrowserImageActivity.TAG_INDEX, 0);
                activity.startActivity(intent);
                break;
            case 6:
                TestChoicePictureActivity.startPage(activity,TestChoicePictureActivity.class);
                break;
            case 7:
                Test updateTest = new Test();
                updateTest.test(activity,ExtraConstant.TEST_BAIDU_URL,10000,R.drawable.btn_back);
                break;
            case 8: // 打印log
                LogUtil.json(printJson());
                break;
            case 9:
                okHttp();
                break;
            case 10:
                timePickerUtil.show();
                break;
            case 11:
                numberPickerUtil.show();
                break;
            case 12:
                PagerViewDemoActivity.startPage(activity,PagerViewDemoActivity.class);
                break;
            case 13:
                NinePictureGridDemo.startPage(activity, NinePictureGridDemo.class);
                break;
            case 14:
                AutoScrollDemoActivity.startPage(activity, AutoScrollDemoActivity.class);
                break;
            case 15:
                PinnerSectionListDemo.startPage(activity, PinnerSectionListDemo.class);
                break;
        }
    }

    private void outFromBottom() {
        View view = LayoutInflater.from(activity).inflate(R.layout.test_from_bottom,null);
//        view.findViewById(R.id.btnExit).setOnClickListener(this);
        popup = new FromBottomToTopPopup(activity,view);
        popup.show();
    }

    private String printJson() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JsonConstant.NAME,"张三");
            jsonObject.put(JsonConstant.AGE,23);
            jsonObject.put(JsonConstant.SCHOOL,"襄阳中学");
            jsonObject.put(JsonConstant.ADDRESS,"北京市");
            JSONObject dataJson = new JSONObject();
            dataJson.put(JsonConstant.DATA,jsonObject);
            return dataJson.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void okHttp() {
        OkHttpUtils.get().url(HttpConstant.ARTICLE_GET_TYPE_LIST).build().execute(new StringCallback() {
            @Override
            public void onResponse(String response) {
                LogUtil.json(response);
            }
        });
    }

    private void initChoiceView() {
        popup = new FromBottomToTopPopup(activity,LayoutInflater.from(activity).inflate(R.layout.item_pinner_section,null));
        timePickerUtil = new TimePickerUtil(activity, TimePickerView.Type.YEAR_MONTH_DAY);
        timePickerUtil.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                ToastUtil.showToast(format.format(date));
            }
        });
        ArrayList<String> arrays = new ArrayList<>();
        for (int i = 1;i < 20;i++) {
            arrays.add(i + "");
        }
        numberPickerUtil = new OptionPickerUtil(activity,arrays,"岁");
    }
}
