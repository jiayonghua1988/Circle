package com.yhjia.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.yhjia.me.logger.LogUtil;

public class YYBaseActivity extends BaseActivity {
    protected ImageLoader imageLoader;
    protected DisplayImageOptions options;
    protected DisplayImageOptions.Builder builder;
    private InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configLoadImage(settingDefaultPicture());
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        String extra_msg_id = getIntent().getStringExtra("extra_msg_id");
        LogUtil.i("extra_msg_id=" + extra_msg_id);
    }

    @Override
    public int initLayoutID() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initLogic() {

    }


    private void configLoadImage(int defaultDrawableId) {
        imageLoader = ImageLoader.getInstance();
        builder = new DisplayImageOptions.Builder();
        builder.cacheOnDisk(true);
        builder.considerExifParams(true);
        builder.cacheInMemory(false);
        builder.imageScaleType(ImageScaleType.IN_SAMPLE_INT);
        builder.bitmapConfig(Bitmap.Config.RGB_565);
        if (defaultDrawableId > 0) {
            builder.showImageOnFail(defaultDrawableId);
            builder.showImageForEmptyUri(defaultDrawableId);
        }
        options = builder.build();
    }

    public int settingDefaultPicture() {
        return 0;
    }

    public void onFocusChange(boolean hasFocus, final View v) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                if (isFocus) {
                    //显示输入法
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //imm.restartInput(inputContent);
                }
            }
        }, 0);
    }

    public static void startPage(Activity activity,Class toClazz) {
        Intent intent = new Intent(activity,toClazz);
        activity.startActivity(intent);
    }

}