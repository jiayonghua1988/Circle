package yhjia.com.circle.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.yhjia.me.activity.BaseActivity;
import com.yhjia.me.animation.FromBottomToTopPopup;
import com.yhjia.me.constant.CodeConstant;
import com.yhjia.me.constant.ExtraConstant;
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
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private View btnGuide;
    private View erCode;
    private View btnCrash;
    private View loadWeb;
    private View animtaionFromTop;
    private FromBottomToTopPopup popup;
    private View browseBigPicture;
    private View btnChoicePicture;
    private View btnUpdate;
    private View printLog;
    private View btnOkHttp;
    private View selectDate;
    private TimePickerUtil timePickerUtil;
    private View selectNumber;
    private OptionPickerUtil numberPickerUtil;
    private View btnChangeTab;
    private View btnNineGrid;
    private View btnBanner;



    @Override
    public int initLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        btnGuide = findView(R.id.btnGuide);
        erCode = findView(R.id.erCode);
        btnCrash = findView(R.id.btnCrash);
        loadWeb = findView(R.id.loadWeb);
        animtaionFromTop = findView(R.id.animtaionFromTop);
        browseBigPicture = findView(R.id.browseBigPicture);
        btnChoicePicture = findView(R.id.btnChoicePicture);
        btnUpdate = findView(R.id.btnUpdate);
        printLog = findView(R.id.printLog);
        btnOkHttp = findView(R.id.btnOkHttp);
        selectDate = findView(R.id.selectDate);
        selectNumber = findView(R.id.selectNumber);
        btnChangeTab = findView(R.id.btnChangeTab);
        btnNineGrid = findView(R.id.btnNineGrid);
        btnBanner = findView(R.id.btnBanner);

    }

    @Override
    public void initLogic() {
        btnGuide.setOnClickListener(this);
        erCode.setOnClickListener(this);
        btnCrash.setOnClickListener(this);
        loadWeb.setOnClickListener(this);
        animtaionFromTop.setOnClickListener(this);
        browseBigPicture.setOnClickListener(this);
        btnChoicePicture.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        printLog.setOnClickListener(this);
        btnOkHttp.setOnClickListener(this);
        selectDate.setOnClickListener(this);
        selectNumber.setOnClickListener(this);
        btnChangeTab.setOnClickListener(this);
        btnNineGrid.setOnClickListener(this);
        btnBanner.setOnClickListener(this);

        timePickerUtil = new TimePickerUtil(this, TimePickerView.Type.YEAR_MONTH_DAY);
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
        numberPickerUtil = new OptionPickerUtil(this,arrays,"岁");
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btnGuide: // 引导页
                intent = new Intent(this,TestGuideActivity.class);
                startActivity(intent);
            break;
            case R.id.erCode: // 扫描二维码
                intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent,CodeConstant.CODE_234);
            break;
            case R.id.btnCrash: // 异常采集
                String str = null;
                if (str.equals("aa")) {

                }
            break;
            case R.id.loadWeb: // 加载网页
                intent = new Intent(this, WebContentActivity.class);
                intent.putExtra(WebContentActivity.EXTRA_WEB_URL,ExtraConstant.TEST_BAIDU_URL);
                startActivity(intent);
            break;
            case R.id.animtaionFromTop: // 从底部滑出
                outFromBottom();
            break;
            case R.id.btnExit:
                popup.hide();
            break;
            case R.id.browseBigPicture:
                ArrayList<String> pictures = new ArrayList<String>();
                pictures.add("http://test.jiahao.me/repo/emo3pd141lzfa");
                pictures.add("http://test.jiahao.me/repo/2a32yk4yih4q3");
                pictures.add("http://test.jiahao.me/repo/hnbd4tyrejlru");
                pictures.add("http://test.jiahao.me/repo/ynwk1lthcxhvg");
                intent = new Intent(this, BrowserImageActivity.class);
                intent.putExtra(BrowserImageActivity.TAG_NAME,"测试");
                intent.putExtra(BrowserImageActivity.TAG_IMGS,pictures);
                intent.putExtra(BrowserImageActivity.TAG_INDEX, 0);
                startActivity(intent);
            break;
            case R.id.btnChoicePicture:
                intent = new Intent(this,TestChoicePictureActivity.class);
                startActivity(intent);
            break;
            case R.id.btnUpdate:
                Test updateTest = new Test();
                updateTest.test(this,ExtraConstant.TEST_BAIDU_URL,10000,R.drawable.btn_back);
            break;
            case R.id.printLog: // 打印log
                LogUtil.json(printJson());
            break;
            case R.id.btnOkHttp:
                okHttp();
            break;
            case R.id.selectDate:
                timePickerUtil.show();
            break;
            case R.id.selectNumber:
                numberPickerUtil.show();
            break;
            case R.id.btnChangeTab:
                PagerViewDemoActivity.startPage(this);
            break;
            case R.id.btnNineGrid:
                NinePictureGridDemo.startPage(this,NinePictureGridDemo.class);
            break;
            case R.id.btnBanner:
                AutoScrollDemoActivity.startPage(this,AutoScrollDemoActivity.class);
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CodeConstant.CODE_234) {
            LogUtil.i("扫码结果：" + getIntent().getStringExtra(ExtraConstant.RESULT));
            ToastUtil.showToast(R.string.scan_success);
        }
    }

    private void outFromBottom() {
        View view = LayoutInflater.from(this).inflate(R.layout.test_from_bottom,null);
        view.findViewById(R.id.btnExit).setOnClickListener(this);
        popup = new FromBottomToTopPopup(this,view);
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
        Map<String,String> map = new HashMap<>();
        OkHttpUtils.get().url("/article/gettypelist").build().execute(new StringCallback() {
            @Override
            public void onResponse(String response) {
                LogUtil.json(response);
            }
        });
    }

}
