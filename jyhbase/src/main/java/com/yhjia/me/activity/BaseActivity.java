package com.yhjia.me.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * activity的基类用来封装 共用的方法参数和属性
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener{
    private InputMethodManager imm;

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
}
