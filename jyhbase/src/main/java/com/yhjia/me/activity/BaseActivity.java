package com.yhjia.me.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * activity的基类用来封装 共用的方法参数和属性
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener{
    private InputMethodManager imm;
    protected ImageLoader imageLoader;
    protected DisplayImageOptions options;
    protected DisplayImageOptions.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initLayoutID() > 0) {
            setContentView(initLayoutID());
            initView();
            initLogic();
            initCommonObj();
        }
    }

    /**
     * 返回布局文件id
     * @return
     */
    public abstract int initLayoutID();

    /**
     * 布局实例化
     */
    public abstract void initView();

    /**
     * 业务逻辑
     */
    public abstract void initLogic();

    /**
     * 键盘的显示和隐藏
     * @param hasFocus
     * @param v
     */
    public void onFocusChange(final boolean hasFocus,final View v){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(hasFocus)  {
                    //显示输入法
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }else{
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //imm.restartInput(inputContent);
                }
            }
        });
    }

    /**
     * 所有对象的实例化
     */
    private void initCommonObj() {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    /**
     * 取代系统的findViewById方法 解决的问题是系统方法每次都要进行类型转化
     * @param resId
     * @param <T>
     * @return
     */
    public <T extends View> T findView(int resId) {
        return (T)findViewById(resId);
    }

    public void toast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

    }

    public static void startPage(Activity activity,Class toClazz) {
        Intent intent = new Intent(activity,toClazz);
        activity.startActivity(intent);
    }

    public void configLoadImage(int defaultDrawableId) {
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
}
